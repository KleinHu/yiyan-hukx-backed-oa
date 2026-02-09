package com.cac.oa.vo.supplies.dashboard;

import com.cac.oa.vo.supplies.SuppliesItemVO;
import com.cac.oa.vo.supplies.SuppliesRequestVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
@Builder
@ApiModel("办公用品看板数据")
public class SuppliesDashboardVO {

    @ApiModelProperty("核心统计指标")
    private Statistics statistics;

    @ApiModelProperty("分类分布（饼图数据）")
    private List<Map<String, Object>> categoryDistribution;

    @ApiModelProperty("近七日申领趋势（折线图数据）")
    private TrendData applyTrend;

    @ApiModelProperty("库存预警列表")
    private List<SuppliesItemVO> lowStockItems;

    @ApiModelProperty("最新申领动态")
    private List<SuppliesRequestVO> recentRequests;

    @Data
    @Builder
    public static class Statistics {
        @ApiModelProperty("物品总数")
        private Integer totalItems;
        @ApiModelProperty("低库存预警数")
        private Integer lowStockCount;
        @ApiModelProperty("待审核申请数")
        private Integer pendingAuditCount;
        @ApiModelProperty("年度申领总金额")
        private Double yearlyTotalAmount;
    }

    @Data
    @Builder
    public static class TrendData {
        @ApiModelProperty("日期列表")
        private List<String> dates;
        @ApiModelProperty("数值列表")
        private List<Integer> values;
    }
}
