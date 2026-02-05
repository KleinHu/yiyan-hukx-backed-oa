package com.cac.oa.convert.sixs;

import java.util.List;

import com.cac.yiyan.common.page.PageData;
import com.cac.oa.entity.sixs.SixSAccountEntity;
import com.cac.oa.vo.sixs.SixSAccountVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 6S 积分台账 Convert
 *
 * @author
 */
@Mapper
public interface SixSAccountConvert {

    SixSAccountConvert INSTANCE = Mappers.getMapper(SixSAccountConvert.class);

    SixSAccountEntity convert(SixSAccountVO bean);

    SixSAccountVO convert(SixSAccountEntity bean);

    List<SixSAccountVO> convertList(List<SixSAccountEntity> list);

    PageData<SixSAccountVO> convertPage(PageData<SixSAccountEntity> page);
}
