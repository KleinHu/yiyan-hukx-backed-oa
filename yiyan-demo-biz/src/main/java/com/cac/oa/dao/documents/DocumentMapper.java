package com.cac.oa.dao.documents;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cac.oa.entity.documents.DocumentEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 文档信息主表 Mapper 接口
 */
@Mapper
public interface DocumentMapper extends BaseMapper<DocumentEntity> {
}
