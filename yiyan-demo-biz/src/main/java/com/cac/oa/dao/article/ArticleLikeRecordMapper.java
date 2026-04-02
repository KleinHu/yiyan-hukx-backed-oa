package com.cac.oa.dao.article;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cac.oa.entity.article.ArticleLikeRecordEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 文章点赞记录 Mapper
 */
@Mapper
public interface ArticleLikeRecordMapper extends BaseMapper<ArticleLikeRecordEntity> {
}
