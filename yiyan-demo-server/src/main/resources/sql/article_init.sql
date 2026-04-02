DROP TABLE IF EXISTS `article`;
DROP TABLE IF EXISTS `article_column`;

-- 专栏表
CREATE TABLE `article_column` (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `name` varchar(50) NOT NULL COMMENT '专栏名称',
  `code` varchar(50) DEFAULT NULL COMMENT '专栏编码',
  `description` varchar(255) DEFAULT NULL COMMENT '专栏简介',
  `cover_url` varchar(500) DEFAULT NULL COMMENT '专栏封面图',
  `sort_order` int(11) DEFAULT '0' COMMENT '排序号',
  `status` tinyint(2) DEFAULT '1' COMMENT '状态 (1-启用 0-停用)',
  `creator` varchar(64) DEFAULT NULL COMMENT '创建者',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updater` varchar(64) DEFAULT NULL COMMENT '更新者',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `update_ip` varchar(255) DEFAULT NULL COMMENT '更新IP',
  `is_deleted` tinyint(2) DEFAULT '0' COMMENT '是否删除 (0-未删除，1-已删除)',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='文章专栏表';

-- 文章内容表
CREATE TABLE `article` (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `column_id` bigint(20) NOT NULL COMMENT '所属专栏ID',
  `title` varchar(200) NOT NULL COMMENT '文章标题',
  `summary` varchar(500) DEFAULT NULL COMMENT '文章摘要',
  `cover_url` varchar(500) DEFAULT NULL COMMENT '封面图',
  `content` longtext COMMENT '文章正文',
  `author_name` varchar(50) DEFAULT NULL COMMENT '作者名称',
  `author_code` varchar(50) DEFAULT NULL COMMENT '作者工号',
  `publish_time` datetime DEFAULT NULL COMMENT '发布时间',
  `status` tinyint(2) DEFAULT '0' COMMENT '状态 (0-草稿，1-已发布，2-已下线)',
  `view_count` int(11) DEFAULT '0' COMMENT '浏览量',
  `is_top` tinyint(2) DEFAULT '0' COMMENT '是否置顶 (1-置顶，0-普通)',
  `creator` varchar(64) DEFAULT NULL COMMENT '创建者',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updater` varchar(64) DEFAULT NULL COMMENT '更新者',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `update_ip` varchar(255) DEFAULT NULL COMMENT '更新IP',
  `is_deleted` tinyint(2) DEFAULT '0' COMMENT '是否删除 (0-未删除，1-已删除)',
  PRIMARY KEY (`id`),
  KEY `idx_column_id` (`column_id`),
  KEY `idx_status` (`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='文章内容表';
