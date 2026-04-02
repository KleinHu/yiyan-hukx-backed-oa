package com.cac.oa.entity.article;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import java.io.Serializable;
import java.util.Date;

/**
 * 文章评论实体
 */
@Data
@TableName("article_comment")
public class ArticleCommentEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 关联文章ID
     */
    private Long articleId;

    /**
     * 评论内容
     */
    private String content;

    /**
     * 评论人名称
     */
    private String userName;

    /**
     * 评论人工号
     */
    private String userCode;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    /**
     * 删除状态 (0-正常, 1-已删除)
     */
    @TableLogic
    private Integer deleteStatus;
}
