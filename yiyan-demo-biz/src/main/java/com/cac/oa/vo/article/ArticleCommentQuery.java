package com.cac.oa.vo.article;

import com.cac.yiyan.common.page.PageParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 文章评论查询参数
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel("文章评论查询")
public class ArticleCommentQuery extends PageParam {

    @ApiModelProperty("文章ID")
    private Long articleId;
}
