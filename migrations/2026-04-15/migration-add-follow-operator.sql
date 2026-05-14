-- =====================================================
-- 迁移脚本: 2026-04-15 - 为 leads 表添加 follow_operator 字段
-- =====================================================
-- 目的: 记录线索的跟进负责人
-- 影响表: leads
-- 执行环境: DEV, PROD
-- 依赖: 无
-- 执行状态: ✅ DEV 已执行 | ✅ PROD 已执行
-- =====================================================

-- DEV 环境
USE `lead_crm_dev`;

ALTER TABLE `leads`
ADD COLUMN `follow_operator` VARCHAR(100) NULL COMMENT '跟进人'
AFTER `lead_channel`;

CREATE INDEX IF NOT EXISTS `idx_follow_operator` ON `leads`(`follow_operator`);

-- PROD 环境
USE `lead_crm`;

ALTER TABLE `leads`
ADD COLUMN IF NOT EXISTS `follow_operator` VARCHAR(100) NULL COMMENT '跟进人'
AFTER `lead_channel`;

CREATE INDEX IF NOT EXISTS `idx_follow_operator` ON `leads`(`follow_operator`);

SELECT 'migration-add-follow-operator.sql 执行完成' AS status;
