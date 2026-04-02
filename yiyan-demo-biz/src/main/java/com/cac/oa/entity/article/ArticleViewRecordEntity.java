package com.cac.oa.entity.article;

import com.baomidou.mybatisplus.annotation.*;
import com.cac.yiyan.mybatis.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.util.Date;

/**
 * 文章阅读记录实体
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("article_view_record")
public class ArticleViewRecordEntity extends BaseEntity {
    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 文章ID
     */
    private Long articleId;

    /**
     * 阅读人员ID
     */
    private Long userId;

    /**
     * 阅读人员姓名
     */
    private String userName;

    /**
     * 阅读人员工号
     */
    private String userCode;

    /**
     * 阅读时间
     */
    private Date viewTime;
}
