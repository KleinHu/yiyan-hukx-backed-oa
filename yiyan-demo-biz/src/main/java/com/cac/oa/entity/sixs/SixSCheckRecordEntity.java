package com.cac.oa.entity.sixs;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cac.yiyan.mybatis.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDate;

/**
 * 6S 检查记录实体（含积分）
 *
 * @author
 * @since
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@TableName("six_s_check_record")
public class SixSCheckRecordEntity extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 台账ID
     */
    private Long accountId;

    /**
     * 分类ID
     */
    private Long categoryId;

    /**
     * 检查日期
     */
    private LocalDate checkDate;

    /**
     * 问题描述
     */
    private String problemDescription;

    /**
     * 检查人工号
     */
    private String checkerCode;

    /**
     * 检查人姓名
     */
    private String checkerName;

    /**
     * 图片URL，多个逗号分隔
     */
    private String images;

    /**
     * 扣分值
     */
    private Integer scoreDeducted;

    /**
     * 扣分前台账积分
     */
    private Integer beforeScore;

    /**
     * 扣分后台账积分
     */
    private Integer afterScore;
}
