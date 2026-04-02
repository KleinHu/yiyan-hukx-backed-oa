package com.cac.oa.entity.article;

import com.baomidou.mybatisplus.annotation.*;
import com.cac.yiyan.mybatis.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.util.Date;

/**
 * 文章点赞记录实体
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("article_like_record")
public class ArticleLikeRecordEntity extends BaseEntity {
    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 文章ID
     */
    private Long articleId;

    /**
     * 用户工号
     */
    private String userCode;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;
}
