package com.cac.oa.convert.duty;

import com.cac.oa.entity.duty.DutyGroupEntity;
import com.cac.oa.entity.duty.DutyScheduleEntity;
import com.cac.oa.vo.duty.DutyGroupSaveReqVO;
import com.cac.oa.vo.duty.DutyGroupVO;
import com.cac.oa.vo.duty.DutyScheduleSaveReqVO;
import com.cac.oa.vo.duty.DutyScheduleVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface DutyConvert {

    DutyConvert INSTANCE = Mappers.getMapper(DutyConvert.class);

    // Group
    DutyGroupVO convert(DutyGroupEntity bean);
    DutyGroupEntity convert(DutyGroupSaveReqVO bean);
    List<DutyGroupVO> convertList(List<DutyGroupEntity> list);

    // Schedule
    DutyScheduleVO convert(DutyScheduleEntity bean);
    DutyScheduleEntity convert(DutyScheduleSaveReqVO bean);
    List<DutyScheduleVO> convertScheduleList(List<DutyScheduleEntity> list);
}
