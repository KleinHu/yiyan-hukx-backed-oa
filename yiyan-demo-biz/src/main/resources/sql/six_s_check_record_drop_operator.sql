-- 移除 six_s_check_record 表冗余字段 operator_code、operator_name
-- 在 240_oa 库中执行：USE 240_oa; source six_s_check_record_drop_operator.sql;

ALTER TABLE six_s_check_record
  DROP COLUMN operator_code,
  DROP COLUMN operator_name;
