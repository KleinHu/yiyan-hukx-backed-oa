package com.cac.oa.service.supplies.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cac.oa.convert.supplies.SuppliesRequestConvert;
import com.cac.oa.dao.supplies.SuppliesInventoryMapper;
import com.cac.oa.dao.supplies.SuppliesRequestItemMapper;
import com.cac.oa.dao.supplies.SuppliesRequestMapper;
import com.cac.oa.dao.supplies.SuppliesItemMapper;
import com.cac.oa.entity.supplies.SuppliesInventoryEntity;
import com.cac.oa.entity.supplies.SuppliesRequestEntity;
import com.cac.oa.entity.supplies.SuppliesRequestItemEntity;
import com.cac.oa.entity.supplies.SuppliesItemEntity;
import com.cac.oa.service.supplies.ISuppliesItemService;
import com.cac.oa.service.supplies.ISuppliesRequestService;
import com.cac.oa.vo.supplies.AuditRequest;
import com.cac.oa.vo.supplies.InventoryChangeRequest;
import com.cac.oa.vo.supplies.SuppliesRequestItemVO;
import com.cac.oa.vo.supplies.SuppliesRequestVO;
import com.cac.oa.vo.supplies.SuppliesRequestQuery;
import com.cac.yiyan.common.page.PageData;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SuppliesRequestServiceImpl extends ServiceImpl<SuppliesRequestMapper, SuppliesRequestEntity> implements ISuppliesRequestService {

    private final SuppliesRequestItemMapper requestItemMapper;
    private final SuppliesItemMapper itemMapper;
    private final SuppliesInventoryMapper inventoryMapper;
    private final ISuppliesItemService itemService;
    private final SuppliesRequestConvert converter = SuppliesRequestConvert.INSTANCE;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void submitRequest(SuppliesRequestVO vo) {
        // 1. 校验库存预占
        if (vo.getItems() != null && !vo.getItems().isEmpty()) {
            for (SuppliesRequestItemVO itemVO : vo.getItems()) {
                SuppliesInventoryEntity inventory = inventoryMapper.selectById(itemVO.getItemId());
                int stock = (inventory != null && inventory.getStock() != null) ? inventory.getStock() : 0;
                int lockStock = (inventory != null && inventory.getLockStock() != null) ? inventory.getLockStock() : 0;
                int available = stock - lockStock;

                if (available < itemVO.getQuantity()) {
                    SuppliesItemEntity item = itemMapper.selectById(itemVO.getItemId());
                    throw new RuntimeException("物品 [" + (item != null ? item.getName() : itemVO.getItemId()) + "] 可领用库存不足 (剩余: " + available + ")");
                }

                // 预占库存
                if (inventory == null) {
                    inventory = new SuppliesInventoryEntity();
                    inventory.setItemId(itemVO.getItemId());
                    inventory.setStock(0);
                    inventory.setLockStock(itemVO.getQuantity());
                    inventoryMapper.insert(inventory);
                } else {
                    inventory.setLockStock(lockStock + itemVO.getQuantity());
                    inventoryMapper.updateById(inventory);
                }
            }
        }

        SuppliesRequestEntity entity = converter.convert(vo);

        // 生成单号: LY + 时间戳 + 随机位
        String orderNo = "LY" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")) + (int)((Math.random()*9+1)*100);
        entity.setOrderNo(orderNo);
        entity.setApplyTime(LocalDateTime.now());
        entity.setAuditStatus(0); // 待审核

        this.save(entity);

        // 保存明细
        if (vo.getItems() != null && !vo.getItems().isEmpty()) {
            List<SuppliesRequestItemEntity> itemEntities = vo.getItems().stream().map(itemVO -> {
                SuppliesRequestItemEntity itemEntity = converter.convertItem(itemVO);
                itemEntity.setRequestId(entity.getId());
                itemEntity.setIssuedQuantity(0);
                return itemEntity;
            }).collect(Collectors.toList());

            for (SuppliesRequestItemEntity item : itemEntities) {
                requestItemMapper.insert(item);
            }
        }
    }

    @Override
    public PageData<SuppliesRequestVO> getPage(SuppliesRequestQuery query) {
        PageData<SuppliesRequestEntity> page = baseMapper.selectPage(query);

        if (page.getList().isEmpty()) {
            return converter.convertPage(page);
        }

        PageData<SuppliesRequestVO> voPage = converter.convertPage(page);

        // 查询明细并填充物品基本信息
        for (SuppliesRequestVO vo : voPage.getList()) {
            List<SuppliesRequestItemEntity> items = requestItemMapper.selectList(
                    new LambdaQueryWrapper<SuppliesRequestItemEntity>().eq(SuppliesRequestItemEntity::getRequestId, vo.getId()));

            if (!items.isEmpty()) {
                List<Long> itemIds = items.stream().map(SuppliesRequestItemEntity::getItemId).collect(Collectors.toList());
                Map<Long, SuppliesItemEntity> itemMap = itemMapper.selectBatchIds(itemIds).stream()
                        .collect(Collectors.toMap(SuppliesItemEntity::getId, i -> i));

                List<SuppliesRequestItemVO> itemVOs = items.stream().map(itemEntity -> {
                    SuppliesRequestItemVO itemVO = converter.convertItem(itemEntity);
                    SuppliesItemEntity i = itemMap.get(itemEntity.getItemId());
                    if (i != null) {
                        itemVO.setItemName(i.getName());
                        itemVO.setSpec(i.getSpec());
                        itemVO.setUnit(i.getUnit());
                    }
                    return itemVO;
                }).collect(Collectors.toList());
                vo.setItems(itemVOs);
            }
        }

        return voPage;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void auditRequest(AuditRequest request) {
        SuppliesRequestEntity entity = this.getById(request.getId());
        if (entity == null) {
            throw new RuntimeException("申请单不存在");
        }
        if (entity.getAuditStatus() != 0) {
            throw new RuntimeException("该申请单已审核，请勿重复操作");
        }

        entity.setAuditStatus(request.getStatus());
        entity.setRemark(request.getRemark());
        entity.setAuditTime(LocalDateTime.now());
        entity.setAuditorName(StringUtils.hasText(request.getOperatorName()) ? request.getOperatorName() : "-");

        this.updateById(entity);

        List<SuppliesRequestItemEntity> items = requestItemMapper.selectList(
                new LambdaQueryWrapper<SuppliesRequestItemEntity>().eq(SuppliesRequestItemEntity::getRequestId, entity.getId()));

        if (request.getStatus() == 1) { // 审核通过 (发放)
            Map<Long, Integer> issuedMap = new java.util.HashMap<>();
            if (request.getItems() != null) {
                request.getItems().forEach(i -> {
                    if (i.getItemId() != null && i.getIssuedQuantity() != null) {
                        issuedMap.put(i.getItemId(), i.getIssuedQuantity());
                    }
                });
            }

            for (SuppliesRequestItemEntity item : items) {
                int issued = item.getQuantity();
                if (issuedMap.containsKey(item.getItemId())) {
                    issued = issuedMap.get(item.getItemId());
                }

                // 1. 实扣库存
                InventoryChangeRequest changeReq = new InventoryChangeRequest();
                changeReq.setItemId(item.getItemId());
                changeReq.setQuantity(-issued); // 减少实际库存 (按实发)
                changeReq.setScenario(2);
                changeReq.setRelNo(entity.getOrderNo());
                changeReq.setOperatorName(request.getOperatorName());
                changeReq.setOperatorCode(request.getOperatorCode());
                itemService.changeInventory(changeReq);

                // 2. 释放预占库存 (按申请)
                SuppliesInventoryEntity inventory = inventoryMapper.selectById(item.getItemId());
                if (inventory != null) {
                    inventory.setLockStock(Math.max(0, inventory.getLockStock() - item.getQuantity()));
                    inventoryMapper.updateById(inventory);
                }

                // 更新明细
                item.setIssuedQuantity(issued);
                requestItemMapper.updateById(item);
            }
            // 状态转为已发放
            entity.setAuditStatus(3);
            this.updateById(entity);
        } else if (request.getStatus() == 2) { // 驳回
            for (SuppliesRequestItemEntity item : items) {
                // 释放预占库存
                SuppliesInventoryEntity inventory = inventoryMapper.selectById(item.getItemId());
                if (inventory != null) {
                    inventory.setLockStock(Math.max(0, inventory.getLockStock() - item.getQuantity()));
                    inventoryMapper.updateById(inventory);
                }
            }
        }
    }

    @Override
    public SuppliesRequestVO getByOrderNo(String orderNo) {
        SuppliesRequestEntity entity = this.getOne(new LambdaQueryWrapper<SuppliesRequestEntity>().eq(SuppliesRequestEntity::getOrderNo, orderNo));
        if (entity == null) {
            return null;
        }

        SuppliesRequestVO vo = converter.convert(entity);

        // 获取明细
        List<SuppliesRequestItemEntity> items = requestItemMapper.selectList(
                new LambdaQueryWrapper<SuppliesRequestItemEntity>().eq(SuppliesRequestItemEntity::getRequestId, entity.getId()));

        if (!items.isEmpty()) {
            List<Long> itemIds = items.stream().map(SuppliesRequestItemEntity::getItemId).collect(Collectors.toList());
            Map<Long, SuppliesItemEntity> itemMap = itemMapper.selectBatchIds(itemIds).stream()
                    .collect(Collectors.toMap(SuppliesItemEntity::getId, i -> i));

            List<SuppliesRequestItemVO> itemVOs = items.stream().map(itemEntity -> {
                SuppliesRequestItemVO itemVO = converter.convertItem(itemEntity);
                SuppliesItemEntity i = itemMap.get(itemEntity.getItemId());
                if (i != null) {
                    itemVO.setItemName(i.getName());
                    itemVO.setSpec(i.getSpec());
                    itemVO.setUnit(i.getUnit());
                }
                return itemVO;
            }).collect(Collectors.toList());
            vo.setItems(itemVOs);
        }

        return vo;
    }

    @Override
    public List<com.cac.oa.vo.supplies.DeptItemConsumptionVO> getDeptItemConsumption(com.cac.oa.vo.supplies.ConsumptionQuery query) {
        return baseMapper.selectDeptItemConsumption(query);
    }

    @Override
    public List<com.cac.oa.vo.supplies.UserConsumptionDetailVO> getConsumptionDetails(com.cac.oa.vo.supplies.ConsumptionQuery query, String deptName, Long itemId) {
        return baseMapper.selectUserConsumptionDetails(query, deptName, itemId);
    }
}
