package com.cac.oa.convert.supplies;

import com.cac.oa.entity.supplies.SuppliesRequestEntity;
import com.cac.oa.entity.supplies.SuppliesRequestItemEntity;
import com.cac.oa.vo.supplies.SuppliesRequestItemVO;
import com.cac.oa.vo.supplies.SuppliesRequestVO;
import com.cac.yiyan.common.page.PageData;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 办公用品领用 Convert
 */
@Mapper
public interface SuppliesRequestConvert {

    SuppliesRequestConvert INSTANCE = Mappers.getMapper(SuppliesRequestConvert.class);

    SuppliesRequestEntity convert(SuppliesRequestVO bean);

    SuppliesRequestVO convert(SuppliesRequestEntity bean);

    SuppliesRequestItemEntity convertItem(SuppliesRequestItemVO bean);

    SuppliesRequestItemVO convertItem(SuppliesRequestItemEntity bean);

    List<SuppliesRequestVO> convertList(List<SuppliesRequestEntity> list);

    List<SuppliesRequestItemVO> convertItemList(List<SuppliesRequestItemEntity> list);

    PageData<SuppliesRequestVO> convertPage(PageData<SuppliesRequestEntity> page);
}
