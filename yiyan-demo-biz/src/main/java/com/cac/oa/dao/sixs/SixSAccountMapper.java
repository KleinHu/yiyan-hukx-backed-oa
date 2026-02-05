package com.cac.oa.dao.sixs;

import com.cac.oa.entity.sixs.SixSAccountEntity;
import com.cac.oa.vo.sixs.SixSAccountQuery;
import com.cac.yiyan.common.page.PageData;
import com.cac.yiyan.mybatis.mapper.BaseMapperX;
import com.cac.yiyan.mybatis.query.LambdaQueryWrapperX;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 6S 积分台账 Mapper
 *
 * @author
 * @since
 */
@Mapper
public interface SixSAccountMapper extends BaseMapperX<SixSAccountEntity> {

    /**
     * 查询列表
     *
     * @param query 查询对象
     * @return List
     */
    default List<SixSAccountEntity> selectSixSAccountList(SixSAccountQuery query) {
        return selectList(getQueryWrapper(query));
    }

    /**
     * 分页查询
     *
     * @param query 查询对象
     * @return PageData
     */
    default PageData<SixSAccountEntity> selectSixSAccountPage(SixSAccountQuery query) {
        return selectPage(query, getQueryWrapper(query));
    }

    default LambdaQueryWrapperX<SixSAccountEntity> getQueryWrapper(SixSAccountQuery query) {
        LambdaQueryWrapperX<SixSAccountEntity> wrapper = new LambdaQueryWrapperX<SixSAccountEntity>()
            .eqIfPresent(SixSAccountEntity::getUserCode, query.getUserCode())
            .likeIfPresent(SixSAccountEntity::getUserName, query.getUserName())
            .likeIfPresent(SixSAccountEntity::getDepartmentName, query.getDepartmentName())
            .inIfPresent(SixSAccountEntity::getDepartmentId, query.getDepartmentIds())
            .eqIfPresent(SixSAccountEntity::getYear, query.getYear())
            .eqIfPresent(SixSAccountEntity::getStatus, query.getStatus());

        // 处理分数等级过滤
        if ("excellent".equals(query.getScoreLevel())) {
            wrapper.ge(SixSAccountEntity::getTotalScore, 100);
        } else if ("warning".equals(query.getScoreLevel())) {
            wrapper.ge(SixSAccountEntity::getTotalScore, 85).lt(SixSAccountEntity::getTotalScore, 100);
        } else if ("serious".equals(query.getScoreLevel())) {
            wrapper.lt(SixSAccountEntity::getTotalScore, 85);
        }

        return wrapper.orderByAsc(SixSAccountEntity::getTotalScore)
            .orderByDesc(SixSAccountEntity::getId);
    }
}
