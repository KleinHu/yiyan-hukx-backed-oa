package com.cac.oa.vo.documents;

import com.cac.oa.entity.documents.DocumentEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 文档信息 VO
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class DocumentVO extends DocumentEntity {
    
    /**
     * 状态名称
     */
    private String statusName;
}
