package com.cac.oa.vo.sixs;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.io.Serializable;

/**
 * 6S 标准化分类 VO
 *
 * @author
 * @since
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "SixSCategoryVO", description = "6S标准化分类")
public class SixSCategoryVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键ID")
    private Long id;

    @ApiModelProperty("分类名称")
    private String name;

    @ApiModelProperty("分类编码")
    private String code;

    @ApiModelProperty("默认扣分值")
    private Integer defaultScore;

    @ApiModelProperty("操作类型：1加分 2减分")
    private Integer operationType;

    @ApiModelProperty("分类描述")
    private String description;

    @ApiModelProperty("排序")
    private Integer sort;

    @ApiModelProperty("状态：0禁用 1启用")
    private Integer status;
}
