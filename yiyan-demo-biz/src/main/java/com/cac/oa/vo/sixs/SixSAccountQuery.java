package com.cac.oa.vo.sixs;

import com.cac.yiyan.common.page.PageParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.List;

/**
 * 6S 积分台账查询对象
 *
 * @author
 * @since
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "SixSAccountQuery", description = "6S积分台账查询")
public class SixSAccountQuery extends PageParam implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("工号")
    private String userCode;

    @ApiModelProperty("用户姓名")
    private String userName;

    @ApiModelProperty("部门名称")
    private String departmentName;

    @ApiModelProperty("年度（按年份查询历史台账）")
    private Integer year;

    @ApiModelProperty("状态：0禁用 1启用")
    private Integer status;

    @ApiModelProperty("分数等级：excellent(优秀), warning(警告), serious(严重)")
    private String scoreLevel;

    @ApiModelProperty("部门ID列表")
    private List<String> departmentIds;
}
