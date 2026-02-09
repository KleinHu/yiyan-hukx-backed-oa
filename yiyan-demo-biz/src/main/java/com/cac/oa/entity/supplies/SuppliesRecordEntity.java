package com.cac.oa.entity.supplies;

import com.baomidou.mybatisplus.annotation.*;
import com.cac.yiyan.mybatis.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * 办公用品库存流水实体
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("supplies_record")
public class SuppliesRecordEntity extends BaseEntity {
    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 物品ID
     */
    private Long itemId;

    /**
     * 1-入库, 2-出库
     */
    private Integer type;

    /**
     * 场景: 1-采购, 2-领用, 3-盘点, 4-退库, 5-报损
     */
    private Integer scenario;

    /**
     * 变动数量
     */
    private Integer quantity;

    /**
     * 关联单据号
     */
    private String relNo;

    /**
     * 创建者姓名
     */
    @TableField("creator_name")
    private String creatorName;
}
