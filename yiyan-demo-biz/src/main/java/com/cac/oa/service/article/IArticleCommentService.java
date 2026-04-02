package com.cac.oa.service.article;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cac.oa.entity.article.ArticleCommentEntity;
import com.cac.oa.vo.article.ArticleCommentQuery;
import com.cac.yiyan.common.page.PageData;
import java.util.List;

/**
 * 文章评论服务接口
 */
public interface IArticleCommentService extends IService<ArticleCommentEntity> {
    /**
     * 获取文章评论列表 (分页)
     */
    PageData<ArticleCommentEntity> getCommentPage(ArticleCommentQuery query);

    /**
     * 发表评论
     */
    void saveComment(ArticleCommentEntity entity);

    /**
     * 删除评论 (逻辑删除)
     */
    void deleteComment(Long id);
}
