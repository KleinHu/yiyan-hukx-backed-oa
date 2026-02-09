package com.cac.oa.vo.supplies;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 办公用品档案 VO
 */
@Data
@ApiModel("办公用品档案")
public class SuppliesItemVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("ID")
    private Long id;

    @ApiModelProperty("分类ID")
    private Long categoryId;

    @ApiModelProperty("分类名称")
    private String categoryName;

    @ApiModelProperty("物品名称")
    private String name;

    @ApiModelProperty("规格型号")
    private String spec;

    @ApiModelProperty("单位")
    private String unit;

    @ApiModelProperty("参考单价")
    private BigDecimal price;

    @ApiModelProperty("最低库存报警值")
    private Integer minStock;

    @ApiModelProperty("当前库存量")
    private Integer stock;

    @ApiModelProperty("待领用（锁定）库存")
    private Integer lockStock;

    @ApiModelProperty("可领用库存")
    private Integer availableStock;

    @ApiModelProperty("状态 (1启用, 0禁用)")
    private Integer status;

    @ApiModelProperty("创建时间")
    private LocalDateTime createTime;
}
