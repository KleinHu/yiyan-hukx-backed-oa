package com.cac.oa.convert.article;

import com.cac.oa.entity.article.ArticleColumnEntity;
import com.cac.oa.vo.article.ArticleColumnVO;
import com.cac.yiyan.common.page.PageData;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 文章专栏 Convert
 */
@Mapper
public interface ArticleColumnConvert {

    ArticleColumnConvert INSTANCE = Mappers.getMapper(ArticleColumnConvert.class);

    ArticleColumnEntity convert(ArticleColumnVO bean);

    ArticleColumnVO convert(ArticleColumnEntity bean);

    List<ArticleColumnVO> convertList(List<ArticleColumnEntity> list);

    PageData<ArticleColumnVO> convertPage(PageData<ArticleColumnEntity> page);
}
