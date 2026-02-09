package com.cac.oa.dao.supplies;

import com.cac.oa.entity.supplies.SuppliesRequestEntity;
import com.cac.oa.vo.supplies.SuppliesRequestQuery;
import com.cac.yiyan.common.page.PageData;
import com.cac.yiyan.mybatis.mapper.BaseMapperX;
import com.cac.yiyan.mybatis.query.LambdaQueryWrapperX;
import org.apache.ibatis.annotations.Mapper;

/**
 * 领用申请单 Mapper
 */
@Mapper
public interface SuppliesRequestMapper extends BaseMapperX<SuppliesRequestEntity> {

    default PageData<SuppliesRequestEntity> selectPage(SuppliesRequestQuery query) {
        return selectPage(query, new LambdaQueryWrapperX<SuppliesRequestEntity>()
                .eqIfPresent(SuppliesRequestEntity::getUserCode, query.getUserCode())
                .eqIfPresent(SuppliesRequestEntity::getAuditStatus, query.getAuditStatus())
                .apply(query.getYear() != null, "YEAR(apply_time) = {0}", query.getYear())
                .orderByDesc(SuppliesRequestEntity::getApplyTime));
    }

    /**
     * 获取部门物品领用汇总
     */
    java.util.List<com.cac.oa.vo.supplies.DeptItemConsumptionVO> selectDeptItemConsumption(@org.apache.ibatis.annotations.Param("query") com.cac.oa.vo.supplies.ConsumptionQuery query);

    /**
     * 获取人员领用明细
     */
    java.util.List<com.cac.oa.vo.supplies.UserConsumptionDetailVO> selectUserConsumptionDetails(@org.apache.ibatis.annotations.Param("query") com.cac.oa.vo.supplies.ConsumptionQuery query,
                                                                                              @org.apache.ibatis.annotations.Param("deptName") String deptName,
                                                                                              @org.apache.ibatis.annotations.Param("itemId") Long itemId);
}
