package com.cac.oa.entity.article;

import com.baomidou.mybatisplus.annotation.*;
import com.cac.yiyan.mybatis.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.util.Date;

/**
 * 文章内容实体
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("article")
public class ArticleEntity extends BaseEntity {
    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 所属专栏ID
     */
    private Long columnId;

    /**
     * 文章标题
     */
    private String title;

    /**
     * 文章摘要
     */
    private String summary;

    /**
     * 封面图
     */
    private String coverUrl;

    /**
     * 文章正文
     */
    private String content;

    /**
     * 附件(JSON格式)
     */
    private String attachments;

    /**
     * 文章标签(多个以逗号分隔)
     */
    private String tags;

    /**
     * 作者名称
     */
    private String authorName;

    /**
     * 作者工号
     */
    private String authorCode;

    /**
     * 定时发布时间
     */
    private Date releaseTime;

    /**
     * 定时下线时间
     */
    private Date offlineTime;

    /**
     * 发布时间
     */
    private Date publishTime;

    /**
     * 状态 (0-草稿，1-待审核，2-已发布，3-已驳回，4-已下线，5-待下线审核，6-定时发布)
     */
    private Integer status;

    /**
     * 浏览量
     */
    private Integer viewCount;

    /**
     * 点赞数
     */
    private Integer likeCount;
    
    /**
     * 收藏数
     */
    private Integer favoriteCount;

    /**
     * 是否置顶 (1-置顶，0-普通)
     */
    private Integer isTop;

    /**
     * 审核意见
     */
    private String auditOpinion;

    /**
     * 置顶截止时间
     */
    private Date topExpireTime;

    /**
     * 是否定时发布 (1-是，0-否)
     */
    private Boolean isReleaseTimed;

    /**
     * 是否定时下线 (1-是，0-否)
     */
    private Boolean isOfflineTimed;

    /**
     * 是否必读 (1-是，0-否)
     */
    private Integer isMustRead;
}
