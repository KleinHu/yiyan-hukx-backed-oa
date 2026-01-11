package com.cac.demo.convert.operatelog;

import java.util.*;

import com.cac.yiyan.common.page.PageData;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import com.cac.demo.vo.operatelog.OperateLogVO;
import com.cac.demo.entity.operatelog.OperateLogEntity;
import com.cac.demo.vo.operatelog.OperateLogExcel;

/**
 *  Convert
 *
 * @author 
 */
@Mapper
public interface OperateLogConvert {

    OperateLogConvert INSTANCE = Mappers.getMapper(OperateLogConvert.class);

    OperateLogEntity convert(OperateLogVO bean);

    OperateLogVO convert(OperateLogEntity bean);

    List<OperateLogVO> convertList(List<OperateLogEntity> list);

    PageData<OperateLogVO> convertPage(PageData<OperateLogEntity> page);

    List<OperateLogExcel> convertExcelList(List<OperateLogEntity> list);

}
