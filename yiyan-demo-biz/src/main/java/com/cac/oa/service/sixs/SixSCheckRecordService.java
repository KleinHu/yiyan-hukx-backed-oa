package com.cac.oa.service.sixs;

import com.cac.oa.entity.sixs.SixSCheckRecordEntity;
import com.cac.oa.vo.sixs.SixSCheckRecordQuery;
import com.cac.oa.vo.sixs.SixSCheckRecordVO;
import com.cac.yiyan.common.page.PageData;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Collection;
import java.util.List;

/**
 * 6S 检查记录 Service 接口
 *
 * @author
 * @since
 */
public interface SixSCheckRecordService extends IService<SixSCheckRecordEntity> {

    /**
     * 查询列表
     *
     * @param query 查询条件
     * @return List
     */
    List<SixSCheckRecordEntity> getSixSCheckRecordList(SixSCheckRecordQuery query);

    /**
     * 分页查询
     *
     * @param query 查询条件
     * @return PageData
     */
    PageData<SixSCheckRecordEntity> getSixSCheckRecordPage(SixSCheckRecordQuery query);

    /**
     * 根据ID获取明细
     *
     * @param id 主键
     * @return 实体
     */
    SixSCheckRecordEntity getSixSCheckRecordById(Long id);

    /**
     * 根据ID数组获取明细
     *
     * @param ids 主键集合
     * @return 实体列表
     */
    List<SixSCheckRecordEntity> getSixSCheckRecordListByIds(Collection<Long> ids);

    /**
     * 保存检查记录（自动扣分并更新台账总积分）
     *
     * @param entity 实体
     * @return 主键
     */
    Long saveSixSCheckRecord(SixSCheckRecordEntity entity);

    /**
     * 根据id修改实体（不更新积分，仅更新检查信息）
     *
     * @param entity 实体
     * @return 是否成功
     */
    Boolean updateSixSCheckRecord(SixSCheckRecordEntity entity);

    /**
     * 根据ID删除
     *
     * @param id 主键
     * @return 是否成功
     */
    Boolean deleteSixSCheckRecordById(Long id);

    /**
     * 根据ID批量删除
     *
     * @param ids 主键集合
     * @return 是否成功
     */
    Boolean deleteSixSCheckRecordByIds(Collection<Long> ids);

    /**
     * 为检查记录 VO 列表填充分类名称
     *
     * @param list VO 列表
     */
    void fillCategoryNames(List<SixSCheckRecordVO> list);
}
