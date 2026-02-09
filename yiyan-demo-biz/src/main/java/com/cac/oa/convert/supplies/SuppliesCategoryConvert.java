package com.cac.oa.convert.supplies;

import com.cac.oa.entity.supplies.SuppliesCategoryEntity;
import com.cac.oa.vo.supplies.SuppliesCategoryVO;
import com.cac.yiyan.common.page.PageData;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 办公用品分类 Convert
 */
@Mapper
public interface SuppliesCategoryConvert {

    SuppliesCategoryConvert INSTANCE = Mappers.getMapper(SuppliesCategoryConvert.class);

    SuppliesCategoryEntity convert(SuppliesCategoryVO bean);

    SuppliesCategoryVO convert(SuppliesCategoryEntity bean);

    List<SuppliesCategoryVO> convertList(List<SuppliesCategoryEntity> list);

    PageData<SuppliesCategoryVO> convertPage(PageData<SuppliesCategoryEntity> page);
}
