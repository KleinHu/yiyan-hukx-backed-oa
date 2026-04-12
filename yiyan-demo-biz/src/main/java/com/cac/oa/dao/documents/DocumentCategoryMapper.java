package com.cac.oa.dao.documents;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cac.oa.entity.documents.DocumentCategoryEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 文档分类 Mapper 接口
 */
@Mapper
public interface DocumentCategoryMapper extends BaseMapper<DocumentCategoryEntity> {
}
