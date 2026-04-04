-- 迁移脚本：将所有表主键改为 AUTO_INCREMENT
-- 请先备份数据库！
-- 执行前请确保没有其他程序在访问数据库

SET FOREIGN_KEY_CHECKS = 0;

-- ============================================
-- 1. 用户表
-- ============================================

-- 删除外键约束（如果有）
-- ALTER TABLE follow_records DROP FOREIGN KEY IF EXISTS fk_follow_operator;

-- 修改 users 表
ALTER TABLE users 
  MODIFY COLUMN id BIGINT NOT NULL AUTO_INCREMENT FIRST,
  DROP PRIMARY KEY,
  ADD PRIMARY KEY (id);

-- ============================================
-- 2. 客户线索表
-- ============================================

-- 删除外键约束（如果有）
-- ALTER TABLE follow_records DROP FOREIGN KEY IF EXISTS fk_follow_lead;

-- 修改 leads 表
ALTER TABLE leads 
  MODIFY COLUMN id BIGINT NOT NULL AUTO_INCREMENT FIRST,
  DROP PRIMARY KEY,
  ADD PRIMARY KEY (id);

-- ============================================
-- 3. 跟进记录表
-- ============================================

-- 修改 follow_records 表
ALTER TABLE follow_records 
  MODIFY COLUMN id BIGINT NOT NULL AUTO_INCREMENT FIRST,
  DROP PRIMARY KEY,
  ADD PRIMARY KEY (id),
  MODIFY COLUMN lead_id BIGINT NULL,
  MODIFY COLUMN operator_id BIGINT NULL,
  MODIFY COLUMN agent_id BIGINT NULL;

-- ============================================
-- 4. Agent 任务表
-- ============================================

ALTER TABLE agent_tasks 
  MODIFY COLUMN id BIGINT NOT NULL AUTO_INCREMENT FIRST,
  DROP PRIMARY KEY,
  ADD PRIMARY KEY (id);

-- ============================================
-- 5. 报表表
-- ============================================

ALTER TABLE reports 
  MODIFY COLUMN id BIGINT NOT NULL AUTO_INCREMENT FIRST,
  DROP PRIMARY KEY,
  ADD PRIMARY KEY (id);

SET FOREIGN_KEY_CHECKS = 1;

-- ============================================
-- 重新设置自增起始值（可选）
-- ============================================

-- ALTER TABLE users AUTO_INCREMENT = 1;
-- ALTER TABLE leads AUTO_INCREMENT = 1;
-- ALTER TABLE follow_records AUTO_INCREMENT = 1;
-- ALTER TABLE agent_tasks AUTO_INCREMENT = 1;
-- ALTER TABLE reports AUTO_INCREMENT = 1;
