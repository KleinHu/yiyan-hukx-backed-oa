package com.cac.oa.vo.documents;

import lombok.Data;

import java.util.List;

/**
 * 文档查询参数
 */
@Data
public class DocumentQuery {
    
    private Integer pageNo = 1;
    private Integer pageSize = 10;
    
    private String name;
    /** 单分类精确查询（可选） */
    private Long categoryId;
    /** 多分类范围查询，如点击父分类时传入所有子分类ID（可选，优先级高于 categoryId） */
    private List<Long> categoryIds;
    private Integer status;
    private String uploader;

}
