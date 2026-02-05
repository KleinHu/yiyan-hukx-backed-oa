package com.cac.oa.vo.sixs;

import com.cac.yiyan.common.page.PageParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 6S 标准化分类查询对象
 *
 * @author
 * @since
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "SixSCategoryQuery", description = "6S标准化分类查询")
public class SixSCategoryQuery extends PageParam implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("分类名称")
    private String name;

    @ApiModelProperty("状态：0禁用 1启用")
    private Integer status;
}
