package com.cac.oa.convert.supplies;

import com.cac.oa.entity.supplies.SuppliesRecordEntity;
import com.cac.oa.vo.supplies.SuppliesRecordVO;
import com.cac.yiyan.common.page.PageData;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import java.util.List;

/**
 * 库存流水转换
 */
@Mapper
public interface SuppliesRecordConvert {
    SuppliesRecordConvert INSTANCE = Mappers.getMapper(SuppliesRecordConvert.class);

    SuppliesRecordVO convert(SuppliesRecordEntity entity);

    List<SuppliesRecordVO> convertList(List<SuppliesRecordEntity> list);

    PageData<SuppliesRecordVO> convertPage(PageData<SuppliesRecordEntity> page);
}
