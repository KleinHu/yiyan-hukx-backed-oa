package com.cac.oa.vo.supplies;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 库存变更请求
 */
@Data
@ApiModel("库存变更请求")
public class InventoryChangeRequest implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("物品ID")
    private Long itemId;

    @ApiModelProperty("变动数量 (正数为增，负数为减)")
    private Integer quantity;

    @ApiModelProperty("场景: 1-采购, 2-领用, 3-盘点, 4-退库, 5-报损")
    private Integer scenario;

    @ApiModelProperty("关联单据号")
    private String relNo;

    @ApiModelProperty("操作备注")
    private String remark;

    @ApiModelProperty("操作人姓名")
    private String operatorName;

    @ApiModelProperty("操作人工号")
    private String operatorCode;
}
