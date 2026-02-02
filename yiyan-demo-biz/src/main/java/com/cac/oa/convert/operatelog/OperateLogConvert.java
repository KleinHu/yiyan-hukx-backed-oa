package com.cac.oa.convert.operatelog;

import java.util.*;

import com.cac.yiyan.common.page.PageData;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import com.cac.oa.vo.operatelog.OperateLogVO;
import com.cac.oa.entity.operatelog.OperateLogEntity;
import com.cac.oa.vo.operatelog.OperateLogExcel;

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
