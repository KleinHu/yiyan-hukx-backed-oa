package com.cac.oa.convert.documents;

import com.cac.oa.entity.documents.DocumentEntity;
import com.cac.oa.vo.documents.DocumentVO;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 文档实体转换器
 */
@Mapper(componentModel = "spring")
public interface DocumentConvert {

    DocumentConvert INSTANCE = Mappers.getMapper(DocumentConvert.class);

    DocumentVO convert(DocumentEntity entity);

    List<DocumentVO> convertList(List<DocumentEntity> list);

    @AfterMapping
    default void afterMapping(DocumentEntity entity, @MappingTarget DocumentVO vo) {
        if (entity.getStatus() != null) {
            switch (entity.getStatus()) {
                case 0:
                    vo.setStatusName("草稿");
                    break;
                case 1:
                    vo.setStatusName("审签中");
                    break;
                case 2:
                    vo.setStatusName("已发布");
                    break;
                default:
                    vo.setStatusName("未知状态");
            }
        }
    }
}
