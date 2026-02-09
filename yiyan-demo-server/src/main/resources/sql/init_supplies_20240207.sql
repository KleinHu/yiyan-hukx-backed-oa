-- 办公用品管理系统 初始化脚本
-- 数据库: 240_oa
-- 版本: MySQL 5.7

CREATE TABLE IF NOT EXISTS `supplies_category` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` VARCHAR(50) NOT NULL COMMENT '分类名称',
  `parent_id` BIGINT(20) DEFAULT 0 COMMENT '父级ID (0为根节点)',
  `sort` INT(11) DEFAULT 0 COMMENT '排序',
  `creator_name` VARCHAR(50) DEFAULT NULL COMMENT '创建人姓名',
  `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updater_name` VARCHAR(50) DEFAULT NULL COMMENT '修改人姓名',
  `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `is_deleted` TINYINT(4) DEFAULT 0 COMMENT '逻辑删除 (0-正常, 1-已删除)',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='办公用品分类表';

CREATE TABLE IF NOT EXISTS `supplies_item` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `category_id` BIGINT(20) NOT NULL COMMENT '所属末级分类ID',
  `name` VARCHAR(100) NOT NULL COMMENT '物品名称',
  `spec` VARCHAR(100) DEFAULT NULL COMMENT '规格型号',
  `unit` VARCHAR(20) DEFAULT NULL COMMENT '单位',
  `price` DECIMAL(10,2) DEFAULT '0.00' COMMENT '参考单价',
  `min_stock` INT(11) DEFAULT 0 COMMENT '最低库存报警值',
  `status` TINYINT(4) DEFAULT 1 COMMENT '状态 (1启用, 0禁用)',
  `creator_name` VARCHAR(50) DEFAULT NULL COMMENT '创建人姓名',
  `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updater_name` VARCHAR(50) DEFAULT NULL COMMENT '修改人姓名',
  `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `is_deleted` TINYINT(4) DEFAULT 0 COMMENT '逻辑删除 (0-正常, 1-已删除)',
  PRIMARY KEY (`id`),
  KEY `idx_category` (`category_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='办公用品档案表';

CREATE TABLE IF NOT EXISTS `supplies_inventory` (
  `item_id` BIGINT(20) NOT NULL COMMENT '物品ID',
  `stock` INT(11) NOT NULL DEFAULT 0 COMMENT '当前库存量',
  `updater_name` VARCHAR(50) DEFAULT NULL COMMENT '修改人姓名',
  `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`item_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='办公用品库存表';

CREATE TABLE IF NOT EXISTS `supplies_record` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `item_id` BIGINT(20) NOT NULL COMMENT '物品ID',
  `type` TINYINT(4) NOT NULL COMMENT '1-入库, 2-出库',
  `scenario` TINYINT(4) NOT NULL COMMENT '场景: 1-采购, 2-领用, 3-盘点, 4-退库, 5-报损',
  `quantity` INT(11) NOT NULL COMMENT '变动数量',
  `rel_no` VARCHAR(64) DEFAULT NULL COMMENT '关联单据号',
  `creator_name` VARCHAR(50) DEFAULT NULL COMMENT '创建人姓名',
  `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '发生时间',
  `is_deleted` TINYINT(4) DEFAULT 0 COMMENT '逻辑删除',
  PRIMARY KEY (`id`),
  KEY `idx_item` (`item_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='办公用品库存流水表';

CREATE TABLE IF NOT EXISTS `supplies_request` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `order_no` VARCHAR(32) NOT NULL COMMENT '申请单号',
  `user_code` VARCHAR(50) NOT NULL COMMENT '领用人工号',
  `user_name` VARCHAR(50) NOT NULL COMMENT '领用人姓名',
  `dept_name` VARCHAR(100) DEFAULT NULL COMMENT '领用部门名称',
  `apply_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '申请时间',
  `reason` VARCHAR(255) DEFAULT NULL COMMENT '申请事由',
  `audit_status` TINYINT(4) DEFAULT 0 COMMENT '状态: 0待审核, 1通过, 2驳回, 3已发放',
  `auditor_name` VARCHAR(50) DEFAULT NULL COMMENT '审核人姓名',
  `audit_time` DATETIME DEFAULT NULL COMMENT '审核时间',
  `remark` VARCHAR(255) DEFAULT NULL COMMENT '审核备注',
  `creator_name` VARCHAR(50) DEFAULT NULL COMMENT '创建人姓名',
  `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updater_name` VARCHAR(50) DEFAULT NULL COMMENT '修改人姓名',
  `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `is_deleted` TINYINT(4) DEFAULT 0 COMMENT '逻辑删除',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_order_no` (`order_no`),
  KEY `idx_user_code` (`user_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='办公用品领用申请表';

CREATE TABLE IF NOT EXISTS `supplies_request_item` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `request_id` BIGINT(20) NOT NULL COMMENT '申请单ID',
  `item_id` BIGINT(20) NOT NULL COMMENT '物品ID',
  `quantity` INT(11) NOT NULL COMMENT '申领数量',
  `issued_quantity` INT(11) DEFAULT 0 COMMENT '实际发放数量',
  PRIMARY KEY (`id`),
  KEY `idx_request` (`request_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='领用申请明细表';
