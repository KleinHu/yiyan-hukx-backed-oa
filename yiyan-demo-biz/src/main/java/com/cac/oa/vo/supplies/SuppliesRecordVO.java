package com.cac.oa.vo.supplies;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 库存流水记录 VO
 */
@Data
@ApiModel("库存流水记录")
public class SuppliesRecordVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("ID")
    private Long id;

    @ApiModelProperty("物品ID")
    private Long itemId;

    @ApiModelProperty("物品名称")
    private String itemName;

    @ApiModelProperty("规格型号")
    private String spec;

    @ApiModelProperty("变动类型 (1-入库, 2-出库)")
    private Integer type;

    @ApiModelProperty("变动场景: 1-采购, 2-领用, 3-盘点, 4-退库, 5-报损")
    private Integer scenario;

    @ApiModelProperty("变动数量")
    private Integer quantity;

    @ApiModelProperty("关联单号")
    private String relNo;

    @ApiModelProperty("操作人工号")
    private String creator;

    @ApiModelProperty("操作人姓名")
    private String creatorName;

    @ApiModelProperty("发生时间")
    private LocalDateTime createTime;
}
