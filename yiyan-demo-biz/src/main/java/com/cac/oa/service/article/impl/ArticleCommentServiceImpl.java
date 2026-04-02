package com.cac.oa.service.article.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cac.oa.dao.article.ArticleCommentMapper;
import com.cac.oa.entity.article.ArticleCommentEntity;
import com.cac.oa.service.article.IArticleCommentService;
import com.cac.oa.vo.article.ArticleCommentQuery;
import com.cac.oa.vo.article.ArticleCommentVO;
import com.cac.yiyan.common.page.PageData;
import com.cac.yiyan.mybatis.query.LambdaQueryWrapperX;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 文章评论服务实现类
 */
@Service
@RequiredArgsConstructor
public class ArticleCommentServiceImpl extends ServiceImpl<ArticleCommentMapper, ArticleCommentEntity> implements IArticleCommentService {

    @Override
    public PageData<ArticleCommentEntity> getCommentPage(ArticleCommentQuery query) {
        return baseMapper.selectPage(query, new LambdaQueryWrapperX<ArticleCommentEntity>()
                .eqIfPresent(ArticleCommentEntity::getArticleId, query.getArticleId())
                .orderByDesc(ArticleCommentEntity::getCreateTime));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveComment(ArticleCommentEntity entity) {
        entity.setCreateTime(new Date());
        this.save(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteComment(Long id) {
        this.removeById(id);
    }
}
