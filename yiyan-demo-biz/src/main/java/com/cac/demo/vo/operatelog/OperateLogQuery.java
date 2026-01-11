package com.cac.demo.vo.operatelog;

import com.cac.yiyan.common.page.PageParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;
import static com.cac.yiyan.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author 
 * @since 
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "OperateLog查询对象", description = "")
public class OperateLogQuery extends PageParam implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("链路追踪编号")
    private String traceId;

    @ApiModelProperty("用户编号")
    private String userId;

    @ApiModelProperty("用户类型")
    private Integer userType;

    @ApiModelProperty("数据密级(10:公开,20:普通商密,30受控,40核心商密,50内部,60秘密,70机密)")
    private Integer secretLevel;

    @ApiModelProperty("模块标题")
    private String module;

    @ApiModelProperty("操作名")
    private String name;

    @ApiModelProperty("操作分类")
    private Integer type;

    @ApiModelProperty("操作内容")
    private String content;

    @ApiModelProperty("拓展字段")
    private String exts;

    @ApiModelProperty("请求方法名")
    private String requestMethod;

    @ApiModelProperty("请求地址")
    private String requestUrl;

    @ApiModelProperty("用户 IP")
    private String userIp;

    @ApiModelProperty("浏览器 UA")
    private String userAgent;

    @ApiModelProperty("Java 方法名")
    private String javaMethod;

    @ApiModelProperty("Java 方法的参数")
    private String javaMethodArgs;

    @ApiModelProperty("操作时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private Date[] startTime;

    @ApiModelProperty("执行时长")
    private Integer duration;

    @ApiModelProperty("结果类型,1成功，2失败")
    private Integer resultType;

    @ApiModelProperty("结果码")
    private Integer resultCode;

    @ApiModelProperty("结果提示")
    private String resultMsg;

    @ApiModelProperty("结果数据")
    private String resultData;

    @ApiModelProperty("创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private Date[] createTime;

    @ApiModelProperty("租户编号")
    private Long tenantId;

}
