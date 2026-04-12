package com.cac.oa.vo.duty;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;

@Data
@ApiModel("值班排班保存/更新请求")
public class DutyScheduleSaveReqVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("ID (更新时必填)")
    private Long id;

    @ApiModelProperty("所属分组ID")
    @NotNull(message = "所属分组不能为空")
    private Long groupId;

    @ApiModelProperty("排班人员工号")
    @NotBlank(message = "人员工号不能为空")
    private String userCode;

    @ApiModelProperty("排班人员姓名")
    @NotBlank(message = "人员姓名不能为空")
    private String userName;

    @ApiModelProperty("值班日期")
    @NotNull(message = "值班日期不能为空")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dutyDate;

    @ApiModelProperty("显示顺序")
    private Integer sortOrder;

    @ApiModelProperty("备注")
    private String remark;
}

