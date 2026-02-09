package com.cac.oa.entity.supplies;

import com.baomidou.mybatisplus.annotation.*;
import com.cac.yiyan.mybatis.domain.BaseEntity;
import lombok.Data;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 办公用品库存实体
 */
@Data
@TableName("supplies_inventory")
public class SuppliesInventoryEntity extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 物品ID
     */
    @TableId(type = IdType.INPUT)
    private Long itemId;

    /**
     * 当前库存量
     */
    private Integer stock;

    /**
     * 待领用（锁定）库存
     */
    private Integer lockStock;
}
