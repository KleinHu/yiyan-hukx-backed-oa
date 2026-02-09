package com.cac.oa.vo.supplies;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 领用审核请求
 */
@Data
@ApiModel("领用审核请求")
public class AuditRequest {

    @ApiModelProperty("申请单ID")
    private Long id;

    @ApiModelProperty("审核状态: 1通过, 2驳回")
    private Integer status;

    @ApiModelProperty("审核备注")
    private String remark;

    @ApiModelProperty("实发详情 (通过时如果不传，默认按申请数量)")
    private java.util.List<SuppliesRequestItemVO> items;

    @ApiModelProperty("操作人姓名")
    private String operatorName;

    @ApiModelProperty("操作人工号")
    private String operatorCode;
}
