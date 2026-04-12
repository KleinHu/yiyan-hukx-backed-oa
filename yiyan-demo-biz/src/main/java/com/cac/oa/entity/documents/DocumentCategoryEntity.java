package com.cac.oa.entity.documents;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cac.yiyan.mybatis.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 文档分类实体类
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("document_category")
public class DocumentCategoryEntity extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 父节点ID
     */
    private Long parentId;

    /**
     * 分类名称
     */
    private String name;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 是否需审签 (0-否, 1-是)
     */
    private Integer needApproval;

    /**
     * 备注
     */
    private String remark;

}
