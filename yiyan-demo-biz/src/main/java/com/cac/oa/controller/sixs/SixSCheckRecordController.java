package com.cac.oa.controller.sixs;

import com.cac.oa.convert.sixs.SixSCheckRecordConvert;
import com.cac.oa.entity.sixs.SixSCheckRecordEntity;
import com.cac.oa.service.sixs.SixSCheckRecordService;
import com.cac.oa.vo.sixs.SixSCheckRecordQuery;
import com.cac.oa.vo.sixs.SixSCheckRecordVO;
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
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Collection;
import java.util.List;

/**
 * 6S 检查记录 Controller（含积分）
 *
 * @author
 * @since
 */
@Api(tags = "6S检查记录")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/240/oa/sixs/check-record")
public class SixSCheckRecordController {

    private final SixSCheckRecordService sixSCheckRecordService;

    private final SixSCheckRecordConvert converter = SixSCheckRecordConvert.INSTANCE;

    @GetMapping("/{id}")
    @ApiOperation("根据id获取")
    @ApiImplicitParam(name = "id", value = "主键", required = true, example = "1", dataTypeClass = Long.class)
    @PreAuthorize("@ss.hasPermission('sixs:check-record:query')")
    public Result<SixSCheckRecordVO> getSixSCheckRecord(@PathVariable("id") Long id) {
        SixSCheckRecordEntity entity = sixSCheckRecordService.getSixSCheckRecordById(id);
        SixSCheckRecordVO vo = converter.convert(entity);
        sixSCheckRecordService.fillCategoryNames(Collections.singletonList(vo));
        return Result.ok(vo);
    }

    @GetMapping("/list")
    @ApiOperation("获取列表（不分页）")
    @PreAuthorize("@ss.hasPermission('sixs:check-record:query')")
    public Result<List<SixSCheckRecordVO>> getSixSCheckRecordList(SixSCheckRecordQuery query) {
        List<SixSCheckRecordEntity> list = sixSCheckRecordService.getSixSCheckRecordList(query);
        List<SixSCheckRecordVO> voList = converter.convertList(list);
        sixSCheckRecordService.fillCategoryNames(voList);
        return Result.ok(voList);
    }

    @GetMapping("/page")
    @ApiOperation("分页查询")
    @ApiImplicitParams({
        @ApiImplicitParam(name = PageConstant.PAGE_NO, value = "当前页码，从1开始", defaultValue = "1", paramType = "query", required = true, dataType = "int"),
        @ApiImplicitParam(name = PageConstant.PAGE_SIZE, value = "每页显示记录数", defaultValue = "10", paramType = "query", required = true, dataType = "int"),
        @ApiImplicitParam(name = PageConstant.ORDER_FIELD, value = "排序字段", paramType = "query", dataType = "String"),
        @ApiImplicitParam(name = PageConstant.ORDER, value = "排序方式，可选值(asc、desc)", paramType = "query", dataType = "String")
    })
    @PreAuthorize("@ss.hasPermission('sixs:check-record:page')")
    public Result<PageData<SixSCheckRecordVO>> page(SixSCheckRecordQuery query) {
        PageData<SixSCheckRecordEntity> pageData = sixSCheckRecordService.getSixSCheckRecordPage(query);
        PageData<SixSCheckRecordVO> voPage = converter.convertPage(pageData);
        sixSCheckRecordService.fillCategoryNames(voPage.getList());
        return Result.ok(voPage);
    }

    @PostMapping
    @ApiOperation("新增检查记录（自动扣分并更新台账总积分）")
    @PreAuthorize("@ss.hasPermission('sixs:check-record:save')")
    public Result<Long> save(@RequestBody SixSCheckRecordVO vo) {
        SixSCheckRecordEntity entity = converter.convert(vo);
        Long id = sixSCheckRecordService.saveSixSCheckRecord(entity);
        return Result.ok(id);
    }

    @PutMapping
    @ApiOperation("修改")
    @PreAuthorize("@ss.hasPermission('sixs:check-record:update')")
    public Result<Boolean> update(@RequestBody SixSCheckRecordVO vo) {
        SixSCheckRecordEntity entity = converter.convert(vo);
        Boolean success = sixSCheckRecordService.updateSixSCheckRecord(entity);
        return Result.ok(success);
    }

    @DeleteMapping("/{id}")
    @ApiOperation("删除")
    @PreAuthorize("@ss.hasPermission('sixs:check-record:delete')")
    public Result<Boolean> delete(@PathVariable("id") Long id) {
        Boolean success = sixSCheckRecordService.deleteSixSCheckRecordById(id);
        return Result.ok(success);
    }

    @DeleteMapping
    @ApiOperation("批量删除")
    @PreAuthorize("@ss.hasPermission('sixs:check-record:delete')")
    public Result<Boolean> deleteBatch(@RequestBody Collection<Long> ids) {
        Boolean success = sixSCheckRecordService.deleteSixSCheckRecordByIds(ids);
        return Result.ok(success);
    }
}
