package com.cac.oa.vo.supplies;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 办公用品分类 VO
 */
@Data
@ApiModel("办公用品分类")
public class SuppliesCategoryVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("ID")
    private Long id;

    @ApiModelProperty("分类名称")
    private String name;

    @ApiModelProperty("父级ID")
    private Long parentId;

    @ApiModelProperty("排序")
    private Integer sort;

    @ApiModelProperty("创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty("子分类列表")
    private List<SuppliesCategoryVO> children;
}
