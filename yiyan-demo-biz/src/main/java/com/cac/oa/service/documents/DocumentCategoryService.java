package com.cac.oa.service.documents;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cac.oa.entity.documents.DocumentCategoryEntity;
import com.cac.oa.vo.documents.DocumentCategoryTreeVO;

import java.util.List;

/**
 * 文档分类 Service 接口
 */
public interface DocumentCategoryService extends IService<DocumentCategoryEntity> {

    /**
     * 获取分类树形结构
     * @return 分类树列表
     */
    List<DocumentCategoryTreeVO> getCategoryTree();

}
