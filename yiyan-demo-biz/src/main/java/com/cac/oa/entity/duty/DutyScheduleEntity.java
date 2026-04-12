package com.cac.oa.entity.duty;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cac.yiyan.mybatis.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

/**
 * 值班排班表
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("oa_duty_schedule")
public class DutyScheduleEntity extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 所属分组ID
     */
    private Long groupId;

    /**
     * 排班人员工号
     */
    private String userCode;

    /**
     * 排班人员姓名
     */
    private String userName;

    /**
     * 值班日期
     */
    private LocalDate dutyDate;

    /**
     * 显示顺序
     */
    private Integer sortOrder;

    /**
     * 备注
     */
    private String remark;

    /**
     * 更新IP
     */
    private String updateIp;
}
