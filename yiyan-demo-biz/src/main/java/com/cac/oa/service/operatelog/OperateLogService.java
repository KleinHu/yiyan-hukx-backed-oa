package com.cac.oa.service.operatelog;

import com.cac.oa.vo.operatelog.OperateLogQuery;
import com.cac.oa.entity.operatelog.OperateLogEntity;
import com.cac.yiyan.common.page.PageData;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Collection;
import java.util.List;

/**
 * <p>
 *  Service 接口
 * </p>
 *
 * @author
 * @since
 */

public interface OperateLogService extends IService<OperateLogEntity> {

    /**																																																																																																																																							/**
    * 查询列表
    * @param query 查询条件
    * @return List<OperateLog>
    */
    public List<OperateLogEntity> getOperateLogList(OperateLogQuery query);

    /**																																																																																																																																							/**
    * 分页查询
    * @param query 查询条件
    * @return 封装的分页数据:PageData<OperateLog>
    */
    public PageData<OperateLogEntity> getOperateLogPage(OperateLogQuery query);


    /**
    * 根据ID获取明细
    * @param id 主键
    * @return
    * @throws Exception
    */
    public OperateLogEntity getOperateLogById(Long id);

    /**
    * 根据ID数组获取明细
    * @param ids 主键集合
    * @return
    * @throws Exception
    */
    public List<OperateLogEntity> getOperateLogListByIds(Collection<Long> ids);


    /**
    * 保存实体
    * @param entity
    * @throws Exception
    */
    public Long saveOperateLog(OperateLogEntity entity);

    /**
    * 根据id修改实体
    * @param entity
    * @throws Exception
    */
    public Boolean updateOperateLog(OperateLogEntity entity);

    /**
    * 根据ID删除
    * @param id
    * @throws Exception
    */
    public Boolean deleteOperateLogById(Long id);

    /**
    * 根据ID批量删除
    * @param ids
    * @throws Exception
    */
    public Boolean deleteOperateLogByIds(Collection<Long> ids);
}
