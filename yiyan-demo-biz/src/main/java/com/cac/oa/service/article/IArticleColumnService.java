package com.cac.oa.service.article;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cac.oa.entity.article.ArticleColumnEntity;
import com.cac.oa.vo.article.ArticleColumnQuery;
import com.cac.oa.vo.article.ArticleColumnVO;
import com.cac.yiyan.common.page.PageData;
import java.util.List;

/**
 * 文章专栏 Service 接口
 */
public interface IArticleColumnService extends IService<ArticleColumnEntity> {

    /**
     * 分页查询专栏
     */
    PageData<ArticleColumnVO> getPage(ArticleColumnQuery query);

    /**
     * 获取所有启用的专栏
     */
    List<ArticleColumnVO> getListAll();
    /**
     * 获取专栏树
     */
    List<ArticleColumnVO> getListTree(ArticleColumnQuery query);

    /**
     * 获取指定专栏及其所有后代专栏的 ID 列表
     *
     * @param columnId 专栏 ID
     * @return 包含该专栏及所有后代专栏 ID 的列表
     */
    List<Long> getChildIds(Long columnId);
}
