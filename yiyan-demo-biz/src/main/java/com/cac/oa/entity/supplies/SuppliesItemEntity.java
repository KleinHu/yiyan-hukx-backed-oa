package com.cac.oa.entity.supplies;

import com.baomidou.mybatisplus.annotation.*;
import com.cac.yiyan.mybatis.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

/**
 * 办公用品档案实体
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("supplies_item")
public class SuppliesItemEntity extends BaseEntity {
    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 所属末级分类ID
     */
    private Long categoryId;

    /**
     * 物品名称
     */
    private String name;

    /**
     * 规格型号
     */
    private String spec;

    /**
     * 单位
     */
    private String unit;

    /**
     * 参考单价
     */
    private BigDecimal price;

    /**
     * 最低库存报警值
     */
    private Integer minStock;

    /**
     * 状态 (1启用, 0禁用)
     */
    private Integer status;
}
