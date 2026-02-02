package com.cac.oa.vo.operatelog;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;
import java.util.Date;

/**
* <p>
*
* </p>
*
* @author
* @since
*/
@Data
public class OperateLogExcel {

    @ExcelProperty(value = "日志主键", index = 0)
    private String id;

    @ExcelProperty(value = "链路追踪编号", index = 1)
    private String traceId;

    @ExcelProperty(value = "用户编号", index = 2)
    private String userId;

    @ExcelProperty(value = "用户类型", index = 3)
    private Integer userType;

    @ExcelProperty(value = "数据密级(10:公开,20:普通商密,30受控,40核心商密,50内部,60秘密,70机密)", index = 4)
    private Integer secretLevel;

    @ExcelProperty(value = "模块标题", index = 5)
    private String module;

    @ExcelProperty(value = "操作名", index = 6)
    private String name;

    @ExcelProperty(value = "操作分类", index = 7)
    private Integer type;

    @ExcelProperty(value = "操作内容", index = 8)
    private String content;

    @ExcelProperty(value = "拓展字段", index = 9)
    private String exts;

    @ExcelProperty(value = "请求方法名", index = 10)
    private String requestMethod;

    @ExcelProperty(value = "请求地址", index = 11)
    private String requestUrl;

    @ExcelProperty(value = "用户 IP", index = 12)
    private String userIp;

    @ExcelProperty(value = "浏览器 UA", index = 13)
    private String userAgent;

    @ExcelProperty(value = "Java 方法名", index = 14)
    private String javaMethod;

    @ExcelProperty(value = "Java 方法的参数", index = 15)
    private String javaMethodArgs;

    @ExcelProperty(value = "操作时间", index = 16)
    private Date startTime;

    @ExcelProperty(value = "执行时长", index = 17)
    private Integer duration;

    @ExcelProperty(value = "结果类型,1成功，2失败", index = 18)
    private Integer resultType;

    @ExcelProperty(value = "结果码", index = 19)
    private Integer resultCode;

    @ExcelProperty(value = "结果提示", index = 20)
    private String resultMsg;

    @ExcelProperty(value = "结果数据", index = 21)
    private String resultData;

    @ExcelProperty(value = "租户编号", index = 22)
    private String tenantId;

}
