package com.cac.oa.entity.supplies;

import com.baomidou.mybatisplus.annotation.*;
import com.cac.yiyan.mybatis.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * 领用申请单
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("supplies_request")
public class SuppliesRequestEntity extends BaseEntity {
    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    private Long id;

    /** 申请单号 */
    private String orderNo;

    /** 领用人工号 */
    private String userCode;

    /** 领用人姓名 */
    private String userName;

    /** 领用部门名称 */
    private String deptName;

    /** 申请时间 */
    private LocalDateTime applyTime;

    /** 申请事由 */
    private String reason;

    /** 状态: 0待审核, 1通过, 2驳回, 3已发放 */
    private Integer auditStatus;

    /** 审核人姓名 */
    private String auditorName;

    /** 审核时间 */
    private LocalDateTime auditTime;

    /** 审核备注 */
    private String remark;
}
