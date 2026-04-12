-- 易研 OA - 文件管理功能建表 SQL

-- 1. 文件分类表
CREATE TABLE `document_category` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `parent_id` bigint(20) DEFAULT '0' COMMENT '父节点ID',
  `name` varchar(100) NOT NULL COMMENT '分类名称',
  `sort` int(11) DEFAULT '0' COMMENT '排序',
  `need_approval` tinyint(1) DEFAULT '0' COMMENT '是否需审签 (0-否, 1-是)',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `is_deleted` tinyint(1) DEFAULT '0' COMMENT '逻辑删除 (0-未删除, 1-已删除)',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='文档分类表';

-- 2. 文件信息主表
CREATE TABLE `document` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `category_id` bigint(20) NOT NULL COMMENT '所属分类ID',
  `name` varchar(255) NOT NULL COMMENT '文件名称',
  `type` varchar(50) DEFAULT NULL COMMENT '文件类型(扩展名)',
  `size` bigint(20) DEFAULT '0' COMMENT '文件大小(字节)',
  `url` varchar(1000) NOT NULL COMMENT '当前文件OSS地址',
  `current_version` varchar(50) DEFAULT 'V1.0' COMMENT '当前版本号',
  `status` int(11) DEFAULT '2' COMMENT '状态 (0-草稿, 1-审签中, 2-已发布)',
  `uploader` varchar(100) DEFAULT NULL COMMENT '上传人',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `is_deleted` tinyint(1) DEFAULT '0' COMMENT '逻辑删除',
  PRIMARY KEY (`id`),
  KEY `idx_category` (`category_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='文档信息主表';

-- 3. 文件历史版本表
CREATE TABLE `document_version` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `document_id` bigint(20) NOT NULL COMMENT '对应的主文档ID',
  `version_num` varchar(50) NOT NULL COMMENT '版次 (如 V1.0, V2.0)',
  `url` varchar(1000) NOT NULL COMMENT '该版本文档地址',
  `update_log` varchar(500) DEFAULT NULL COMMENT '更新说明/留痕记录',
  `creator` varchar(64) DEFAULT '' COMMENT '创建者',
  `uploader` varchar(100) DEFAULT NULL COMMENT '该版本上传人',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '上传/归档时间',
  `updater` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `update_ip` varchar(50) DEFAULT '' COMMENT '更新IP',
  PRIMARY KEY (`id`),
  KEY `idx_document` (`document_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='文档历史版本记录表';
