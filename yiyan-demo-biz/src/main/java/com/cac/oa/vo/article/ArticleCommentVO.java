package com.cac.oa.vo.article;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.io.Serializable;
import java.util.Date;

/**
 * 评论 VO
 */
@Data
@ApiModel("文章评论信息")
public class ArticleCommentVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("ID")
    private Long id;

    @ApiModelProperty("文章ID")
    private Long articleId;

    @ApiModelProperty("评论内容")
    private String content;

    @ApiModelProperty("评论人名称")
    private String userName;

    @ApiModelProperty("评论人工号")
    private String userCode;

    @ApiModelProperty("评论时间")
    private Date createTime;
}
