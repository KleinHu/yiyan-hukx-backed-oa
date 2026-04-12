package com.cac.oa.service.duty;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.cac.oa.convert.duty.DutyConvert;
import com.cac.oa.dao.duty.DutyScheduleMapper;
import com.cac.oa.entity.duty.DutyScheduleEntity;
import com.cac.oa.vo.duty.DutyScheduleQueryVO;
import com.cac.oa.vo.duty.DutyScheduleSaveReqVO;
import com.cac.oa.vo.duty.DutyScheduleVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@Service
public class DutyScheduleService {

    @Resource
    private DutyScheduleMapper dutyScheduleMapper;

    /**
     * 查询排班列表
     */
    public List<DutyScheduleVO> getScheduleList(DutyScheduleQueryVO query) {
        LambdaQueryWrapper<DutyScheduleEntity> wrapper = new LambdaQueryWrapper<DutyScheduleEntity>()
                .eq(query.getGroupId() != null, DutyScheduleEntity::getGroupId, query.getGroupId())
                .ge(query.getStartDate() != null, DutyScheduleEntity::getDutyDate, query.getStartDate())
                .le(query.getEndDate() != null, DutyScheduleEntity::getDutyDate, query.getEndDate());

        if (StringUtils.hasText(query.getUserKey())) {
            wrapper.and(w -> w.like(DutyScheduleEntity::getUserName, query.getUserKey())
                    .or()
                    .like(DutyScheduleEntity::getUserCode, query.getUserKey()));
        }

        wrapper.orderByAsc(DutyScheduleEntity::getDutyDate)
               .orderByAsc(DutyScheduleEntity::getSortOrder);
        return DutyConvert.INSTANCE.convertScheduleList(dutyScheduleMapper.selectList(wrapper));
    }

    /**
     * 保存单条排班
     */
    @Transactional(rollbackFor = Exception.class)
    public Long saveSchedule(DutyScheduleSaveReqVO vo) {
        DutyScheduleEntity entity = DutyConvert.INSTANCE.convert(vo);
        if (entity.getId() != null) {
            dutyScheduleMapper.updateById(entity);
        } else {
            dutyScheduleMapper.insert(entity);
        }
        return entity.getId();
    }

    /**
     * 批量排班逻辑 (预留)
     */
    @Transactional(rollbackFor = Exception.class)
    public void batchSaveSchedule(List<DutyScheduleSaveReqVO> vos) {
        for (DutyScheduleSaveReqVO vo : vos) {
            saveSchedule(vo);
        }
    }

    /**
     * 批量删除排班
     */
    @Transactional(rollbackFor = Exception.class)
    public void deleteScheduleBatch(List<Long> ids) {
        dutyScheduleMapper.deleteBatchIds(ids);
    }

    /**
     * 删除排班
     */
    public void deleteSchedule(Long id) {
        dutyScheduleMapper.deleteById(id);
    }
}
