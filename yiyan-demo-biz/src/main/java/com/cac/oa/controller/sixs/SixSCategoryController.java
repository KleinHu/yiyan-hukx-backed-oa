package com.cac.oa.controller.sixs;

import com.cac.oa.convert.sixs.SixSCategoryConvert;
import com.cac.oa.entity.sixs.SixSCategoryEntity;
import com.cac.oa.service.sixs.SixSCategoryService;
import com.cac.oa.vo.sixs.SixSCategoryQuery;
import com.cac.oa.vo.sixs.SixSCategoryVO;
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

import java.util.Collection;
import java.util.List;

/**
 * 6S 标准化分类 Controller
 *
 * @author
 * @since
 */
@Api(tags = "6S标准化分类")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/240/oa/sixs/category")
public class SixSCategoryController {

    private final SixSCategoryService sixSCategoryService;

    private final SixSCategoryConvert converter = SixSCategoryConvert.INSTANCE;

    @GetMapping("/{id}")
    @ApiOperation("根据id获取")
    @ApiImplicitParam(name = "id", value = "主键", required = true, example = "1", dataTypeClass = Long.class)
    @PreAuthorize("@ss.hasPermission('sixs:category:query')")
    public Result<SixSCategoryVO> getSixSCategory(@PathVariable("id") Long id) {
        SixSCategoryEntity entity = sixSCategoryService.getSixSCategoryById(id);
        return Result.ok(converter.convert(entity));
    }

    @GetMapping("/list")
    @ApiOperation("获取列表（不分页）")
    @PreAuthorize("@ss.hasPermission('sixs:category:query')")
    public Result<List<SixSCategoryVO>> getSixSCategoryList(SixSCategoryQuery query) {
        List<SixSCategoryEntity> list = sixSCategoryService.getSixSCategoryList(query);
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
    @PreAuthorize("@ss.hasPermission('sixs:category:page')")
    public Result<PageData<SixSCategoryVO>> page(SixSCategoryQuery query) {
        PageData<SixSCategoryEntity> pageData = sixSCategoryService.getSixSCategoryPage(query);
        return Result.ok(converter.convertPage(pageData));
    }

    @PostMapping
    @ApiOperation("新增")
    @PreAuthorize("@ss.hasPermission('sixs:category:save')")
    public Result<Long> save(@RequestBody SixSCategoryVO vo) {
        SixSCategoryEntity entity = converter.convert(vo);
        Long id = sixSCategoryService.saveSixSCategory(entity);
        return Result.ok(id);
    }

    @PutMapping
    @ApiOperation("修改")
    @PreAuthorize("@ss.hasPermission('sixs:category:update')")
    public Result<Boolean> update(@RequestBody SixSCategoryVO vo) {
        SixSCategoryEntity entity = converter.convert(vo);
        Boolean success = sixSCategoryService.updateSixSCategory(entity);
        return Result.ok(success);
    }

    @DeleteMapping("/{id}")
    @ApiOperation("删除")
    @PreAuthorize("@ss.hasPermission('sixs:category:delete')")
    public Result<Boolean> delete(@PathVariable("id") Long id) {
        Boolean success = sixSCategoryService.deleteSixSCategoryById(id);
        return Result.ok(success);
    }

    @DeleteMapping
    @ApiOperation("批量删除")
    @PreAuthorize("@ss.hasPermission('sixs:category:delete')")
    public Result<Boolean> deleteBatch(@RequestBody Collection<Long> ids) {
        Boolean success = sixSCategoryService.deleteSixSCategoryByIds(ids);
        return Result.ok(success);
    }
}
