package com.cac.oa.vo.supplies;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 领用申请单 VO
 */
@Data
@ApiModel("领用申请单")
public class SuppliesRequestVO {

    @ApiModelProperty("ID")
    private Long id;

    @ApiModelProperty("申请单号")
    private String orderNo;

    @ApiModelProperty("领用人工号")
    private String userCode;

    @ApiModelProperty("领用人姓名")
    private String userName;

    @ApiModelProperty("领用部门名称")
    private String deptName;

    @ApiModelProperty("申请时间")
    private LocalDateTime applyTime;

    @ApiModelProperty("申请事由")
    private String reason;

    @ApiModelProperty("状态: 0待审核, 1通过, 2驳回, 3已发放")
    private Integer auditStatus;

    @ApiModelProperty("审核人姓名")
    private String auditorName;

    @ApiModelProperty("审核时间")
    private LocalDateTime auditTime;

    @ApiModelProperty("审核备注")
    private String remark;

    @ApiModelProperty("明细列表")
    private List<SuppliesRequestItemVO> items;
}
