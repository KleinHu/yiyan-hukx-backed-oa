package com.cac.oa.dao.article;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cac.oa.entity.article.ArticleFavoriteEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 文章收藏记录 Mapper
 */
@Mapper
public interface ArticleFavoriteMapper extends BaseMapper<ArticleFavoriteEntity> {
}
