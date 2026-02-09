package com.cac.oa.controller.supplies;

import com.cac.oa.convert.supplies.SuppliesCategoryConvert;
import com.cac.oa.entity.supplies.SuppliesCategoryEntity;
import com.cac.oa.service.supplies.ISuppliesCategoryService;
import com.cac.oa.vo.supplies.SuppliesCategoryVO;
import com.cac.yiyan.common.vo.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 办公用品分类 Controller
 */
@Api(tags = "办公用品-分类管理")
@RestController
@RequestMapping("/api/240/oa/supplies/category")
@RequiredArgsConstructor
public class SuppliesCategoryController {

    private final ISuppliesCategoryService categoryService;
    private final SuppliesCategoryConvert converter = SuppliesCategoryConvert.INSTANCE;

    @GetMapping("/tree")
    @ApiOperation("获取分类树")
    public Result<List<SuppliesCategoryVO>> tree() {
        return Result.ok(categoryService.listTree());
    }

    @PostMapping
    @ApiOperation("新增分类")
    public Result<Boolean> save(@RequestBody SuppliesCategoryVO vo) {
        SuppliesCategoryEntity entity = converter.convert(vo);
        return Result.ok(categoryService.save(entity));
    }

    @PutMapping
    @ApiOperation("修改分类")
    public Result<Boolean> update(@RequestBody SuppliesCategoryVO vo) {
        SuppliesCategoryEntity entity = converter.convert(vo);
        return Result.ok(categoryService.updateById(entity));
    }

    @DeleteMapping("/{id}")
    @ApiOperation("删除分类")
    public Result<Boolean> delete(@PathVariable Long id) {
        return Result.ok(categoryService.removeById(id));
    }
}
