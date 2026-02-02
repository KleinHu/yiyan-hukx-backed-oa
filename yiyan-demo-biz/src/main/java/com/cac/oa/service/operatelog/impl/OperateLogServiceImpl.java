package com.cac.oa.service.operatelog.impl;

import com.cac.oa.vo.operatelog.OperateLogQuery;
import com.cac.oa.entity.operatelog.OperateLogEntity;
import com.cac.oa.service.operatelog.OperateLogService;
import com.cac.oa.dao.operatelog.OperateLogMapper;
import com.cac.oa.convert.operatelog.OperateLogConvert;
import com.cac.yiyan.common.page.PageData;
import com.cac.yiyan.common.exception.service.ServiceException;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

/**
 * <p>
 *  Service 实现类
 * </p>
 *
 * @author
 * @since
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class OperateLogServiceImpl extends ServiceImpl<OperateLogMapper, OperateLogEntity> implements OperateLogService {

    private final OperateLogMapper operateLogMapper;

    private final OperateLogConvert converter = OperateLogConvert.INSTANCE;

    @Override
    public List<OperateLogEntity> getOperateLogList(OperateLogQuery query){
        try{
            return operateLogMapper.selectOperateLogList(query);
        } catch (Exception e){
            log.error("执行[selectOperateLogListByEntity]方法发生异常:{}", e.getMessage());
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public PageData<OperateLogEntity> getOperateLogPage(OperateLogQuery query){
        try{
            return operateLogMapper.selectOperateLogPage(query);
        } catch (Exception e){
            log.error("执行[selectOperateLogListByEntity]方法发生异常:{}", e.getMessage());
            throw new ServiceException(e.getMessage(), e);
        }
    }


    @Override
    public OperateLogEntity getOperateLogById(Long id){
        try{
            return operateLogMapper.selectById(id);
        } catch (Exception e){
            log.error("执行[getOperateLogById]方法发生异常:{}", e.getMessage());
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public List<OperateLogEntity> getOperateLogListByIds(Collection<Long> ids){
        try{
            return operateLogMapper.selectBatchIds(ids);
        } catch (Exception e){
            log.error("执行[getOperateLogListByIds]方法发生异常:{}", e.getMessage());
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long saveOperateLog(OperateLogEntity entity){
        try {
            operateLogMapper.insert(entity);
        } catch (Exception e){
            log.error("执行[saveOperateLog]方法发生异常:{}", e.getMessage());
            throw new ServiceException(e.getMessage(), e);
        }
        return entity.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean updateOperateLog(OperateLogEntity entity){
        this.validateOperateLogExists(entity.getId());
        try {
            return updateById(entity);
        } catch (Exception e){
            log.error("执行[updateOperateLog]方法发生异常:{}", e.getMessage());
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean deleteOperateLogById(Long id){
        this.validateOperateLogExists(id);
        try {
            return removeById(id);
        } catch (Exception  e){
            log.error("执行[deleteOperateLogById]方法发生异常:{}", e.getMessage());
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean deleteOperateLogByIds(Collection<Long> ids){
        try {
            return removeByIds(ids);
        } catch (Exception  e){
            log.error("执行[deleteOperateLogByIds]方法发生异常:{}", e.getMessage());
            throw new ServiceException(e.getMessage(), e);
        }
    }

    private OperateLogEntity validateOperateLogExists(Long id) {
        OperateLogEntity exists = operateLogMapper.selectById(id);
        if (exists == null) {
            throw new ServiceException("数据失效,请刷新");
        }
        return exists;
    }

}
