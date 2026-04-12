package com.cac.oa.entity.duty;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cac.yiyan.mybatis.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 值班分组表
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("oa_duty_group")
public class DutyGroupEntity extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 分组名称
     */
    private String name;

    /**
     * 父级ID
     */
    private Long parentId;

    /**
     * 分组层级 (1-科室级, 2-班组级)
     */
    private Integer level;

    /**
     * 绑定的HR系统科室ID
     */
    private String departmentId;

    /**
     * 绑定的HR系统科室名称
     */
    private String departmentName;

    /**
     * 状态 (1-启用 0-停用)
     */
    private Integer status;

    /**
     * 更新IP
     */
    private String updateIp;
}
