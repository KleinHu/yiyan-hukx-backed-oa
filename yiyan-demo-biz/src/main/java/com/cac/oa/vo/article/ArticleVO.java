package com.cac.oa.vo.article;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.io.Serializable;
import java.util.Date;

/**
 * 文章 VO
 */
@Data
@ApiModel("文章信息")
public class ArticleVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("ID")
    private Long id;

    @ApiModelProperty("所属专栏ID")
    private Long columnId;

    @ApiModelProperty("文章标题")
    private String title;

    @ApiModelProperty("文章摘要")
    private String summary;

    @ApiModelProperty("封面图")
    private String coverUrl;

    @ApiModelProperty("文章正文")
    private String content;

    @ApiModelProperty("附件(JSON格式)")
    private String attachments;

    @ApiModelProperty("文章标签(多个以逗号分隔)")
    private String tags;

    @ApiModelProperty("作者名称")
    private String authorName;

    @ApiModelProperty("作者工号")
    private String authorCode;

    @ApiModelProperty("定时发布时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date releaseTime;

    @ApiModelProperty("定时下线时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date offlineTime;

    @ApiModelProperty("发布时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date publishTime;

    @ApiModelProperty("状态(0-草稿，1-待审核，2-已发布，3-已驳回，4-已下线，5-待下线审核，6-定时发布)")
    private Integer status;

    @ApiModelProperty("浏览量")
    private Integer viewCount;

    @ApiModelProperty("点赞数")
    private Integer likeCount;

    @ApiModelProperty("收藏数")
    private Integer favoriteCount;

    @ApiModelProperty("是否置顶(1-置顶，0-普通)")
    private Integer isTop;

    @ApiModelProperty("创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    @ApiModelProperty("是否定时发布(1-是，0-否)")
    private Boolean isReleaseTimed;

    @ApiModelProperty("是否定时下线(1-是，0-否)")
    private Boolean isOfflineTimed;

    @ApiModelProperty("是否必读(1-是，0-否)")
    private Integer isMustRead;
}
