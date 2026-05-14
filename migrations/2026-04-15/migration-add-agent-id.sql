-- =====================================================
-- 迁移脚本: 2026-04-15 - 为 follow_records 表添加 agent_id 字段
-- =====================================================
-- 目的: 支持按 agent 查询跟进记录
-- 影响表: follow_records
-- 执行环境: DEV, PROD
-- 依赖: 无
-- 执行状态: ✅ DEV 已执行 | ✅ PROD 已执行
-- =====================================================

-- DEV 环境
USE `lead_crm_dev`;

ALTER TABLE `follow_records`
ADD COLUMN IF NOT EXISTS `agent_id` VARCHAR(36) NULL
AFTER `operator_id`;

-- PROD 环境
USE `lead_crm`;

ALTER TABLE `follow_records`
ADD COLUMN IF NOT EXISTS `agent_id` VARCHAR(36) NULL
AFTER `operator_id`;

SELECT 'migration-add-agent-id.sql 执行完成' AS status;
