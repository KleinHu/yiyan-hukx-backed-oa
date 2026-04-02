package com.cac.oa.service.article.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cac.oa.dao.article.ArticleReceiptMapper;
import com.cac.oa.entity.article.ArticleReceiptEntity;
import com.cac.oa.service.article.IArticleReceiptService;
import com.cac.yiyan.common.page.PageData;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 必读回执服务实现类
 */
@Service
@RequiredArgsConstructor
public class ArticleReceiptServiceImpl extends ServiceImpl<ArticleReceiptMapper, ArticleReceiptEntity> implements IArticleReceiptService {

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void submitReceipt(ArticleReceiptEntity receipt) {
        // 先查是否已经回执过了
        long count = this.count(new LambdaQueryWrapper<ArticleReceiptEntity>()
                .eq(ArticleReceiptEntity::getArticleId, receipt.getArticleId())
                .eq(ArticleReceiptEntity::getUserId, receipt.getUserId()));
        
        if (count == 0) {
            this.save(receipt);
        }
    }

    @Override
    public boolean hasRead(Long articleId, String userId) {
        if (userId == null || userId.isEmpty()) {
            return false;
        }
        return this.count(new LambdaQueryWrapper<ArticleReceiptEntity>()
                .eq(ArticleReceiptEntity::getArticleId, articleId)
                .eq(ArticleReceiptEntity::getUserId, userId)) > 0;
    }

    @Override
    public PageData<ArticleReceiptEntity> getReceiptPage(Long articleId, Integer pageNo, Integer pageSize) {
        Page<ArticleReceiptEntity> page = new Page<>(pageNo == null ? 1 : pageNo, pageSize == null ? 10 : pageSize);
        Page<ArticleReceiptEntity> resultPage = this.page(page, new LambdaQueryWrapper<ArticleReceiptEntity>()
                .eq(ArticleReceiptEntity::getArticleId, articleId)
                .orderByDesc(ArticleReceiptEntity::getCreateTime));
        
        PageData<ArticleReceiptEntity> pageData = new PageData<>();
        pageData.setList(resultPage.getRecords());
        pageData.setTotal(resultPage.getTotal());
        return pageData;
    }
}
