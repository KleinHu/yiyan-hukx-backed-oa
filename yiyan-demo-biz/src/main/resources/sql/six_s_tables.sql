-- 6S 积分台账功能 - 数据库表结构
-- 在 240_oa 实例中执行：mysql -u user -p 240_oa < six_s_tables.sql
-- 或先创建库再执行：CREATE DATABASE IF NOT EXISTS 240_oa; USE 240_oa; 然后执行下表结构

-- 1. 积分台账表（six_s_account）- 按年度区分，每年度重置积分为100分
CREATE TABLE IF NOT EXISTS `six_s_account` (
  `id` bigint NOT NULL COMMENT '主键ID',
  `user_code` varchar(32) NOT NULL COMMENT '工号',
  `user_name` varchar(64) NOT NULL COMMENT '用户姓名',
  `department_id` varchar(36) DEFAULT NULL COMMENT '部门ID（来自HR系统）',
  `department_name` varchar(128) DEFAULT NULL COMMENT '部门名称',
  `year` int NOT NULL COMMENT '年度（每年度重置积分为100分）',
  `total_score` int NOT NULL DEFAULT 100 COMMENT '总积分',
  `status` tinyint NOT NULL DEFAULT 1 COMMENT '状态：0禁用 1启用',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  `creator` varchar(64) DEFAULT NULL COMMENT '创建者',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updater` varchar(64) DEFAULT NULL COMMENT '更新者',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `is_deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_user_code_year` (`user_code`, `year`, `is_deleted`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='6S积分台账表';

-- 2. 标准化分类表（six_s_category）
CREATE TABLE IF NOT EXISTS `six_s_category` (
  `id` bigint NOT NULL COMMENT '主键ID',
  `name` varchar(64) NOT NULL COMMENT '分类名称',
  `code` varchar(32) NOT NULL COMMENT '分类编码',
  `default_score` int NOT NULL DEFAULT 5 COMMENT '默认扣分值',
  `operation_type` tinyint NOT NULL DEFAULT 1 COMMENT '操作类型：1加分 2减分',
  `description` varchar(500) DEFAULT NULL COMMENT '分类描述',
  `sort` int NOT NULL DEFAULT 0 COMMENT '排序',
  `status` tinyint NOT NULL DEFAULT 1 COMMENT '状态：0禁用 1启用',
  `creator` varchar(64) DEFAULT NULL COMMENT '创建者',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updater` varchar(64) DEFAULT NULL COMMENT '更新者',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `is_deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_code` (`code`, `is_deleted`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='6S标准化分类表';

-- 3. 检查记录表（含积分）（six_s_check_record）
CREATE TABLE IF NOT EXISTS `six_s_check_record` (
  `id` bigint NOT NULL COMMENT '主键ID',
  `account_id` bigint NOT NULL COMMENT '台账ID',
  `category_id` bigint NOT NULL COMMENT '分类ID',
  `check_date` date NOT NULL COMMENT '检查日期',
  `problem_description` varchar(1000) NOT NULL COMMENT '问题描述',
  `checker_code` varchar(32) DEFAULT NULL COMMENT '检查人工号',
  `checker_name` varchar(64) DEFAULT NULL COMMENT '检查人姓名',
  `images` varchar(2000) DEFAULT NULL COMMENT '图片URL，多个逗号分隔',
  `score_deducted` int NOT NULL DEFAULT 0 COMMENT '扣分值',
  `before_score` int NOT NULL COMMENT '扣分前台账积分',
  `after_score` int NOT NULL COMMENT '扣分后台账积分',
  `creator` varchar(64) DEFAULT NULL COMMENT '创建者',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updater` varchar(64) DEFAULT NULL COMMENT '更新者',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `is_deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
  PRIMARY KEY (`id`),
  KEY `idx_account_id` (`account_id`),
  KEY `idx_category_id` (`category_id`),
  KEY `idx_check_date` (`check_date`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='6S检查记录表（含积分）';
