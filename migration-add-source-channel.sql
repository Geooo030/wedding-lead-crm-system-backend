-- 添加客户来源和渠道字段的迁移脚本
USE `lead_crm_dev`;

-- 为 leads 表添加客户来源和渠道字段
ALTER TABLE `leads` 
ADD COLUMN `lead_source` VARCHAR(20) NULL DEFAULT 'manual' COMMENT '客户来源 (ai/人工添加)' 
AFTER `source_url`,
ADD COLUMN `lead_channel` VARCHAR(100) NULL COMMENT '客户渠道 (WhatsApp/Facebook/1688等)' 
AFTER `lead_source`;

-- 添加索引
CREATE INDEX IF NOT EXISTS `idx_lead_source` ON `leads`(`lead_source`);

-- 更新现有数据
UPDATE `leads` SET `lead_source` = 'manual' WHERE `lead_source` IS NULL;

-- 同样更新生产环境的 lead_crm 库（如果存在）
USE `lead_crm`;

ALTER TABLE `leads` 
ADD COLUMN IF NOT EXISTS `lead_source` VARCHAR(20) NULL DEFAULT 'manual' COMMENT '客户来源 (ai/人工添加)' 
AFTER `source_url`,
ADD COLUMN IF NOT EXISTS `lead_channel` VARCHAR(100) NULL COMMENT '客户渠道 (WhatsApp/Facebook/1688等)' 
AFTER `lead_source`;

CREATE INDEX IF NOT EXISTS `idx_lead_source` ON `leads`(`lead_source`);

UPDATE `leads` SET `lead_source` = 'manual' WHERE `lead_source` IS NULL;

SELECT '迁移完成！' as message;
