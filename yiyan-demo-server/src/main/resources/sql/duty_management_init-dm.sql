-- 易研 OA - 值班管理功能建表 SQL (达梦数据库 DM 版本)

-- 1. 值班分组表
CREATE TABLE "oa_duty_group" (
  "id" BIGINT IDENTITY(1,1) NOT NULL,
  "name" VARCHAR(100) NOT NULL,
  "parent_id" BIGINT DEFAULT 0,
  "level" INT DEFAULT 1,
  "department_id" VARCHAR(100),
  "department_name" VARCHAR(100),
  "status" TINYINT DEFAULT 1,
  "creator" VARCHAR(64),
  "create_time" DATETIME DEFAULT CURRENT_TIMESTAMP,
  "updater" VARCHAR(64),
  "update_time" DATETIME DEFAULT CURRENT_TIMESTAMP,
  "update_ip" VARCHAR(255),
  "is_deleted" TINYINT DEFAULT 0,
  PRIMARY KEY ("id")
);

COMMENT ON TABLE "oa_duty_group" IS '值班分组表';
COMMENT ON COLUMN "oa_duty_group"."id" IS '主键ID';
COMMENT ON COLUMN "oa_duty_group"."name" IS '分组名称';
COMMENT ON COLUMN "oa_duty_group"."parent_id" IS '父级ID';
COMMENT ON COLUMN "oa_duty_group"."level" IS '分组层级 (1-科室级, 2-班组级)';
COMMENT ON COLUMN "oa_duty_group"."department_id" IS '绑定的HR系统科室ID';
COMMENT ON COLUMN "oa_duty_group"."department_name" IS '绑定的HR系统科室名称';
COMMENT ON COLUMN "oa_duty_group"."status" IS '状态 (1-启用 0-停用)';
COMMENT ON COLUMN "oa_duty_group"."creator" IS '创建者';
COMMENT ON COLUMN "oa_duty_group"."create_time" IS '创建时间';
COMMENT ON COLUMN "oa_duty_group"."updater" IS '更新者';
COMMENT ON COLUMN "oa_duty_group"."update_time" IS '更新时间';
COMMENT ON COLUMN "oa_duty_group"."update_ip" IS '最后更新IP';
COMMENT ON COLUMN "oa_duty_group"."is_deleted" IS '逻辑删除 (0-未删除, 1-已删除)';

-- 2. 值班排班表
CREATE TABLE "oa_duty_schedule" (
  "id" BIGINT IDENTITY(1,1) NOT NULL,
  "group_id" BIGINT NOT NULL,
  "user_code" VARCHAR(50) NOT NULL,
  "user_name" VARCHAR(50) NOT NULL,
  "duty_date" DATE NOT NULL,
  "remark" VARCHAR(255),
  "creator" VARCHAR(64),
  "create_time" DATETIME DEFAULT CURRENT_TIMESTAMP,
  "updater" VARCHAR(64),
  "update_time" DATETIME DEFAULT CURRENT_TIMESTAMP,
  "update_ip" VARCHAR(255),
  "is_deleted" TINYINT DEFAULT 0,
  PRIMARY KEY ("id")
);

CREATE INDEX "idx_duty_schedule_group" ON "oa_duty_schedule"("group_id");
CREATE INDEX "idx_duty_schedule_date" ON "oa_duty_schedule"("duty_date");

COMMENT ON TABLE "oa_duty_schedule" IS '值班排班表';
COMMENT ON COLUMN "oa_duty_schedule"."id" IS '主键ID';
COMMENT ON COLUMN "oa_duty_schedule"."group_id" IS '所属分组ID';
COMMENT ON COLUMN "oa_duty_schedule"."user_code" IS '排班人员工号';
COMMENT ON COLUMN "oa_duty_schedule"."user_name" IS '排班人员姓名';
COMMENT ON COLUMN "oa_duty_schedule"."duty_date" IS '值班日期';
COMMENT ON COLUMN "oa_duty_schedule"."remark" IS '备注';
COMMENT ON COLUMN "oa_duty_schedule"."creator" IS '创建者';
COMMENT ON COLUMN "oa_duty_schedule"."create_time" IS '创建时间';
COMMENT ON COLUMN "oa_duty_schedule"."updater" IS '更新者';
COMMENT ON COLUMN "oa_duty_schedule"."update_time" IS '更新时间';
COMMENT ON COLUMN "oa_duty_schedule"."update_ip" IS '最后更新IP';
COMMENT ON COLUMN "oa_duty_schedule"."is_deleted" IS '逻辑删除 (0-未删除, 1-已删除)';
