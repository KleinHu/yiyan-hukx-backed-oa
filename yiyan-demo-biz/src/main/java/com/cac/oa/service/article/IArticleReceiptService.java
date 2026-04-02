package com.cac.oa.service.article;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cac.oa.entity.article.ArticleReceiptEntity;
import com.cac.yiyan.common.page.PageData;

public interface IArticleReceiptService extends IService<ArticleReceiptEntity> {
    
    /**
     * 提交已阅回执
     */
    void submitReceipt(ArticleReceiptEntity receipt);

    /**
     * 查询当前用户是否已读
     */
    boolean hasRead(Long articleId, String userId);

    /**
     * 分页查询某文章的回执记录
     */
    PageData<ArticleReceiptEntity> getReceiptPage(Long articleId, Integer pageNo, Integer pageSize);
}
