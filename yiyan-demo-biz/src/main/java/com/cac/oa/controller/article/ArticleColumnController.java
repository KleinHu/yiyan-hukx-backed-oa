package com.cac.oa.controller.article;

import com.cac.oa.convert.article.ArticleColumnConvert;
import com.cac.oa.entity.article.ArticleColumnEntity;
import com.cac.oa.service.article.IArticleColumnService;
import com.cac.oa.vo.article.ArticleColumnQuery;
import com.cac.oa.vo.article.ArticleColumnVO;
import com.cac.yiyan.common.page.PageData;
import com.cac.yiyan.common.vo.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 文章专栏 Controller
 */
@Api(tags = "OA-文章专栏管理")
@RestController
@RequestMapping("/api/240/oa/article-column")
@RequiredArgsConstructor
public class ArticleColumnController {

    private final IArticleColumnService columnService;
    private final ArticleColumnConvert converter = ArticleColumnConvert.INSTANCE;

    @GetMapping("/page")
    @ApiOperation("分页查询专栏列表")
    public Result<PageData<ArticleColumnVO>> page(ArticleColumnQuery query) {
        return Result.ok(columnService.getPage(query));
    }

    @GetMapping("/list-all")
    @ApiOperation("查询所有已启用的专栏")
    public Result<List<ArticleColumnVO>> listAll() {
        return Result.ok(columnService.getListAll());
    }

    @GetMapping("/list-tree")
    @ApiOperation("查询专栏树")
    public Result<List<ArticleColumnVO>> listTree(ArticleColumnQuery query) {
        return Result.ok(columnService.getListTree(query));
    }

    @PostMapping
    @ApiOperation("新增专栏")
    public Result<Boolean> save(@RequestBody ArticleColumnVO vo) {
        ArticleColumnEntity entity = converter.convert(vo);
        return Result.ok(columnService.save(entity));
    }

    @PutMapping
    @ApiOperation("修改专栏")
    public Result<Boolean> update(@RequestBody ArticleColumnVO vo) {
        ArticleColumnEntity entity = converter.convert(vo);
        return Result.ok(columnService.updateById(entity));
    }

    @DeleteMapping("/{id}")
    @ApiOperation("删除专栏")
    public Result<Boolean> delete(@PathVariable Long id) {
        // TODO: 校验该专栏下是否还有文章，有则禁止删除或提示级联处理
        return Result.ok(columnService.removeById(id));
    }

    @PutMapping("/{id}/status")
    @ApiOperation("更新专栏状态(启用/禁用)")
    public Result<Boolean> updateStatus(@PathVariable Long id, @RequestParam Integer status) {
        ArticleColumnEntity entity = new ArticleColumnEntity();
        entity.setId(id);
        entity.setStatus(status);
        return Result.ok(columnService.updateById(entity));
    }
}
