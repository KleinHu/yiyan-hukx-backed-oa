package com.cac.oa.entity.documents;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cac.yiyan.mybatis.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 文档信息主表实体类
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("document")
public class DocumentEntity extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 所属分类ID
     */
    private Long categoryId;

    /**
     * 文件名称
     */
    private String name;

    /**
     * 文件类型(扩展名)
     */
    private String type;

    /**
     * 文件大小(字节)
     */
    private Long size;

    /**
     * 当前文件OSS地址
     */
    private String url;

    /**
     * 当前版本号
     */
    private String currentVersion;

    /**
     * 状态 (0-草稿, 1-待审签, 2-已发布)
     */
    private Integer status;

    /**
     * 上传人
     */
    private String uploader;

}
