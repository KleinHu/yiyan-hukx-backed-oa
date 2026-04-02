-- 1. 为文章主表新增必读标记字段
ALTER TABLE article ADD COLUMN is_must_read tinyint(1) DEFAULT 0 COMMENT '是否为必读项(1-是，0-否)';

-- 2. 新建已阅回执表
CREATE TABLE oa_article_receipt (
  id bigint(20) NOT NULL COMMENT '主键',
  article_id bigint(20) NOT NULL COMMENT '文章ID',
  user_id varchar(64) NOT NULL COMMENT '用户ID',
  user_name varchar(50) DEFAULT NULL COMMENT '用户名称',
  dept_id varchar(64) DEFAULT NULL COMMENT '部门ID',
  dept_name varchar(255) DEFAULT NULL COMMENT '部门名称',
  creator varchar(64) DEFAULT NULL COMMENT '创建者',
  create_time datetime DEFAULT CURRENT_TIMESTAMP COMMENT '确认时间',
  updater varchar(64) DEFAULT NULL COMMENT '更新者',
  update_time datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  update_ip varchar(255) DEFAULT NULL COMMENT '更新IP',
  is_deleted tinyint(2) DEFAULT 0 COMMENT '是否逻辑删除',
  PRIMARY KEY (id),
  UNIQUE KEY uk_article_user (article_id, user_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='文章已阅回执表';
