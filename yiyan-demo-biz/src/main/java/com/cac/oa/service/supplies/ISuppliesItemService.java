package com.cac.oa.service.supplies;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cac.oa.entity.supplies.SuppliesItemEntity;

import com.cac.oa.vo.supplies.InventoryChangeRequest;
import com.cac.oa.vo.supplies.SuppliesItemVO;
import com.cac.oa.vo.supplies.SuppliesQuery;
import com.cac.oa.vo.supplies.SuppliesRecordQuery;
import com.cac.oa.vo.supplies.SuppliesRecordVO;
import com.cac.yiyan.common.page.PageData;

public interface ISuppliesItemService extends IService<SuppliesItemEntity> {
    /**
     * 分页查询物品列表（包含库存和分类名）
     */
    PageData<SuppliesItemVO> getPage(SuppliesQuery query);

    /**
     * 分页查询库存流水
     */
    PageData<SuppliesRecordVO> getRecordPage(SuppliesRecordQuery query);

    /**
     * 变更库存 (事务控制)
     */
    void changeInventory(InventoryChangeRequest request);
}
