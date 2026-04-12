package com.cac.oa.controller.documents;

import com.cac.oa.entity.documents.DocumentCategoryEntity;
import com.cac.oa.service.documents.DocumentCategoryService;
import com.cac.oa.vo.documents.DocumentCategoryTreeVO;
import com.cac.yiyan.common.vo.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 文档分类 Controller
 */
@Api(tags = "OA-文档分类管理")
@RestController
@RequestMapping("/api/240/oa/document-category")
@RequiredArgsConstructor
public class DocumentCategoryController {

    private final DocumentCategoryService categoryService;

    @GetMapping("/tree")
    @ApiOperation("获取全部分类树")
    public Result<List<DocumentCategoryTreeVO>> getTree() {
        return Result.ok(categoryService.getCategoryTree());
    }

    @PostMapping
    @ApiOperation("新增分类")
    public Result<Boolean> save(@RequestBody DocumentCategoryEntity entity) {
        return Result.ok(categoryService.save(entity));
    }

    @PutMapping
    @ApiOperation("更新分类")
    public Result<Boolean> update(@RequestBody DocumentCategoryEntity entity) {
        return Result.ok(categoryService.updateById(entity));
    }

    @DeleteMapping("/{id}")
    @ApiOperation("删除分类")
    public Result<Boolean> delete(@PathVariable Long id) {
        return Result.ok(categoryService.removeById(id));
    }

}
