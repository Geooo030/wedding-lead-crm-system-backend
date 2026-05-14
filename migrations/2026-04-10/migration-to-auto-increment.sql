-- =====================================================
-- 迁移脚本: 2026-04-10 - 将所有表主键改为 AUTO_INCREMENT
-- =====================================================
-- 目的: 简化主键管理，支持自增ID
-- 影响表: users, leads, follow_records, agent_tasks, reports
-- 执行环境: DEV, PROD
-- 依赖: 无
-- 执行状态: ✅ DEV 已执行 | ⚠️ PROD 需确认
-- 注意事项: 执行前请备份数据库！
-- =====================================================

SET FOREIGN_KEY_CHECKS = 0;

-- ====================
-- 1. 用户表
-- ====================
ALTER TABLE `users`
MODIFY COLUMN `id` BIGINT NOT NULL AUTO_INCREMENT FIRST,
DROP PRIMARY KEY,
ADD PRIMARY KEY (`id`);

-- ====================
-- 2. 客户线索表
-- ====================
ALTER TABLE `leads`
MODIFY COLUMN `id` BIGINT NOT NULL AUTO_INCREMENT FIRST,
DROP PRIMARY KEY,
ADD PRIMARY KEY (`id`);

-- ====================
-- 3. 跟进记录表
-- ====================
ALTER TABLE `follow_records`
MODIFY COLUMN `id` BIGINT NOT NULL AUTO_INCREMENT FIRST,
DROP PRIMARY KEY,
ADD PRIMARY KEY (`id`),
MODIFY COLUMN `lead_id` BIGINT NULL,
MODIFY COLUMN `operator_id` BIGINT NULL,
MODIFY COLUMN `agent_id` BIGINT NULL;

-- ====================
-- 4. Agent 任务表
-- ====================
ALTER TABLE `agent_tasks`
MODIFY COLUMN `id` BIGINT NOT NULL AUTO_INCREMENT FIRST,
DROP PRIMARY KEY,
ADD PRIMARY KEY (`id`);

-- ====================
-- 5. 报表表
-- ====================
ALTER TABLE `reports`
MODIFY COLUMN `id` BIGINT NOT NULL AUTO_INCREMENT FIRST,
DROP PRIMARY KEY,
ADD PRIMARY KEY (`id`);

SET FOREIGN_KEY_CHECKS = 1;

SELECT 'migration-to-auto-increment.sql 执行完成' AS status;
