package com.cac.oa.vo.article;

import com.cac.yiyan.common.page.PageParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 文章查询参数
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel("文章查询")
public class ArticleQuery extends PageParam {

    @ApiModelProperty("标题(模糊查询)")
    private String title;

    @ApiModelProperty("标签(模糊查询)")
    private String tag;

    @ApiModelProperty("归属专栏ID")
    private Long columnId;

    @ApiModelProperty("状态(0-草稿，1-已发布，2-已下线)")
    private Integer status;

    @ApiModelProperty("是否按热度排序(1-是)")
    private Integer hotSort;

    @ApiModelProperty("开始时间")
    private String beginTime;

    @ApiModelProperty("结束时间")
    private String endTime;

    @ApiModelProperty("作者工号(精准匹配)")
    private String authorCode;

    @ApiModelProperty("是否定时发布")
    private Boolean isReleaseTimed;

    @ApiModelProperty("是否定时下线")
    private Boolean isOfflineTimed;
}
