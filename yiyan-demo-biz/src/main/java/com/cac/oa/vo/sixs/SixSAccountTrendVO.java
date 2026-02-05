package com.cac.oa.vo.sixs;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * 6S 分数趋势 VO
 *
 * @author 
 * @since 
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "SixSAccountTrendVO", description = "6S分数趋势")
public class SixSAccountTrendVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("日期")
    private LocalDate checkDate;

    @ApiModelProperty("当前分数")
    private Integer score;
}
