-- 易研 OA - 文件管理功能建表 SQL (达梦数据库 DM 版本)

-- 1. 文件分类表
CREATE TABLE "document_category" (
  "id" BIGINT IDENTITY(1,1) NOT NULL,
  "parent_id" BIGINT DEFAULT 0,
  "name" VARCHAR(100) NOT NULL,
  "sort" INT DEFAULT 0,
  "need_approval" TINYINT DEFAULT 0,
  "remark" VARCHAR(500),
  "creator" VARCHAR(64),
  "updater" VARCHAR(64),
  "update_ip" VARCHAR(64),
  "create_time" DATETIME DEFAULT CURRENT_TIMESTAMP,
  "update_time" DATETIME DEFAULT CURRENT_TIMESTAMP,
  "is_deleted" TINYINT DEFAULT 0,
  PRIMARY KEY ("id")
);

COMMENT ON TABLE "document_category" IS '文档分类表';
COMMENT ON COLUMN "document_category"."id" IS '主键ID';
COMMENT ON COLUMN "document_category"."parent_id" IS '父节点ID';
COMMENT ON COLUMN "document_category"."name" IS '分类名称';
COMMENT ON COLUMN "document_category"."sort" IS '排序';
COMMENT ON COLUMN "document_category"."need_approval" IS '是否需审签 (0-否, 1-是)';
COMMENT ON COLUMN "document_category"."remark" IS '备注';
COMMENT ON COLUMN "document_category"."creator" IS '创建者';
COMMENT ON COLUMN "document_category"."updater" IS '更新者';
COMMENT ON COLUMN "document_category"."update_ip" IS '最后更新IP';
COMMENT ON COLUMN "document_category"."create_time" IS '创建时间';
COMMENT ON COLUMN "document_category"."update_time" IS '更新时间';
COMMENT ON COLUMN "document_category"."is_deleted" IS '逻辑删除 (0-未删除, 1-已删除)';


-- 2. 文件信息主表
CREATE TABLE "document" (
  "id" BIGINT IDENTITY(1,1) NOT NULL,
  "category_id" BIGINT NOT NULL,
  "name" VARCHAR(255) NOT NULL,
  "type" VARCHAR(50),
  "size" BIGINT DEFAULT 0,
  "url" VARCHAR(1000) NOT NULL,
  "current_version" VARCHAR(50) DEFAULT 'V1.0',
  "status" INT DEFAULT 2,
  "uploader" VARCHAR(100),
  "creator" VARCHAR(64),
  "updater" VARCHAR(64),
  "update_ip" VARCHAR(64),
  "create_time" DATETIME DEFAULT CURRENT_TIMESTAMP,
  "update_time" DATETIME DEFAULT CURRENT_TIMESTAMP,
  "is_deleted" TINYINT DEFAULT 0,
  PRIMARY KEY ("id")
);

-- 达梦中创建索引
CREATE INDEX "idx_document_category" ON "document"("category_id");

COMMENT ON TABLE "document" IS '文档信息主表';
COMMENT ON COLUMN "document"."id" IS '主键ID';
COMMENT ON COLUMN "document"."category_id" IS '所属分类ID';
COMMENT ON COLUMN "document"."name" IS '文件名称';
COMMENT ON COLUMN "document"."type" IS '文件类型(扩展名)';
COMMENT ON COLUMN "document"."size" IS '文件大小(字节)';
COMMENT ON COLUMN "document"."url" IS '当前文件OSS地址';
COMMENT ON COLUMN "document"."current_version" IS '当前版本号';
COMMENT ON COLUMN "document"."status" IS '状态 (0-草稿, 1-审签中, 2-已发布)';
COMMENT ON COLUMN "document"."uploader" IS '上传人';
COMMENT ON COLUMN "document"."creator" IS '创建者';
COMMENT ON COLUMN "document"."updater" IS '更新者';
COMMENT ON COLUMN "document"."update_ip" IS '最后更新IP';
COMMENT ON COLUMN "document"."create_time" IS '创建时间';
COMMENT ON COLUMN "document"."update_time" IS '更新时间';
COMMENT ON COLUMN "document"."is_deleted" IS '逻辑删除';


-- 3. 文件历史版本表
CREATE TABLE "document_version" (
  "id" BIGINT IDENTITY(1,1) NOT NULL,
  "document_id" BIGINT NOT NULL,
  "version_num" VARCHAR(50) NOT NULL,
  "url" VARCHAR(1000) NOT NULL,
  "update_log" VARCHAR(500),
  "uploader" VARCHAR(100),
  "creator" VARCHAR(64),
  "updater" VARCHAR(64),
  "update_ip" VARCHAR(64),
  "create_time" DATETIME DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY ("id")
);

CREATE INDEX "idx_document_version_doc" ON "document_version"("document_id");

COMMENT ON TABLE "document_version" IS '文档历史版本记录表';
COMMENT ON COLUMN "document_version"."id" IS '主键ID';
COMMENT ON COLUMN "document_version"."document_id" IS '对应的主文档ID';
COMMENT ON COLUMN "document_version"."version_num" IS '版次 (如 V1.0, V2.0)';
COMMENT ON COLUMN "document_version"."url" IS '该版本文档地址';
COMMENT ON COLUMN "document_version"."update_log" IS '更新说明/留痕记录';
COMMENT ON COLUMN "document_version"."uploader" IS '该版本上传人';
COMMENT ON COLUMN "document_version"."creator" IS '创建者';
COMMENT ON COLUMN "document_version"."updater" IS '更新者';
COMMENT ON COLUMN "document_version"."update_ip" IS '最后更新IP';
COMMENT ON COLUMN "document_version"."create_time" IS '上传/归档时间';
