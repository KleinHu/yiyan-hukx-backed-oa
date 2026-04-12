package com.cac.oa.vo.duty;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel("值班排班查询参数")
public class DutyScheduleQueryVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("分组ID (支持逗号分隔多个)")
    private String groupId;

    @ApiModelProperty("人员工号或姓名模糊搜索")
    private String userKey;

    @ApiModelProperty("查询开始日期 (YYYY-MM-DD)")
    private String startDate;

    @ApiModelProperty("查询结束日期 (YYYY-MM-DD)")
    private String endDate;
}
