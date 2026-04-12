DROP TABLE IF EXISTS `oa_duty_group`;
DROP TABLE IF EXISTS `oa_duty_schedule`;

-- 值班分组表
CREATE TABLE `oa_duty_group` (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `name` varchar(100) NOT NULL COMMENT '分组名称',
  `parent_id` bigint(20) DEFAULT '0' COMMENT '父级ID',
  `level` int(11) DEFAULT '1' COMMENT '分组层级 (1-科室级, 2-班组级)',
  `department_id` varchar(100) DEFAULT NULL COMMENT '绑定的HR系统科室ID',
  `department_name` varchar(100) DEFAULT NULL COMMENT '绑定的HR系统科室名称',
  `status` tinyint(2) DEFAULT '1' COMMENT '状态 (1-启用 0-停用)',
  `creator` varchar(64) DEFAULT NULL COMMENT '创建者',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updater` varchar(64) DEFAULT NULL COMMENT '更新者',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `update_ip` varchar(255) DEFAULT NULL COMMENT '更新IP',
  `is_deleted` tinyint(2) DEFAULT '0' COMMENT '是否删除 (0-未删除，1-已删除)',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='值班分组表';

-- 值班排班表
CREATE TABLE `oa_duty_schedule` (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `group_id` bigint(20) NOT NULL COMMENT '所属分组ID',
  `user_code` varchar(50) NOT NULL COMMENT '排班人员工号',
  `user_name` varchar(50) NOT NULL COMMENT '排班人员姓名',
  `duty_date` date NOT NULL COMMENT '值班日期',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `creator` varchar(64) DEFAULT NULL COMMENT '创建者',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updater` varchar(64) DEFAULT NULL COMMENT '更新者',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `update_ip` varchar(255) DEFAULT NULL COMMENT '更新IP',
  `is_deleted` tinyint(2) DEFAULT '0' COMMENT '是否删除 (0-未删除，1-已删除)',
  PRIMARY KEY (`id`),
  KEY `idx_group_id` (`group_id`),
  KEY `idx_duty_date` (`duty_date`),
  KEY `idx_user_code` (`user_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='值班排班表';
