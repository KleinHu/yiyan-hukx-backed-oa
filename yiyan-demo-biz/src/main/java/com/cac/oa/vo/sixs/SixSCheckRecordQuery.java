package com.cac.oa.vo.sixs;

import com.cac.yiyan.common.page.PageParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * 6S 检查记录查询对象
 *
 * @author
 * @since
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "SixSCheckRecordQuery", description = "6S检查记录查询")
public class SixSCheckRecordQuery extends PageParam implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("台账ID")
    private Long accountId;

    @ApiModelProperty("分类ID")
    private Long categoryId;

    @ApiModelProperty("检查日期范围 [起, 止]")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate[] checkDate;
}
