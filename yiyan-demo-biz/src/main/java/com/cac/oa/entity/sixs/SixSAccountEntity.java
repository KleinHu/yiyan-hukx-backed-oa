package com.cac.oa.entity.sixs;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cac.yiyan.mybatis.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

/**
 * 6S 积分台账实体
 *
 * @author
 * @since
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@TableName("six_s_account")
public class SixSAccountEntity extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 工号
     */
    private String userCode;

    /**
     * 用户姓名
     */
    private String userName;

    /**
     * 部门ID（来自HR系统）
     */
    private String departmentId;

    /**
     * 部门名称
     */
    private String departmentName;

    /**
     * 年度（每年度重置积分为100分，按年份查询历史台账）
     */
    private Integer year;

    /**
     * 总积分
     */
    private Integer totalScore;

    /**
     * 状态：0禁用 1启用
     */
    private Integer status;

    /**
     * 备注
     */
    private String remark;
}
