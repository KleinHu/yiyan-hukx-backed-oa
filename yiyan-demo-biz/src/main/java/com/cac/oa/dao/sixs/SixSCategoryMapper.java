package com.cac.oa.dao.sixs;

import com.cac.oa.entity.sixs.SixSCategoryEntity;
import com.cac.oa.vo.sixs.SixSCategoryQuery;
import com.cac.yiyan.common.page.PageData;
import com.cac.yiyan.mybatis.mapper.BaseMapperX;
import com.cac.yiyan.mybatis.query.LambdaQueryWrapperX;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 6S 标准化分类 Mapper
 *
 * @author
 * @since
 */
@Mapper
public interface SixSCategoryMapper extends BaseMapperX<SixSCategoryEntity> {

    /**
     * 查询列表
     *
     * @param query 查询对象
     * @return List
     */
    default List<SixSCategoryEntity> selectSixSCategoryList(SixSCategoryQuery query) {
        return selectList(getQueryWrapper(query));
    }

    /**
     * 分页查询
     *
     * @param query 查询对象
     * @return PageData
     */
    default PageData<SixSCategoryEntity> selectSixSCategoryPage(SixSCategoryQuery query) {
        return selectPage(query, getQueryWrapper(query));
    }

    default LambdaQueryWrapperX<SixSCategoryEntity> getQueryWrapper(SixSCategoryQuery query) {
        return new LambdaQueryWrapperX<SixSCategoryEntity>()
            .likeIfPresent(SixSCategoryEntity::getName, query.getName())
            .eqIfPresent(SixSCategoryEntity::getStatus, query.getStatus())
            .orderByAsc(SixSCategoryEntity::getSort)
            .orderByDesc(SixSCategoryEntity::getId);
    }
}
