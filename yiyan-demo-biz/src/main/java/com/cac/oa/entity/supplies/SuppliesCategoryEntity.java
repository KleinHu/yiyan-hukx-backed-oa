package com.cac.oa.entity.supplies;

import com.baomidou.mybatisplus.annotation.*;
import com.cac.yiyan.mybatis.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 办公用品分类实体
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("supplies_category")
public class SuppliesCategoryEntity extends BaseEntity {
    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 分类名称
     */
    private String name;

    /**
     * 父级ID (0为根节点)
     */
    private Long parentId;

    /**
     * 排序
     */
    private Integer sort;
}
