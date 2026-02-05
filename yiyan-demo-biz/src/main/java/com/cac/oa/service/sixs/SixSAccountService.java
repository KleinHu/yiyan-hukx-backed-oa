package com.cac.oa.service.sixs;

import com.cac.oa.entity.sixs.SixSAccountEntity;
import com.cac.oa.vo.sixs.SixSAccountQuery;
import com.cac.oa.vo.sixs.SixSAccountStatisticsVO;
import com.cac.oa.vo.sixs.SixSAccountTrendVO;
import com.cac.yiyan.common.page.PageData;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Collection;
import java.util.List;

/**
 * 6S 积分台账 Service 接口
 *
 * @author
 * @since
 */
public interface SixSAccountService extends IService<SixSAccountEntity> {

    /**
     * 获取统计数据
     *
     * @param year 年份
     * @return 统计结果
     */
    SixSAccountStatisticsVO getStatistics(Integer year);

    /**
     * 获取分数趋势
     *
     * @param accountId 台账ID
     * @return 趋势列表
     */
    List<SixSAccountTrendVO> getTrend(Long accountId);

    /**
     * 查询列表
     *
     * @param query 查询条件
     * @return List
     */
    List<SixSAccountEntity> getSixSAccountList(SixSAccountQuery query);

    /**
     * 分页查询
     *
     * @param query 查询条件
     * @return PageData
     */
    PageData<SixSAccountEntity> getSixSAccountPage(SixSAccountQuery query);

    /**
     * 根据ID获取明细
     *
     * @param id 主键
     * @return 实体
     */
    SixSAccountEntity getSixSAccountById(Long id);

    /**
     * 根据ID数组获取明细
     *
     * @param ids 主键集合
     * @return 实体列表
     */
    List<SixSAccountEntity> getSixSAccountListByIds(Collection<Long> ids);

    /**
     * 保存实体
     *
     * @param entity 实体
     * @return 主键
     */
    Long saveSixSAccount(SixSAccountEntity entity);

    /**
     * 批量保存实体 (存在跳过)
     *
     * @param entityList 实体列表
     * @return 是否成功
     */
    Boolean saveSixSAccountBatch(List<SixSAccountEntity> entityList);

    /**
     * 根据id修改实体
     *
     * @param entity 实体
     * @return 是否成功
     */
    Boolean updateSixSAccount(SixSAccountEntity entity);

    /**
     * 根据ID删除
     *
     * @param id 主键
     * @return 是否成功
     */
    Boolean deleteSixSAccountById(Long id);

    /**
     * 根据ID批量删除
     *
     * @param ids 主键集合
     * @return 是否成功
     */
    Boolean deleteSixSAccountByIds(Collection<Long> ids);
}
