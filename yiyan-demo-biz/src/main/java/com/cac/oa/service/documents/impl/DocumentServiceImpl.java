package com.cac.oa.service.documents.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cac.oa.dao.documents.DocumentMapper;
import com.cac.oa.entity.documents.DocumentEntity;
import com.cac.oa.entity.documents.DocumentVersionEntity;
import com.cac.oa.service.documents.DocumentService;
import com.cac.oa.service.documents.DocumentVersionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 文档信息主表 Service 实现类
 */
@Service
public class DocumentServiceImpl extends ServiceImpl<DocumentMapper, DocumentEntity> implements DocumentService {

    @Autowired
    private DocumentVersionService documentVersionService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void changeDocumentVersion(Long documentId, String newUrl, String newFileName, Long newFileSize, String updateLog, String uploader) {
        // 1. 获取主文档
        DocumentEntity document = this.getById(documentId);
        if (document == null) {
            throw new RuntimeException("文档不存在");
        }

        // 2. 现将旧版本归档到历史版本表
        DocumentVersionEntity historyVersion = new DocumentVersionEntity();
        historyVersion.setDocumentId(documentId);
        historyVersion.setVersionNum(document.getCurrentVersion());
        historyVersion.setUrl(document.getUrl());
        historyVersion.setUpdateLog("自动归档（换版前版本）");
        // 获取之前的上传人信息，如果有对应的字段，这里默认写入前上传人，或者填入归档人信息
        historyVersion.setUploader(document.getUploader());
        documentVersionService.save(historyVersion);

        // 3. 生成新版本号 (V1.0 -> V2.0 简单递增逻辑)
        String newVersion = incrementVersion(document.getCurrentVersion());

        // 4. 更新当前主文档记录
        document.setUrl(newUrl);
        if (newFileName != null && !newFileName.isEmpty()) {
            document.setName(newFileName);
        }
        if (newFileSize != null) {
            document.setSize(newFileSize);
        }
        document.setCurrentVersion(newVersion);
        document.setUploader(uploader);
        
        // 此处可以根据分类判断是否进入待审签状态，目前默认不直接操作状态
        this.updateById(document);

        // 5. 将新版本也记录一份作为追溯（如果需要的话，也可以不记录，主表有最新的就行，此处记录一次新版说明）
        DocumentVersionEntity newVersionRecord = new DocumentVersionEntity();
        newVersionRecord.setDocumentId(documentId);
        newVersionRecord.setVersionNum(newVersion);
        newVersionRecord.setUrl(newUrl);
        newVersionRecord.setUpdateLog(updateLog != null ? updateLog : "换版更新");
        newVersionRecord.setUploader(uploader);
        documentVersionService.save(newVersionRecord);
    }

    /**
     * 简单的版本号递增逻辑: V1.0 -> V2.0
     * @param currentVersion 当前版本
     * @return 递增后的版本
     */
    private String incrementVersion(String currentVersion) {
        if (currentVersion == null || currentVersion.isEmpty()) {
            return "V1.0";
        }
        if (currentVersion.startsWith("V")) {
            try {
                String numberPart = currentVersion.substring(1);
                double versionNum = Double.parseDouble(numberPart);
                versionNum += 1.0;
                return "V" + String.format("%.1f", versionNum);
            } catch (Exception e) {
                return currentVersion + ".1";
            }
        }
        return currentVersion + ".new";
    }
}
