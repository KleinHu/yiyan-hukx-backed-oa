package com.cac.oa.dao.documents;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cac.oa.entity.documents.DocumentVersionEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 文档历史版本记录表 Mapper 接口
 */
@Mapper
public interface DocumentVersionMapper extends BaseMapper<DocumentVersionEntity> {
}
