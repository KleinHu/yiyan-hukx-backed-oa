-- 积分台账按年度区分：将唯一键从 (user_code, is_deleted) 改为 (user_code, year, is_deleted)
-- 在 240_oa 库中执行：mysql -u user -p 240_oa < six_s_account_add_year_unique.sql

USE 240_oa;

-- 删除旧唯一键
ALTER TABLE six_s_account DROP INDEX uk_user_code;

-- 添加新唯一键（同一工号同一年度仅一条台账）
ALTER TABLE six_s_account ADD UNIQUE KEY uk_user_code_year (user_code, year, is_deleted);
