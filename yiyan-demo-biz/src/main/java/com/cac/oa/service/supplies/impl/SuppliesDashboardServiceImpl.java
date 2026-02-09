package com.cac.oa.service.supplies.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.cac.oa.convert.supplies.SuppliesItemConvert;
import com.cac.oa.dao.supplies.SuppliesCategoryMapper;
import com.cac.oa.dao.supplies.SuppliesInventoryMapper;
import com.cac.oa.dao.supplies.SuppliesItemMapper;
import com.cac.oa.dao.supplies.SuppliesRequestItemMapper;
import com.cac.oa.dao.supplies.SuppliesRequestMapper;
import com.cac.oa.entity.supplies.SuppliesInventoryEntity;
import com.cac.oa.entity.supplies.SuppliesItemEntity;
import com.cac.oa.service.supplies.ISuppliesDashboardService;
import com.cac.oa.service.supplies.ISuppliesRequestService;
import com.cac.oa.vo.supplies.SuppliesItemVO;
import com.cac.oa.vo.supplies.SuppliesRequestQuery;
import com.cac.oa.vo.supplies.SuppliesRequestVO;
import com.cac.oa.vo.supplies.dashboard.SuppliesDashboardVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SuppliesDashboardServiceImpl implements ISuppliesDashboardService {

    private final SuppliesCategoryMapper categoryMapper;
    private final SuppliesItemMapper itemMapper;
    private final SuppliesInventoryMapper inventoryMapper;
    private final SuppliesRequestMapper requestMapper;
    private final SuppliesRequestItemMapper requestItemMapper;
    private final ISuppliesRequestService requestService;

    @Override
    public SuppliesDashboardVO getDashboardData(String year) {
        // 1. 获取基础档案 (用于关联名称和计算指标)
        List<SuppliesItemEntity> allItems = itemMapper.selectList(new LambdaQueryWrapper<SuppliesItemEntity>().eq(SuppliesItemEntity::getStatus, 1));
        Map<Long, SuppliesItemEntity> itemPriceMap = allItems.stream()
                .collect(Collectors.toMap(SuppliesItemEntity::getId, i -> i, (a, b) -> a));
        
        Map<Long, String> categoryNames = categoryMapper.selectList(null).stream()
                .collect(Collectors.toMap(c -> c.getId(), c -> c.getName(), (a, b) -> a));

        // 2. 获取该年度的领用记录 (核心数据源)
        SuppliesRequestQuery query = new SuppliesRequestQuery();
        query.setYear(year);
        query.setPageNo(1);
        query.setPageSize(2000); 
        List<SuppliesRequestVO> yearRequests = requestService.getPage(query).getList();

        // 3. 计算统计指标
        long pendingAuditCount = yearRequests.stream().filter(r -> r.getAuditStatus() == 0).count();
        double yearlyTotalAmount = yearRequests.stream()
                .filter(r -> r.getAuditStatus() == 1 || r.getAuditStatus() == 3)
                .flatMap(r -> r.getItems().stream())
                .mapToDouble(item -> {
                    SuppliesItemEntity itemEntity = itemPriceMap.get(item.getItemId());
                    return (itemEntity != null && itemEntity.getPrice() != null) 
                            ? itemEntity.getPrice().doubleValue() * item.getQuantity() 
                            : 0.0;
                }).sum();

        // 4. 分类分布 (基于年度申领量统计，体现年度差异)
        Map<String, Long> categoryDataMap = yearRequests.stream()
                .flatMap(r -> r.getItems().stream())
                .collect(Collectors.groupingBy(reqItem -> {
                    SuppliesItemEntity item = itemPriceMap.get(reqItem.getItemId());
                    return (item != null && item.getCategoryId() != null) ? categoryNames.getOrDefault(item.getCategoryId(), "其他") : "其他";
                }, Collectors.summingLong(i -> i.getQuantity())));

        List<Map<String, Object>> distribution = new ArrayList<>();
        categoryDataMap.forEach((name, count) -> {
            Map<String, Object> map = new HashMap<>();
            map.put("name", name);
            map.put("value", count);
            distribution.add(map);
        });

        // 5. 年度月度趋势 (12个月)
        List<String> dates = new ArrayList<>();
        List<Integer> values = new ArrayList<>();
        for (int m = 1; m <= 12; m++) {
            String monthLabel = m + "月";
            dates.add(monthLabel);
            int monthValue = m;
            long count = yearRequests.stream()
                    .filter(r -> r.getApplyTime() != null && r.getApplyTime().getMonthValue() == monthValue)
                    .count();
            values.add((int) count);
        }

        // 6. 获取低库存预警 (全局快照)
        List<Long> itemIds = allItems.stream().map(SuppliesItemEntity::getId).collect(Collectors.toList());
        Map<Long, SuppliesInventoryEntity> inventoryMap = itemIds.isEmpty() ? Collections.emptyMap() :
                inventoryMapper.selectBatchIds(itemIds).stream()
                        .collect(Collectors.toMap(SuppliesInventoryEntity::getItemId, i -> i));

        List<SuppliesItemEntity> lowStockEntities = allItems.stream()
                .filter(i -> {
                    SuppliesInventoryEntity inv = inventoryMap.get(i.getId());
                    int stock = (inv != null && inv.getStock() != null) ? inv.getStock() : 0;
                    int lock = (inv != null && inv.getLockStock() != null) ? inv.getLockStock() : 0;
                    return i.getMinStock() != null && (stock - lock) <= i.getMinStock();
                })
                .limit(5)
                .collect(Collectors.toList());

        List<SuppliesItemVO> lowStockVOs = SuppliesItemConvert.INSTANCE.convertList(lowStockEntities);
        lowStockVOs.forEach(vo -> {
            SuppliesInventoryEntity inv = inventoryMap.get(vo.getId());
            vo.setStock(inv != null ? inv.getStock() : 0);
            vo.setLockStock(inv != null ? inv.getLockStock() : 0);
            vo.setAvailableStock(Math.max(0, (vo.getStock() != null ? vo.getStock() : 0) - (vo.getLockStock() != null ? vo.getLockStock() : 0)));
        });

        return SuppliesDashboardVO.builder()
                .statistics(SuppliesDashboardVO.Statistics.builder()
                        .totalItems(allItems.size())
                        .lowStockCount((int) allItems.stream().filter(i -> {
                            SuppliesInventoryEntity inv = inventoryMap.get(i.getId());
                            int s = (inv != null && inv.getStock() != null) ? inv.getStock() : 0;
                            int l = (inv != null && inv.getLockStock() != null) ? inv.getLockStock() : 0;
                            return i.getMinStock() != null && (s - l) <= i.getMinStock();
                        }).count())
                        .pendingAuditCount((int) pendingAuditCount)
                        .yearlyTotalAmount(yearlyTotalAmount)
                        .build())
                .categoryDistribution(distribution)
                .applyTrend(SuppliesDashboardVO.TrendData.builder()
                        .dates(dates)
                        .values(values)
                        .build())
                .lowStockItems(lowStockVOs)
                .recentRequests(yearRequests.stream().sorted(Comparator.comparing(SuppliesRequestVO::getApplyTime).reversed()).limit(5).collect(Collectors.toList()))
                .build();
    }
}
