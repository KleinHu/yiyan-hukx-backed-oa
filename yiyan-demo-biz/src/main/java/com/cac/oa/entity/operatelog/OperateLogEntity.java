package com.cac.demo.entity.operatelog;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.baomidou.mybatisplus.annotation.*;
import com.cac.yiyan.mybatis.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
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
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@TableName("system_operate_log")
public class OperateLogEntity extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
    * 日志主键
    */
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    /**
    * 链路追踪编号
    */
    private String traceId;

    /**
    * 用户编号
    */
    private String userId;

    /**
    * 用户类型
    */
    private Integer userType;

    /**
    * 数据密级(10:公开,20:普通商密,30受控,40核心商密,50内部,60秘密,70机密)
    */
    private Integer secretLevel;

    /**
    * 模块标题
    */
    private String module;

    /**
    * 操作名
    */
    private String name;

    /**
    * 操作分类
    */
    private Integer type;

    /**
    * 操作内容
    */
    private String content;

    /**
    * 拓展字段
    */
    private String exts;

    /**
    * 请求方法名
    */
    private String requestMethod;

    /**
    * 请求地址
    */
    private String requestUrl;

    /**
    * 用户 IP
    */
    private String userIp;

    /**
    * 浏览器 UA
    */
    private String userAgent;

    /**
    * Java 方法名
    */
    private String javaMethod;

    /**
    * Java 方法的参数
    */
    private String javaMethodArgs;

    /**
    * 操作时间
    */
    private Date startTime;

    /**
    * 执行时长
    */
    private Integer duration;

    /**
    * 结果类型,1成功，2失败
    */
    private Integer resultType;

    /**
    * 结果码
    */
    private Integer resultCode;

    /**
    * 结果提示
    */
    private String resultMsg;

    /**
    * 结果数据
    */
    private String resultData;

    /**
    * 租户编号
    */
    @TableField(fill = FieldFill.INSERT)
    private Long tenantId;

}
