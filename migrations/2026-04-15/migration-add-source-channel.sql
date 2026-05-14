-- =====================================================
-- 迁移脚本: 2026-04-15 - 为 leads 表添加 lead_source 和 lead_channel 字段
-- =====================================================
-- 目的: 记录线索来源和渠道
-- 影响表: leads
-- 执行环境: DEV, PROD
-- 依赖: 无
-- 执行状态: ✅ DEV 已执行 | ✅ PROD 已执行
-- =====================================================

-- DEV 环境
USE `lead_crm_dev`;

ALTER TABLE `leads`
ADD COLUMN `lead_source` VARCHAR(20) NULL DEFAULT 'manual' COMMENT '客户来源 (ai/人工添加)'
AFTER `source_url`,
ADD COLUMN `lead_channel` VARCHAR(100) NULL COMMENT '客户渠道 (WhatsApp/Facebook/1688等)'
AFTER `lead_source`;

CREATE INDEX IF NOT EXISTS `idx_lead_source` ON `leads`(`lead_source`);

UPDATE `leads` SET `lead_source` = 'manual' WHERE `lead_source` IS NULL;

-- PROD 环境
USE `lead_crm`;

ALTER TABLE `leads`
ADD COLUMN IF NOT EXISTS `lead_source` VARCHAR(20) NULL DEFAULT 'manual' COMMENT '客户来源 (ai/人工添加)'
AFTER `source_url`,
ADD COLUMN IF NOT EXISTS `lead_channel` VARCHAR(100) NULL COMMENT '客户渠道 (WhatsApp/Facebook/1688等)'
AFTER `lead_source`;

CREATE INDEX IF NOT EXISTS `idx_lead_source` ON `leads`(`lead_source`);

UPDATE `leads` SET `lead_source` = 'manual' WHERE `lead_source` IS NULL;

SELECT 'migration-add-source-channel.sql 执行完成' AS status;
