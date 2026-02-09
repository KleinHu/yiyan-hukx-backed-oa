package com.cac.oa.vo.supplies;

import com.cac.yiyan.common.page.PageParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 领用申请查询参数
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel("领用申请查询参数")
public class SuppliesRequestQuery extends PageParam {

    @ApiModelProperty("领用人工号")
    private String userCode;

    @ApiModelProperty("审核状态")
    private Integer auditStatus;

    @ApiModelProperty("所属年份")
    private String year;
}
