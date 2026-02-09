package com.cac.oa.vo.supplies;

import com.cac.yiyan.common.page.PageParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.time.LocalDateTime;

/**
 * 库存流水查询参数
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel("库存流水查询参数")
public class SuppliesRecordQuery extends PageParam {

    @ApiModelProperty("物品ID")
    private Long itemId;

    @ApiModelProperty("物品名称 (模糊查询)")
    private String itemName;

    @ApiModelProperty("变动类型 (1-入库, 2-出库)")
    private Integer type;

    @ApiModelProperty("变动场景")
    private Integer scenario;

    @ApiModelProperty("开始时间")
    private LocalDateTime startTime;

    @ApiModelProperty("结束时间")
    private LocalDateTime endTime;
}
