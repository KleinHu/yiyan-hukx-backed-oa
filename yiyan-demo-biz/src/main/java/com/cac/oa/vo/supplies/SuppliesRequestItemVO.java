package com.cac.oa.vo.supplies;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 领用申请明细 VO
 */
@Data
@ApiModel("领用申请明细")
public class SuppliesRequestItemVO {

    @ApiModelProperty("ID")
    private Long id;

    @ApiModelProperty("申请主表ID")
    private Long requestId;

    @ApiModelProperty("物品ID")
    private Long itemId;

    @ApiModelProperty("物品名称")
    private String itemName;

    @ApiModelProperty("规格型号")
    private String spec;

    @ApiModelProperty("单位")
    private String unit;

    @ApiModelProperty("申请数量")
    private Integer quantity;

    @ApiModelProperty("实际发放数量")
    private Integer issuedQuantity;
}
