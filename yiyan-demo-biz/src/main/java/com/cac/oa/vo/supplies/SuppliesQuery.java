package com.cac.oa.vo.supplies;

import com.cac.yiyan.common.page.PageParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 办公用品查询参数
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel("办公用品查询")
public class SuppliesQuery extends PageParam {
    @ApiModelProperty("物品名称")
    private String name;

    @ApiModelProperty("分类ID列表")
    private java.util.List<Long> categoryIds;

    @ApiModelProperty("状态")
    private Integer status;

    @ApiModelProperty("是否库存紧缺")
    private Boolean lowStock;
}
