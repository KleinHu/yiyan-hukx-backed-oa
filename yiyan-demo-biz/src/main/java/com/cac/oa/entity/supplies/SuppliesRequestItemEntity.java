package com.cac.oa.entity.supplies;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.io.Serializable;

/**
 * 领用申请明细
 */
@Data
@TableName("supplies_request_item")
public class SuppliesRequestItemEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    private Long id;

    /** 申请主表ID */
    private Long requestId;

    /** 物品ID */
    private Long itemId;

    /** 申请数量 */
    private Integer quantity;

    /** 实际发放数量 */
    private Integer issuedQuantity;
}
