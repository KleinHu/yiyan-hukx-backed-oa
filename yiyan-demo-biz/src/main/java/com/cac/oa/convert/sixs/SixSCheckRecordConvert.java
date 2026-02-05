package com.cac.oa.convert.sixs;

import java.util.List;

import com.cac.yiyan.common.page.PageData;
import com.cac.oa.entity.sixs.SixSCheckRecordEntity;
import com.cac.oa.vo.sixs.SixSCheckRecordVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 6S 检查记录 Convert
 *
 * @author
 */
@Mapper
public interface SixSCheckRecordConvert {

    SixSCheckRecordConvert INSTANCE = Mappers.getMapper(SixSCheckRecordConvert.class);

    SixSCheckRecordEntity convert(SixSCheckRecordVO bean);

    SixSCheckRecordVO convert(SixSCheckRecordEntity bean);

    List<SixSCheckRecordVO> convertList(List<SixSCheckRecordEntity> list);

    PageData<SixSCheckRecordVO> convertPage(PageData<SixSCheckRecordEntity> page);
}
