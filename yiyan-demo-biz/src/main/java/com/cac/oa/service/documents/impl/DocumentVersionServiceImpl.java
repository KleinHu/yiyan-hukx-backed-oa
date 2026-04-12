package com.cac.oa.service.documents.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cac.oa.dao.documents.DocumentVersionMapper;
import com.cac.oa.entity.documents.DocumentVersionEntity;
import com.cac.oa.service.documents.DocumentVersionService;
import org.springframework.stereotype.Service;

/**
 * 文档历史版本记录表 Service 实现类
 */
@Service
public class DocumentVersionServiceImpl extends ServiceImpl<DocumentVersionMapper, DocumentVersionEntity> implements DocumentVersionService {
}
