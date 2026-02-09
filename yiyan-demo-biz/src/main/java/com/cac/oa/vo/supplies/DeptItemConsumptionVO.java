package com.cac.oa.vo.supplies;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 部门物品领用汇总 VO
 */
@Data
@ApiModel("部门物品领用汇总")
public class DeptItemConsumptionVO {

    @ApiModelProperty("部门名称")
    private String deptName;

    @ApiModelProperty("物品ID")
    private Long itemId;

    @ApiModelProperty("物品名称")
    private String itemName;

    @ApiModelProperty("规格型号")
    private String spec;

    @ApiModelProperty("领用发放总量")
    private Integer totalQuantity;

    @ApiModelProperty("物品分类ID")
    private String categoryId;
}
