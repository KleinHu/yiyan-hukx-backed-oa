package com.cac.oa.controller.sixs;

import com.cac.oa.convert.sixs.SixSAccountConvert;
import com.cac.oa.entity.sixs.SixSAccountEntity;
import com.cac.oa.service.sixs.SixSAccountService;
import com.cac.oa.vo.sixs.SixSAccountBatchSaveRequest;
import com.cac.oa.vo.sixs.SixSAccountQuery;
import com.cac.oa.vo.sixs.SixSAccountStatisticsVO;
import com.cac.oa.vo.sixs.SixSAccountTrendVO;
import com.cac.oa.vo.sixs.SixSAccountVO;
import com.cac.yiyan.common.constant.PageConstant;
import com.cac.yiyan.common.page.PageData;
import com.cac.yiyan.common.vo.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 6S 积分台账 Controller
 *
 * @author
 * @since
 */
@Api(tags = "6S积分台账")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/240/oa/sixs/account")
public class SixSAccountController {

    private final SixSAccountService sixSAccountService;

    private final SixSAccountConvert converter = SixSAccountConvert.INSTANCE;

    @GetMapping("/statistics")
    @ApiOperation("获取统计数据")
    @ApiImplicitParam(name = "year", value = "年份", required = false, example = "2024", dataTypeClass = Integer.class)
    @PreAuthorize("@ss.hasPermission('sixs:account:query')")
    public Result<SixSAccountStatisticsVO> getStatistics(@RequestParam(value = "year", required = false) Integer year) {
        return Result.ok(sixSAccountService.getStatistics(year));
    }

    @GetMapping("/{id}")
    @ApiOperation("根据id获取")
    @ApiImplicitParam(name = "id", value = "主键", required = true, example = "1", dataTypeClass = Long.class)
    @PreAuthorize("@ss.hasPermission('sixs:account:query')")
    public Result<SixSAccountVO> getSixSAccount(@PathVariable("id") Long id) {
        SixSAccountEntity entity = sixSAccountService.getSixSAccountById(id);
        return Result.ok(converter.convert(entity));
    }

    @GetMapping("/trend/{id}")
    @ApiOperation("获取分数趋势图数据")
    @ApiImplicitParam(name = "id", value = "台账ID", required = true, example = "1", dataTypeClass = Long.class)
    @PreAuthorize("@ss.hasPermission('sixs:account:query')")
    public Result<List<SixSAccountTrendVO>> getTrend(@PathVariable("id") Long id) {
        return Result.ok(sixSAccountService.getTrend(id));
    }

    @GetMapping("/list")
    @ApiOperation("获取列表（不分页）")
    @PreAuthorize("@ss.hasPermission('sixs:account:query')")
    public Result<List<SixSAccountVO>> getSixSAccountList(SixSAccountQuery query) {
        List<SixSAccountEntity> list = sixSAccountService.getSixSAccountList(query);
        return Result.ok(converter.convertList(list));
    }

    @GetMapping("/page")
    @ApiOperation("分页查询")
    @ApiImplicitParams({
        @ApiImplicitParam(name = PageConstant.PAGE_NO, value = "当前页码，从1开始", defaultValue = "1", paramType = "query", required = true, dataType = "int"),
        @ApiImplicitParam(name = PageConstant.PAGE_SIZE, value = "每页显示记录数", defaultValue = "10", paramType = "query", required = true, dataType = "int"),
        @ApiImplicitParam(name = PageConstant.ORDER_FIELD, value = "排序字段", paramType = "query", dataType = "String"),
        @ApiImplicitParam(name = PageConstant.ORDER, value = "排序方式，可选值(asc、desc)", paramType = "query", dataType = "String")
    })
    @PreAuthorize("@ss.hasPermission('sixs:account:page')")
    public Result<PageData<SixSAccountVO>> page(SixSAccountQuery query) {
        PageData<SixSAccountEntity> pageData = sixSAccountService.getSixSAccountPage(query);
        return Result.ok(converter.convertPage(pageData));
    }

    @PostMapping
    @ApiOperation("新增")
    @PreAuthorize("@ss.hasPermission('sixs:account:save')")
    public Result<Long> save(@RequestBody SixSAccountVO vo) {
        SixSAccountEntity entity = converter.convert(vo);
        Long id = sixSAccountService.saveSixSAccount(entity);
        return Result.ok(id);
    }

    @PostMapping("/batch")
    @ApiOperation("批量新增（需传参数 year 指定台账年份）")
    @PreAuthorize("@ss.hasPermission('sixs:account:save')")
    public Result<Boolean> saveBatch(@Valid @RequestBody SixSAccountBatchSaveRequest request) {
        if (request.getList() == null || request.getList().isEmpty()) {
            return Result.error("台账列表不能为空");
        }
        Integer year = request.getYear();
        List<SixSAccountEntity> entities = request.getList().stream()
                .map(converter::convert)
                .collect(Collectors.toList());
        for (SixSAccountEntity e : entities) {
            e.setYear(year);
            if (e.getTotalScore() == null) {
                e.setTotalScore(100);
            }
            if (e.getStatus() == null) {
                e.setStatus(1);
            }
        }
        sixSAccountService.saveSixSAccountBatch(entities);
        return Result.ok(true);
    }

    @PutMapping
    @ApiOperation("修改")
    @PreAuthorize("@ss.hasPermission('sixs:account:update')")
    public Result<Boolean> update(@RequestBody SixSAccountVO vo) {
        SixSAccountEntity entity = converter.convert(vo);
        Boolean success = sixSAccountService.updateSixSAccount(entity);
        return Result.ok(success);
    }

    @DeleteMapping("/{id}")
    @ApiOperation("删除")
    @PreAuthorize("@ss.hasPermission('sixs:account:delete')")
    public Result<Boolean> delete(@PathVariable("id") Long id) {
        Boolean success = sixSAccountService.deleteSixSAccountById(id);
        return Result.ok(success);
    }

    @DeleteMapping
    @ApiOperation("批量删除")
    @PreAuthorize("@ss.hasPermission('sixs:account:delete')")
    public Result<Boolean> deleteBatch(@RequestBody Collection<Long> ids) {
        Boolean success = sixSAccountService.deleteSixSAccountByIds(ids);
        return Result.ok(success);
    }
}
