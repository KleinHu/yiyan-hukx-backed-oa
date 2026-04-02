package com.cac.oa.dao.article;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cac.oa.entity.article.ArticleViewRecordEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 文章阅读记录 Mapper
 */
@Mapper
public interface ArticleViewRecordMapper extends BaseMapper<ArticleViewRecordEntity> {
}
