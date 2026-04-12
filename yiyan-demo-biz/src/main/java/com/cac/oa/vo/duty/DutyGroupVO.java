package com.cac.oa.vo.duty;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@ApiModel("值班分组响应数据")
public class DutyGroupVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("ID")
    private Long id;

    @ApiModelProperty("分组名称")
    private String name;

    @ApiModelProperty("父级ID")
    private Long parentId;

    @ApiModelProperty("分组层级")
    private Integer level;

    @ApiModelProperty("绑定的HR系统科室ID")
    private String departmentId;

    @ApiModelProperty("绑定的HR系统科室名称")
    private String departmentName;

    @ApiModelProperty("状态 (1-启用 0-停用)")
    private Integer status;

    @ApiModelProperty("子节点列表")
    private List<DutyGroupVO> children;
}
