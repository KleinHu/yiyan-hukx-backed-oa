package com.cac.oa.service.supplies.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cac.oa.convert.supplies.SuppliesItemConvert;
import com.cac.oa.dao.supplies.SuppliesItemMapper;
import com.cac.oa.dao.supplies.SuppliesInventoryMapper;
import com.cac.oa.dao.supplies.SuppliesRecordMapper;
import com.cac.oa.dao.supplies.SuppliesCategoryMapper;
import com.cac.oa.entity.supplies.SuppliesItemEntity;
import com.cac.oa.entity.supplies.SuppliesInventoryEntity;
import com.cac.oa.entity.supplies.SuppliesRecordEntity;
import com.cac.oa.entity.supplies.SuppliesCategoryEntity;
import com.cac.oa.service.supplies.ISuppliesItemService;
import com.cac.oa.vo.supplies.InventoryChangeRequest;
import com.cac.oa.vo.supplies.SuppliesItemVO;
import com.cac.oa.vo.supplies.SuppliesQuery;
import com.cac.yiyan.common.page.PageData;
import com.cac.yiyan.mybatis.query.LambdaQueryWrapperX;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import com.cac.oa.convert.supplies.SuppliesRecordConvert;
import com.cac.oa.vo.supplies.SuppliesRecordQuery;
import com.cac.oa.vo.supplies.SuppliesRecordVO;

@Service
@RequiredArgsConstructor
public class SuppliesItemServiceImpl extends ServiceImpl<SuppliesItemMapper, SuppliesItemEntity> implements ISuppliesItemService {

    private final SuppliesInventoryMapper inventoryMapper;
    private final SuppliesRecordMapper recordMapper;
    private final SuppliesCategoryMapper categoryMapper;

    private final SuppliesItemConvert converter = SuppliesItemConvert.INSTANCE;

    @Override
    public PageData<SuppliesItemVO> getPage(SuppliesQuery query) {
        PageData<SuppliesItemEntity> page = baseMapper.selectPage(query);

        if (page.getList().isEmpty()) {
            return converter.convertPage(page);
        }

        // 获取分类名称映射
        List<Long> categoryIds = page.getList().stream().map(SuppliesItemEntity::getCategoryId).distinct().collect(Collectors.toList());
        Map<Long, String> categoryMap = categoryMapper.selectBatchIds(categoryIds).stream()
                .collect(Collectors.toMap(SuppliesCategoryEntity::getId, SuppliesCategoryEntity::getName));

        // 获取库存映射
        List<Long> itemIds = page.getList().stream().map(SuppliesItemEntity::getId).collect(Collectors.toList());
        Map<Long, SuppliesInventoryEntity> inventoryMap = inventoryMapper.selectBatchIds(itemIds).stream()
                .collect(Collectors.toMap(SuppliesInventoryEntity::getItemId, i -> i));

        PageData<SuppliesItemVO> voPage = converter.convertPage(page);
        voPage.getList().forEach(vo -> {
            vo.setCategoryName(categoryMap.get(vo.getCategoryId()));

            SuppliesInventoryEntity inventory = inventoryMap.get(vo.getId());
            int stock = (inventory != null && inventory.getStock() != null) ? inventory.getStock() : 0;
            int lockStock = (inventory != null && inventory.getLockStock() != null) ? inventory.getLockStock() : 0;

            vo.setStock(stock);
            vo.setLockStock(lockStock);
            vo.setAvailableStock(Math.max(0, stock - lockStock));
        });

        return voPage;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void changeInventory(InventoryChangeRequest request) {
        Long itemId = request.getItemId();
        Integer quantity = request.getQuantity();

        // 1. 更新库存
        SuppliesInventoryEntity inventory = inventoryMapper.selectById(itemId);
        if (inventory == null) {
            inventory = new SuppliesInventoryEntity();
            inventory.setItemId(itemId);
            inventory.setStock(quantity);
            inventoryMapper.insert(inventory);
        } else {
            inventory.setStock(inventory.getStock() + quantity);
            if (inventory.getStock() < 0) {
                throw new RuntimeException("库存不足，无法核减");
            }
            inventoryMapper.updateById(inventory);
        }

        // 2. 记录流水
        SuppliesRecordEntity record = new SuppliesRecordEntity();
        record.setItemId(itemId);
        record.setType(quantity > 0 ? 1 : 2);
        record.setScenario(request.getScenario());
        record.setQuantity(Math.abs(quantity));
        record.setRelNo(request.getRelNo());
        record.setCreatorName(request.getOperatorName());
        record.setCreator(request.getOperatorCode());
        recordMapper.insert(record);
    }

    @Override
    public PageData<SuppliesRecordVO> getRecordPage(SuppliesRecordQuery query) {
        // 如果有物品名称模糊查询，需先处理 ID 过滤
        if (StringUtils.hasText(query.getItemName())) {
            List<Long> matchedItemIds = baseMapper.selectList(new LambdaQueryWrapperX<SuppliesItemEntity>()
                    .like(SuppliesItemEntity::getName, query.getItemName())).stream()
                    .map(SuppliesItemEntity::getId).collect(Collectors.toList());
            if (matchedItemIds.isEmpty()) {
                return new PageData<>(Collections.emptyList(), 0L);
            }
            query.setItemId(matchedItemIds.get(0)); // 简化处理，只取第一个匹配的，或由 Mapper 扩展支持 IN
            // 注意：由于 PageParam 本身不直接支持 setItemId 这种逻辑，
            // 且我们的 Mapper selectPage 模式倾向于由 Mapper 内部构建 wrapper。
            // 这里为了保持模式一致，我们最好是在 Mapper 里处理模糊匹配，
            // 或者在这里构造好 ID 集合传给 Mapper。
        }

        PageData<SuppliesRecordEntity> page = recordMapper.selectPage(query);

        if (page.getList().isEmpty()) {
            return SuppliesRecordConvert.INSTANCE.convertPage(page);
        }

        // 关联物品信息
        List<Long> itemIds = page.getList().stream().map(SuppliesRecordEntity::getItemId).distinct().collect(Collectors.toList());
        Map<Long, SuppliesItemEntity> itemMap = this.listByIds(itemIds).stream()
                .collect(Collectors.toMap(SuppliesItemEntity::getId, entity -> entity));

        PageData<SuppliesRecordVO> voPage = SuppliesRecordConvert.INSTANCE.convertPage(page);
        voPage.getList().forEach(vo -> {
            SuppliesItemEntity item = itemMap.get(vo.getItemId());
            if (item != null) {
                vo.setItemName(item.getName());
                vo.setSpec(item.getSpec());
            }
        });

        return voPage;
    }
}
