package com.cac.oa.controller.article;

import com.cac.oa.convert.article.ArticleConvert;
import com.cac.oa.entity.article.ArticleEntity;
import com.cac.oa.entity.article.ArticleViewRecordEntity;
import com.cac.oa.entity.article.ArticleReceiptEntity;
import com.cac.oa.service.article.IArticleService;
import com.cac.oa.service.article.IArticleReceiptService;
import com.cac.oa.vo.article.ArticleQuery;
import com.cac.oa.vo.article.ArticleVO;
import com.cac.yiyan.common.page.PageData;
import com.cac.yiyan.common.vo.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 文章 Controller
 */
@Api(tags = "OA-文章管理")
@RestController
@RequestMapping("/api/240/oa/article")
@RequiredArgsConstructor
public class ArticleController {

    private final IArticleService articleService;
    private final IArticleReceiptService receiptService;
    private final ArticleConvert converter = ArticleConvert.INSTANCE;

    @GetMapping("/page")
    @ApiOperation("分页查询文章")
    public Result<PageData<ArticleVO>> page(ArticleQuery query) {
        return Result.ok(articleService.getPage(query));
    }

    @GetMapping("/audit/page")
    @ApiOperation("分页查询待审批文章")
    public Result<PageData<ArticleVO>> auditPage(ArticleQuery query) {
        return Result.ok(articleService.getAuditPage(query));
    }

    @GetMapping("/{id}")
    @ApiOperation("获取文章详情")
    public Result<ArticleVO> get(@PathVariable Long id) {
        return Result.ok(articleService.getDetail(id));
    }

    @PostMapping
    @ApiOperation("新增文章")
    public Result<Long> save(@RequestBody ArticleVO vo) {
        ArticleEntity entity = converter.convert(vo);
        articleService.save(entity);
        return Result.ok(entity.getId());
    }

    @PutMapping
    @ApiOperation("更新文章")
    public Result<Boolean> update(@RequestBody ArticleVO vo) {
        ArticleEntity entity = converter.convert(vo);
        return Result.ok(articleService.updateById(entity));
    }

    @DeleteMapping("/{id}")
    @ApiOperation("物理删除文章")
    public Result<Boolean> delete(@PathVariable Long id) {
        return Result.ok(articleService.removeById(id));
    }

    @PutMapping("/{id}/submit-audit")
    @ApiOperation("提交送审")
    public Result<Void> submitAudit(@PathVariable Long id) {
        articleService.submitAudit(id);
        return Result.ok();
    }
    @PutMapping("/{id}/withdraw")
    @ApiOperation("撤回审批")
    public Result<Void> withdraw(@PathVariable Long id) {
        articleService.withdraw(id);
        return Result.ok();
    }

    @PutMapping("/{id}/audit")
    @ApiOperation("审核文章")
    public Result<Void> audit(@PathVariable Long id,
                             @RequestParam boolean pass,
                             @RequestParam(required = false) String opinion,
                             @RequestParam(required = false) Integer isTop,
                             @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date topExpireTime,
                             @RequestParam(required = false) Boolean isReleaseTimed,
                             @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date releaseTime,
                             @RequestParam(required = false) Boolean isOfflineTimed,
                             @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date offlineTime,
                             @RequestParam(required = false) Integer isMustRead) {
        articleService.audit(id, pass, opinion, isTop, topExpireTime, isReleaseTimed, releaseTime, isOfflineTimed, offlineTime, isMustRead);
        return Result.ok();
    }

    @PutMapping("/{id}/offline")
    @ApiOperation("下线文章")
    public Result<Void> offline(@PathVariable Long id) {
        articleService.offline(id);
        return Result.ok();
    }

    @PutMapping("/{id}/top")
    @ApiOperation("设置/取消置顶")
    public Result<Void> toggleTop(@PathVariable Long id) {
        articleService.toggleTop(id);
        return Result.ok();
    }

    @PutMapping("/{id}/publish")
    @ApiOperation("重新发布文章")
    public Result<Void> publish(@PathVariable Long id) {
        articleService.publish(id);
        return Result.ok();
    }

    @PutMapping("/{id}/applyOffline")
    @ApiOperation("申请下线文章")
    public Result<Void> applyOffline(@PathVariable Long id) {
        articleService.applyOffline(id);
        return Result.ok();
    }

    @GetMapping("/my-stats")
    @ApiOperation("获取我的文章统计")
    public Result<Map<String, Object>> getMyStats(@RequestParam String authorCode) {
        return Result.ok(articleService.getMyStats(authorCode));
    }

    @GetMapping("/dashboard-stats")
    @ApiOperation("获取门户首页数据面板统计")
    public Result<Map<String, Object>> getDashboardStats() {
        return Result.ok(articleService.getDashboardStats());
    }

    @PostMapping("/{id}/view")
    @ApiOperation("记录文章阅读")
    public Result<Void> recordView(@PathVariable Long id, @RequestParam String userName, @RequestParam String userCode) {
        articleService.recordView(id, userName, userCode);
        return Result.ok();
    }

    @GetMapping("/{id}/view-records")
    @ApiOperation("获取阅读记录名单")
    public Result<List<ArticleViewRecordEntity>> getViewRecords(@PathVariable Long id) {
        return Result.ok(articleService.getViewRecords(id));
    }

    @PutMapping("/{id}/like")
    @ApiOperation("点赞文章")
    public Result<Void> likeArticle(@PathVariable Long id, @RequestParam String userCode) {
        articleService.likeArticle(id, userCode);
        return Result.ok();
    }

    @GetMapping("/{id}/has-liked")
    @ApiOperation("查询是否已点赞")
    public Result<Boolean> hasLiked(@PathVariable Long id, @RequestParam String userCode) {
        return Result.ok(articleService.hasLiked(id, userCode));
    }

    @PostMapping("/{id}/favorite")
    @ApiOperation("收藏文章")
    public Result<Void> favoriteArticle(@PathVariable Long id, @RequestParam String userCode) {
        articleService.favoriteArticle(id, userCode);
        return Result.ok();
    }

    @GetMapping("/{id}/has-favorited")
    @ApiOperation("查询是否已收藏")
    public Result<Boolean> hasFavorited(@PathVariable Long id, @RequestParam String userCode) {
        return Result.ok(articleService.hasFavorited(id, userCode));
    }

    @GetMapping("/favorite/page")
    @ApiOperation("分页查询我的收藏")
    public Result<PageData<ArticleVO>> getFavoritePage(ArticleQuery query) {
        return Result.ok(articleService.getFavoritePage(query));
    }

    @PostMapping("/receipt/{id}")
    @ApiOperation("提交已阅回执")
    public Result<Void> submitReceipt(@PathVariable Long id, @RequestBody ArticleReceiptEntity receipt) {
        receipt.setArticleId(id);
        receiptService.submitReceipt(receipt);
        return Result.ok();
    }

    @GetMapping("/receipt/status/{id}")
    @ApiOperation("查询当前用户是否已读")
    public Result<Boolean> hasRead(@PathVariable Long id, @RequestParam String userId) {
        return Result.ok(receiptService.hasRead(id, userId));
    }

    @GetMapping("/receipt/list/{id}")
    @ApiOperation("分页查询某文章的回执记录")
    public Result<PageData<ArticleReceiptEntity>> getReceiptPage(@PathVariable Long id,
                                                                 @RequestParam(required = false, defaultValue = "1") Integer pageNo,
                                                                 @RequestParam(required = false, defaultValue = "10") Integer pageSize) {
        return Result.ok(receiptService.getReceiptPage(id, pageNo, pageSize));
    }
}
