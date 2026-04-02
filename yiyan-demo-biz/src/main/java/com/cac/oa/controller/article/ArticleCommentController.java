package com.cac.oa.controller.article;

import com.cac.oa.entity.article.ArticleCommentEntity;
import com.cac.oa.service.article.IArticleCommentService;
import com.cac.oa.vo.article.ArticleCommentQuery;
import com.cac.oa.vo.article.ArticleCommentVO;
import com.cac.yiyan.common.page.PageData;
import com.cac.yiyan.common.vo.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * 文章评论 Controller
 */
@Api(tags = "OA-文章评论管理")
@RestController
@RequestMapping("/api/240/oa/article/comment")
@RequiredArgsConstructor
public class ArticleCommentController {

    private final IArticleCommentService commentService;

    @GetMapping("/list")
    @ApiOperation("分页查询文章评论列表")
    public Result<PageData<ArticleCommentEntity>> list(ArticleCommentQuery query) {
        return Result.ok(commentService.getCommentPage(query));
    }

    @PostMapping
    @ApiOperation("发表评论")
    public Result<Void> save(@RequestBody com.cac.oa.entity.article.ArticleCommentEntity entity) {
        commentService.saveComment(entity);
        return Result.ok();
    }

    @DeleteMapping("/{id}")
    @ApiOperation("删除评论")
    public Result<Void> delete(@PathVariable Long id) {
        commentService.deleteComment(id);
        return Result.ok();
    }
}
