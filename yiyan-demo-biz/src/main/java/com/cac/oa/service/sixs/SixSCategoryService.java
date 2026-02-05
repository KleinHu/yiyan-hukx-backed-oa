package com.cac.oa.service.sixs;

import com.cac.oa.entity.sixs.SixSCategoryEntity;
import com.cac.oa.vo.sixs.SixSCategoryQuery;
import com.cac.yiyan.common.page.PageData;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Collection;
import java.util.List;

/**
 * 6S 标准化分类 Service 接口
 *
 * @author
 * @since
 */
public interface SixSCategoryService extends IService<SixSCategoryEntity> {

    /**
     * 查询列表
     *
     * @param query 查询条件
     * @return List
     */
    List<SixSCategoryEntity> getSixSCategoryList(SixSCategoryQuery query);

    /**
     * 分页查询
     *
     * @param query 查询条件
     * @return PageData
     */
    PageData<SixSCategoryEntity> getSixSCategoryPage(SixSCategoryQuery query);

    /**
     * 根据ID获取明细
     *
     * @param id 主键
     * @return 实体
     */
    SixSCategoryEntity getSixSCategoryById(Long id);

    /**
     * 根据ID数组获取明细
     *
     * @param ids 主键集合
     * @return 实体列表
     */
    List<SixSCategoryEntity> getSixSCategoryListByIds(Collection<Long> ids);

    /**
     * 保存实体
     *
     * @param entity 实体
     * @return 主键
     */
    Long saveSixSCategory(SixSCategoryEntity entity);

    /**
     * 根据id修改实体
     *
     * @param entity 实体
     * @return 是否成功
     */
    Boolean updateSixSCategory(SixSCategoryEntity entity);

    /**
     * 根据ID删除
     *
     * @param id 主键
     * @return 是否成功
     */
    Boolean deleteSixSCategoryById(Long id);

    /**
     * 根据ID批量删除
     *
     * @param ids 主键集合
     * @return 是否成功
     */
    Boolean deleteSixSCategoryByIds(Collection<Long> ids);
}
