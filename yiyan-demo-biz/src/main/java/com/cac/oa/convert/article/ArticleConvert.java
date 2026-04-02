package com.cac.oa.convert.article;

import com.cac.oa.entity.article.ArticleEntity;
import com.cac.oa.vo.article.ArticleVO;
import com.cac.yiyan.common.page.PageData;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 文章 Convert
 */
@Mapper
public interface ArticleConvert {

    ArticleConvert INSTANCE = Mappers.getMapper(ArticleConvert.class);

    ArticleEntity convert(ArticleVO bean);

    ArticleVO convert(ArticleEntity bean);

    List<ArticleVO> convertList(List<ArticleEntity> list);

    PageData<ArticleVO> convertPage(PageData<ArticleEntity> page);
}
