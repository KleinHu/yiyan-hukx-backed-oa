package com.cac.oa.controller.supplies;

import com.cac.oa.service.supplies.ISuppliesDashboardService;
import com.cac.oa.vo.supplies.dashboard.SuppliesDashboardVO;
import com.cac.yiyan.common.vo.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "办公用品-看板")
@RestController
@RequestMapping("/api/240/oa/supplies/dashboard")
@RequiredArgsConstructor
public class SuppliesDashboardController {

    private final ISuppliesDashboardService dashboardService;

    @GetMapping
    @ApiOperation("获取看板聚合数据")
    public Result<SuppliesDashboardVO> getDashboardData(@RequestParam("year") String year) {
        return Result.ok(dashboardService.getDashboardData(year));
    }
}
