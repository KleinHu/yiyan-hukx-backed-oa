package com.cac.oa.service.supplies;

import com.cac.oa.vo.supplies.dashboard.SuppliesDashboardVO;

public interface ISuppliesDashboardService {
    /**
     * 获取看板数据
     * @param year 年份
     * @return 看板聚合数据
     */
    SuppliesDashboardVO getDashboardData(String year);
}
