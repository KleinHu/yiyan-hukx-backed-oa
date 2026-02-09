package com.cac.oa.vo.supplies;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 人员领用明细 VO
 */
@Data
@ApiModel("人员领用明细")
public class UserConsumptionDetailVO {

    @ApiModelProperty("姓名")
    private String userName;

    @ApiModelProperty("工号")
    private String userCode;

    @ApiModelProperty("申请单号")
    private String orderNo;

    @ApiModelProperty("申请时间")
    private LocalDateTime applyTime;

    @ApiModelProperty("实际发放数量")
    private Integer quantity;
}
