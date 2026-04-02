package com.cac.oa.service.article;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cac.oa.entity.article.ArticleEntity;
import com.cac.oa.entity.article.ArticleViewRecordEntity;
import com.cac.oa.vo.article.ArticleQuery;
import com.cac.oa.vo.article.ArticleVO;
import com.cac.yiyan.common.page.PageData;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 文章 Service 接口
 */
public interface IArticleService extends IService<ArticleEntity> {

    /**
     * 分页查询文章
     */
    PageData<ArticleVO> getPage(ArticleQuery query);

    /**
     * 提交审批
     */
    void submitAudit(Long id);

    /**
     * 撤回审批
     */
    void withdraw(Long id);

    /**
     * 审批文章
     * @param id 文章ID
     * @param pass 是否通过
     * @param opinion 审核意见
     * @param isTop 是否置顶
     * @param topExpireTime 置顶截止时间
     * @param isReleaseTimed 是否定时发布
     * @param releaseTime 定时发布时间
     * @param isOfflineTimed 是否定时下线
     * @param offlineTime 定时下线时间
     */
    void audit(Long id, boolean pass, String opinion, Integer isTop, Date topExpireTime,
               Boolean isReleaseTimed, Date releaseTime, Boolean isOfflineTimed, Date offlineTime,
               Integer isMustRead);

    /**
     * 申请下线文章（需审批）
     */
    void applyOffline(Long id);

    /**
     * 下线文章
     */
    void offline(Long id);

    /**
     * 重新发布/上线文章
     */
    void publish(Long id);

    /**
     * 切换置顶状态
     */
    void toggleTop(Long id);
    
    /**
     * 获取审批列表分页
     */
    PageData<ArticleVO> getAuditPage(ArticleQuery query);

    /**
     * 获取文章详情
     */
    ArticleVO getDetail(Long id);

    /**
     * 获取作者文章全局统计数据
     */
    Map<String, Object> getMyStats(String authorCode);

    /**
     * 点赞文章 (幂等操作，已点赞则取消)
     */
    void likeArticle(Long id, String userCode);

    /**
     * 是否已点赞
     */
    boolean hasLiked(Long id, String userCode);

    /**
     * 收藏文章 (幂等操作，已收藏则取消)
     */
    void favoriteArticle(Long id, String userCode);

    /**
     * 是否已收藏
     */
    boolean hasFavorited(Long id, String userCode);

    /**
     * 分页查询我的收藏
     */
    PageData<ArticleVO> getFavoritePage(ArticleQuery query);

    /**
     * 清理过期置顶接口
     */
    void cleanExpiredTop();

    /**
     * 记录文章阅读
     * @param id 文章ID
     * @param userName 阅读人姓名
     * @param userCode 阅读人工号
     */
    void recordView(Long id, String userName, String userCode);

    /**
     * 获取所有阅读过该文章的人员记录
     */
    List<ArticleViewRecordEntity> getViewRecords(Long id);

    /**
     * 获取总门户看板数据
     */
    Map<String, Object> getDashboardStats();
}
