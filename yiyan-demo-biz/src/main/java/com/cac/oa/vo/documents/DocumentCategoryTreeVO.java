package com.cac.oa.vo.documents;

import com.cac.oa.entity.documents.DocumentCategoryEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * 分类树形视图对象
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class DocumentCategoryTreeVO extends DocumentCategoryEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 子节点列表
     */
    private List<DocumentCategoryTreeVO> children;

}
