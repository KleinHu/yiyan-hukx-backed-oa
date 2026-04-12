package com.cac.oa.vo.duty;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
@ApiModel("值班分组保存/更新请求")
public class DutyGroupSaveReqVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("ID (更新时必填)")
    private Long id;

    @ApiModelProperty("分组名称")
    @NotBlank(message = "分组名称不能为空")
    private String name;

    @ApiModelProperty("父级ID")
    private Long parentId;

    @ApiModelProperty("分组层级 (1-科室级, 2-班组级)")
    private Integer level;

    @ApiModelProperty("绑定的HR系统科室ID")
    private String departmentId;

    @ApiModelProperty("绑定的HR系统科室名称")
    private String departmentName;

    @ApiModelProperty("状态 (1-启用 0-停用)")
    private Integer status;
}
