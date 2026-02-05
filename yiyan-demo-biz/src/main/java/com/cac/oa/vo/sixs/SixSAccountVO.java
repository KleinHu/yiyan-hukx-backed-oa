package com.cac.oa.vo.sixs;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.io.Serializable;

/**
 * 6S 积分台账 VO
 *
 * @author
 * @since
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "SixSAccountVO", description = "6S积分台账")
public class SixSAccountVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键ID")
    private Long id;

    @ApiModelProperty("工号")
    private String userCode;

    @ApiModelProperty("用户姓名")
    private String userName;

    @ApiModelProperty("部门ID（来自HR系统）")
    private String departmentId;

    @ApiModelProperty("部门名称")
    private String departmentName;

    @ApiModelProperty("年度（每年度重置积分为100分）")
    private Integer year;

    @ApiModelProperty("总积分")
    private Integer totalScore;

    @ApiModelProperty("状态：0禁用 1启用")
    private Integer status;

    @ApiModelProperty("备注")
    private String remark;
}
