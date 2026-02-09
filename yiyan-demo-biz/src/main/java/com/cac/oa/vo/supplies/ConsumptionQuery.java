package com.cac.oa.vo.supplies;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 领用分析查询参数
 */
@Data
@ApiModel("领用分析查询参数")
public class ConsumptionQuery {

    @ApiModelProperty("年份")
    private String year;

    @ApiModelProperty("开始月份 (YYYY-MM)")
    private String startMonth;

    @ApiModelProperty("结束月份 (YYYY-MM)")
    private String endMonth;
}
