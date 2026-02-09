package com.cac.oa.dao.supplies;

import com.cac.oa.entity.supplies.SuppliesItemEntity;
import com.cac.oa.vo.supplies.SuppliesQuery;
import com.cac.yiyan.common.page.PageData;
import com.cac.yiyan.mybatis.mapper.BaseMapperX;
import com.cac.yiyan.mybatis.query.LambdaQueryWrapperX;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SuppliesItemMapper extends BaseMapperX<SuppliesItemEntity> {

    default PageData<SuppliesItemEntity> selectPage(SuppliesQuery query) {
        return selectPage(query, new LambdaQueryWrapperX<SuppliesItemEntity>()
                .likeIfPresent(SuppliesItemEntity::getName, query.getName())
                .inIfPresent(SuppliesItemEntity::getCategoryId, query.getCategoryIds())
                .eqIfPresent(SuppliesItemEntity::getStatus, query.getStatus())
                .apply(Boolean.TRUE.equals(query.getLowStock()),
                        "(EXISTS (SELECT 1 FROM supplies_inventory inv WHERE inv.item_id = id AND (inv.stock - IFNULL(inv.lock_stock, 0)) < min_stock) " +
                                "OR (NOT EXISTS (SELECT 1 FROM supplies_inventory inv WHERE inv.item_id = id) AND min_stock > 0))")
                .orderByDesc(SuppliesItemEntity::getCreateTime));
    }
}
