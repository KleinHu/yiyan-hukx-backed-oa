-- 办公用品分类表
CREATE TABLE `supplies_category` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  `parent_id` bigint(20) DEFAULT '0',
  `sort` int(11) DEFAULT '0',
  `creator` varchar(64) DEFAULT NULL COMMENT '创建者',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `updater` varchar(64) DEFAULT NULL COMMENT '更新者',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `is_deleted` tinyint(4) DEFAULT '0',
  `update_ip` varchar(255) DEFAULT NULL COMMENT '更新IP',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='办公用品分类表';

-- 办公用品档案表
CREATE TABLE `supplies_item` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `category_id` bigint(20) NOT NULL COMMENT '所属末级分类ID',
  `name` varchar(100) NOT NULL COMMENT '物品名称',
  `spec` varchar(100) DEFAULT NULL COMMENT '规格型号',
  `unit` varchar(20) DEFAULT NULL COMMENT '单位',
  `price` decimal(10,2) DEFAULT '0.00' COMMENT '参考单价',
  `min_stock` int(11) DEFAULT '0' COMMENT '最低库存报警值',
  `status` tinyint(4) DEFAULT '1' COMMENT '状态 (1启用, 0禁用)',
  `creator` varchar(64) DEFAULT NULL COMMENT '创建者',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updater` varchar(64) DEFAULT NULL COMMENT '更新者',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `is_deleted` tinyint(4) DEFAULT '0' COMMENT '逻辑删除 (0-正常, 1-已删除)',
  `update_ip` varchar(255) DEFAULT NULL COMMENT '更新IP',
  PRIMARY KEY (`id`),
  KEY `idx_category` (`category_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='办公用品档案表';

-- 办公用品库存表
CREATE TABLE `supplies_inventory` (
  `item_id` bigint(20) NOT NULL COMMENT '物品ID',
  `stock` int(11) NOT NULL DEFAULT '0' COMMENT '当前库存量',
  `lock_stock` int(11) DEFAULT '0' COMMENT '待领用（锁定）库存',
  `updater_name` varchar(50) DEFAULT NULL COMMENT '修改人姓名',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `creator` varchar(255) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_ip` varchar(255) DEFAULT NULL,
  `updater` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`item_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='办公用品库存表';

-- 办公用品库存流水表
CREATE TABLE `supplies_record` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `item_id` bigint(20) NOT NULL COMMENT '物品ID',
  `type` tinyint(4) NOT NULL COMMENT '1-入库, 2-出库',
  `scenario` tinyint(4) NOT NULL COMMENT '场景: 1-采购, 2-领用, 3-盘点, 4-退库, 5-报损',
  `quantity` int(11) NOT NULL COMMENT '变动数量',
  `rel_no` varchar(64) DEFAULT NULL COMMENT '关联单据号',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '发生时间',
  `is_deleted` tinyint(4) DEFAULT '0' COMMENT '逻辑删除',
  `creator` varchar(64) DEFAULT NULL COMMENT '创建者',
  `updater` varchar(64) DEFAULT NULL COMMENT '更新者',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `update_ip` varchar(255) DEFAULT NULL COMMENT '更新IP',
  `creator_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_item` (`item_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='办公用品库存流水表';

-- 办公用品领用申请表
CREATE TABLE `supplies_request` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `order_no` varchar(32) NOT NULL COMMENT '申请单号',
  `user_code` varchar(50) NOT NULL COMMENT '领用人工号',
  `user_name` varchar(50) NOT NULL COMMENT '领用人姓名',
  `dept_name` varchar(100) DEFAULT NULL COMMENT '领用部门名称',
  `apply_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '申请时间',
  `reason` varchar(255) DEFAULT NULL COMMENT '申请事由',
  `audit_status` tinyint(4) DEFAULT '0' COMMENT '状态: 0待审核, 1通过, 2驳回, 3已发放',
  `auditor_name` varchar(50) DEFAULT NULL COMMENT '审核人姓名',
  `audit_time` datetime DEFAULT NULL COMMENT '审核时间',
  `remark` varchar(255) DEFAULT NULL COMMENT '审核备注',
  `creator` varchar(64) DEFAULT NULL COMMENT '创建者',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updater` varchar(64) DEFAULT NULL COMMENT '更新者',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `is_deleted` tinyint(4) DEFAULT '0' COMMENT '逻辑删除',
  `update_ip` varchar(255) DEFAULT NULL COMMENT '更新IP',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_order_no` (`order_no`),
  KEY `idx_user_code` (`user_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='办公用品领用申请表';

-- 领用申请明细表
CREATE TABLE `supplies_request_item` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `request_id` bigint(20) NOT NULL COMMENT '申请单ID',
  `item_id` bigint(20) NOT NULL COMMENT '物品ID',
  `quantity` int(11) NOT NULL COMMENT '申领数量',
  `issued_quantity` int(11) DEFAULT '0' COMMENT '实际发放数量',
  PRIMARY KEY (`id`),
  KEY `idx_request` (`request_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='领用申请明细表';
