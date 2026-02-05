package com.cac.oa.vo.sixs;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 6S 积分台账统计 VO
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "SixSAccountStatisticsVO", description = "6S积分台账统计")
public class SixSAccountStatisticsVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("员工总数")
    private Long totalCount;

    @ApiModelProperty("优秀人数 (>95)")
    private Long excellentCount;

    @ApiModelProperty("警告人数 (90-95)")
    private Long warningCount;

    @ApiModelProperty("严重人数 (<90)")
    private Long seriousCount;
}
