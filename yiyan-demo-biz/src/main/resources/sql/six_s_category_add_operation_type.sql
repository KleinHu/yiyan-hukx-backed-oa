-- 为 six_s_category 表增加「操作类型」字段
-- 在 240_oa 库中执行：USE 240_oa; source six_s_category_add_operation_type.sql;

ALTER TABLE six_s_category
  ADD COLUMN operation_type tinyint NOT NULL DEFAULT 1 COMMENT '操作类型：1加分 2减分';
