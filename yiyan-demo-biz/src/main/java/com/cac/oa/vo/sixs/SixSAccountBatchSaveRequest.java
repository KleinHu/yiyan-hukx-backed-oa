package com.cac.oa.vo.sixs;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * 6S 积分台账批量新增请求
 *
 * @author
 * @since
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "SixSAccountBatchSaveRequest", description = "6S积分台账批量新增请求")
public class SixSAccountBatchSaveRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull(message = "台账年份不能为空")
    @ApiModelProperty(value = "台账年份", required = true, example = "2026")
    private Integer year;

    @Valid
    @NotNull(message = "台账列表不能为空")
    @ApiModelProperty(value = "台账列表", required = true)
    private List<SixSAccountVO> list;
}
