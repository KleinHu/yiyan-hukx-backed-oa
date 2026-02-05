package com.cac.oa.vo.sixs;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * 6S 检查记录 VO（含积分）
 *
 * @author
 * @since
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "SixSCheckRecordVO", description = "6S检查记录（含积分）")
public class SixSCheckRecordVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键ID")
    private Long id;

    @ApiModelProperty("台账ID")
    private Long accountId;

    @ApiModelProperty("分类ID")
    private Long categoryId;

    @ApiModelProperty("分类名称")
    private String categoryName;

    @ApiModelProperty("检查日期")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate checkDate;

    @ApiModelProperty("问题描述")
    private String problemDescription;

    @ApiModelProperty("检查人工号")
    private String checkerCode;

    @ApiModelProperty("检查人姓名")
    private String checkerName;

    @ApiModelProperty("图片URL，多个逗号分隔")
    private String images;

    @ApiModelProperty("扣分值")
    private Integer scoreDeducted;

    @ApiModelProperty("扣分前台账积分")
    private Integer beforeScore;

    @ApiModelProperty("扣分后台账积分")
    private Integer afterScore;
}
