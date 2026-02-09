package com.cac.oa.dao.supplies;

import com.cac.oa.entity.supplies.SuppliesRecordEntity;
import com.cac.oa.vo.supplies.SuppliesRecordQuery;
import com.cac.yiyan.common.page.PageData;
import com.cac.yiyan.mybatis.mapper.BaseMapperX;
import com.cac.yiyan.mybatis.query.LambdaQueryWrapperX;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SuppliesRecordMapper extends BaseMapperX<SuppliesRecordEntity> {

    default PageData<SuppliesRecordEntity> selectPage(SuppliesRecordQuery query) {
        return selectPage(query, new LambdaQueryWrapperX<SuppliesRecordEntity>()
                .eqIfPresent(SuppliesRecordEntity::getItemId, query.getItemId())
                .eqIfPresent(SuppliesRecordEntity::getType, query.getType())
                .eqIfPresent(SuppliesRecordEntity::getScenario, query.getScenario())
                .geIfPresent(SuppliesRecordEntity::getCreateTime, query.getStartTime())
                .leIfPresent(SuppliesRecordEntity::getCreateTime, query.getEndTime())
                .orderByDesc(SuppliesRecordEntity::getCreateTime)
                .orderByDesc(SuppliesRecordEntity::getId));
    }
}
