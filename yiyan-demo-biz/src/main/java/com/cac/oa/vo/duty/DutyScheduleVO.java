package com.cac.oa.vo.duty;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;

@Data
@ApiModel("值班排班 VO")
public class DutyScheduleVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("ID")
    private Long id;

    @ApiModelProperty("所属分组ID")
    private Long groupId;

    @ApiModelProperty("排班人员工号")
    private String userCode;

    @ApiModelProperty("排班人员姓名")
    private String userName;

    @ApiModelProperty("值班日期")
    private LocalDate dutyDate;

    @ApiModelProperty("显示顺序")
    private Integer sortOrder;

    @ApiModelProperty("备注")
    private String remark;

    @ApiModelProperty("创建时间")
    private Date createTime;
}
