package com.cac.oa.service.documents;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cac.oa.entity.documents.DocumentEntity;

/**
 * 文档信息主表 Service 接口
 */
public interface DocumentService extends IService<DocumentEntity> {
    
    /**
     * 更换文档版本
     * @param documentId 原文档ID
     * @param newUrl 新版本文件URL
     * @param newFileName 新文件名称
     * @param newFileSize 新文件大小
     * @param updateLog 换版说明
     * @param uploader 上传人
     */
    void changeDocumentVersion(Long documentId, String newUrl, String newFileName, Long newFileSize, String updateLog, String uploader);

}
