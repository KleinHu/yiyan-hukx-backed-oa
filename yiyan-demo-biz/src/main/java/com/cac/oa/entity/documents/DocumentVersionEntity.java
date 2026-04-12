package com.cac.oa.entity.documents;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cac.yiyan.mybatis.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 文档历史版本实体类
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("document_version")
public class DocumentVersionEntity extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 对应的主文档ID
     */
    private Long documentId;

    /**
     * 版次 (如 V1.0, V2.0)
     */
    private String versionNum;

    /**
     * 该版本文档地址
     */
    private String url;

    /**
     * 更新说明/留痕记录
     */
    private String updateLog;

    /**
     * 该版本上传人
     */
    private String uploader;

}
