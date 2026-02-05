package com.cac.oa.convert.sixs;

import java.util.List;

import com.cac.yiyan.common.page.PageData;
import com.cac.oa.entity.sixs.SixSCategoryEntity;
import com.cac.oa.vo.sixs.SixSCategoryVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 6S 标准化分类 Convert
 *
 * @author
 */
@Mapper
public interface SixSCategoryConvert {

    SixSCategoryConvert INSTANCE = Mappers.getMapper(SixSCategoryConvert.class);

    SixSCategoryEntity convert(SixSCategoryVO bean);

    SixSCategoryVO convert(SixSCategoryEntity bean);

    List<SixSCategoryVO> convertList(List<SixSCategoryEntity> list);

    PageData<SixSCategoryVO> convertPage(PageData<SixSCategoryEntity> page);
}
