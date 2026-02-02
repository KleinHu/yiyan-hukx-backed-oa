package com.cac.oa.dao.operatelog;

import com.cac.oa.vo.operatelog.OperateLogQuery;
import com.cac.oa.entity.operatelog.OperateLogEntity;
import com.cac.yiyan.common.page.PageData;
import com.cac.yiyan.mybatis.mapper.BaseMapperX;
import com.cac.yiyan.mybatis.query.LambdaQueryWrapperX;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 *  Mapper
 * </p>
 *
 * @author
 * @since
 */
@Mapper
public interface OperateLogMapper extends BaseMapperX<OperateLogEntity> {

    /**																																																																																																																																							/**
    * 查询列表
    * @param query 查询对象
    * @return List<OperateLog>
    */
    default List<OperateLogEntity> selectOperateLogList(OperateLogQuery query){
        return selectList(getQueryWrapper(query));
    }

    /**																																																																																																																																							/**
    * 查询列表
    * @param query 查询对象
    * @return PageData<OperateLog>
    */
    default PageData<OperateLogEntity> selectOperateLogPage(OperateLogQuery query){
        return selectPage(query, getQueryWrapper(query));
    }


    default LambdaQueryWrapperX<OperateLogEntity> getQueryWrapper(OperateLogQuery query){
        return new LambdaQueryWrapperX<OperateLogEntity>()
            .eqIfPresent(OperateLogEntity::getTraceId, query.getTraceId())
            .eqIfPresent(OperateLogEntity::getUserId, query.getUserId())
            .eqIfPresent(OperateLogEntity::getUserType, query.getUserType())
            .eqIfPresent(OperateLogEntity::getSecretLevel, query.getSecretLevel())
            .eqIfPresent(OperateLogEntity::getModule, query.getModule())
            .likeIfPresent(OperateLogEntity::getName, query.getName())
            .eqIfPresent(OperateLogEntity::getType, query.getType())
            .eqIfPresent(OperateLogEntity::getContent, query.getContent())
            .eqIfPresent(OperateLogEntity::getExts, query.getExts())
            .eqIfPresent(OperateLogEntity::getRequestMethod, query.getRequestMethod())
            .eqIfPresent(OperateLogEntity::getRequestUrl, query.getRequestUrl())
            .eqIfPresent(OperateLogEntity::getUserIp, query.getUserIp())
            .eqIfPresent(OperateLogEntity::getUserAgent, query.getUserAgent())
            .eqIfPresent(OperateLogEntity::getJavaMethod, query.getJavaMethod())
            .eqIfPresent(OperateLogEntity::getJavaMethodArgs, query.getJavaMethodArgs())
            .betweenIfPresent(OperateLogEntity::getStartTime, query.getStartTime())
            .eqIfPresent(OperateLogEntity::getDuration, query.getDuration())
            .eqIfPresent(OperateLogEntity::getResultType, query.getResultType())
            .eqIfPresent(OperateLogEntity::getResultCode, query.getResultCode())
            .eqIfPresent(OperateLogEntity::getResultMsg, query.getResultMsg())
            .eqIfPresent(OperateLogEntity::getResultData, query.getResultData())
            .betweenIfPresent(OperateLogEntity::getCreateTime, query.getCreateTime())
            .eqIfPresent(OperateLogEntity::getTenantId, query.getTenantId())
            .orderByDesc(OperateLogEntity::getId);
    }


}
