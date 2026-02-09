package com.cac.oa.controller.supplies;

import com.cac.oa.service.supplies.ISuppliesRequestService;
import com.cac.oa.vo.supplies.*;
import com.cac.yiyan.common.constant.PageConstant;
import com.cac.yiyan.common.page.PageData;
import com.cac.yiyan.common.vo.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 领用申请 Controller
 */
@Api(tags = "办公用品-领用申请")
@RestController
@RequestMapping("/api/240/oa/supplies/request")
@RequiredArgsConstructor
public class SuppliesRequestController {

    private final ISuppliesRequestService requestService;

    @PostMapping
    @ApiOperation("提交领用申请")
    public Result<Void> submit(@RequestBody SuppliesRequestVO vo) {
        requestService.submitRequest(vo);
        return Result.ok();
    }

    @GetMapping("/page")
    @ApiOperation("分页查询领用记录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = PageConstant.PAGE_NO, value = "当前页码，从1开始", defaultValue = "1", paramType = "query", required = true, dataType = "int"),
            @ApiImplicitParam(name = PageConstant.PAGE_SIZE, value = "每页显示记录数", defaultValue = "10", paramType = "query", required = true, dataType = "int"),
            @ApiImplicitParam(name = PageConstant.ORDER_FIELD, value = "排序字段", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = PageConstant.ORDER, value = "排序方式，可选值(asc、desc)", paramType = "query", dataType = "String")
    })
    public Result<PageData<SuppliesRequestVO>> page(SuppliesRequestQuery query) {
        return Result.ok(requestService.getPage(query));
    }

    @PostMapping("/audit")
    @ApiOperation("审核领用申请")
    public Result<Void> audit(@RequestBody AuditRequest request) {
        requestService.auditRequest(request);
        return Result.ok();
    }

    @GetMapping("/{orderNo}")
    @ApiOperation("根据单号获取领用申请详情")
    public Result<SuppliesRequestVO> getByOrderNo(@PathVariable("orderNo") String orderNo) {
        return Result.ok(requestService.getByOrderNo(orderNo));
    }

    @GetMapping("/dashboard/dept-item-consumption")
    @ApiOperation("获取部门物品领用汇总")
    public Result<List<DeptItemConsumptionVO>> getDeptItemConsumption(ConsumptionQuery query) {
        return Result.ok(requestService.getDeptItemConsumption(query));
    }

    @GetMapping("/dashboard/consumption-details")
    @ApiOperation("获取人员领用明细")
    public Result<List<UserConsumptionDetailVO>> getConsumptionDetails(ConsumptionQuery query,
                                                                       @RequestParam("deptName") String deptName,
                                                                       @RequestParam("itemId") Long itemId) {
        return Result.ok(requestService.getConsumptionDetails(query, deptName, itemId));
    }
}
