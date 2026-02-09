package com.cac.oa.convert.supplies;

import com.cac.oa.entity.supplies.SuppliesItemEntity;
import com.cac.oa.vo.supplies.SuppliesItemVO;
import com.cac.yiyan.common.page.PageData;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 办公用品档案 Convert
 */
@Mapper
public interface SuppliesItemConvert {

    SuppliesItemConvert INSTANCE = Mappers.getMapper(SuppliesItemConvert.class);

    SuppliesItemEntity convert(SuppliesItemVO bean);

    @Mapping(target = "categoryName", ignore = true)
    @Mapping(target = "stock", ignore = true)
    SuppliesItemVO convert(SuppliesItemEntity bean);

    List<SuppliesItemVO> convertList(List<SuppliesItemEntity> list);

    PageData<SuppliesItemVO> convertPage(PageData<SuppliesItemEntity> page);
}
