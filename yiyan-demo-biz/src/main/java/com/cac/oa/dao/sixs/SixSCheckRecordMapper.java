package com.cac.oa.dao.sixs;

import com.cac.oa.entity.sixs.SixSCheckRecordEntity;
import com.cac.oa.vo.sixs.SixSCheckRecordQuery;
import com.cac.yiyan.common.page.PageData;
import com.cac.yiyan.mybatis.mapper.BaseMapperX;
import com.cac.yiyan.mybatis.query.LambdaQueryWrapperX;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 6S 检查记录 Mapper
 *
 * @author
 * @since
 */
@Mapper
public interface SixSCheckRecordMapper extends BaseMapperX<SixSCheckRecordEntity> {

    /**
     * 查询列表
     *
     * @param query 查询对象
     * @return List
     */
    default List<SixSCheckRecordEntity> selectSixSCheckRecordList(SixSCheckRecordQuery query) {
        return selectList(getQueryWrapper(query));
    }

    /**
     * 分页查询
     *
     * @param query 查询对象
     * @return PageData
     */
    default PageData<SixSCheckRecordEntity> selectSixSCheckRecordPage(SixSCheckRecordQuery query) {
        return selectPage(query, getQueryWrapper(query));
    }

    default LambdaQueryWrapperX<SixSCheckRecordEntity> getQueryWrapper(SixSCheckRecordQuery query) {
        return new LambdaQueryWrapperX<SixSCheckRecordEntity>()
            .eqIfPresent(SixSCheckRecordEntity::getAccountId, query.getAccountId())
            .eqIfPresent(SixSCheckRecordEntity::getCategoryId, query.getCategoryId())
            .betweenIfPresent(SixSCheckRecordEntity::getCheckDate, query.getCheckDate())
            .orderByDesc(SixSCheckRecordEntity::getCheckDate)
            .orderByDesc(SixSCheckRecordEntity::getId);
    }
}
