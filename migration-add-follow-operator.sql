-- 添加跟进人字段的迁移脚本
USE `lead_crm_dev`;

-- 为 leads 表添加跟进人字段
ALTER TABLE `leads` 
ADD COLUMN `follow_operator` VARCHAR(100) NULL COMMENT '跟进人' 
AFTER `lead_channel`;

-- 添加索引
CREATE INDEX IF NOT EXISTS `idx_follow_operator` ON `leads`(`follow_operator`);

-- 同样更新生产环境的 lead_crm 库（如果存在）
USE `lead_crm`;

ALTER TABLE `leads` 
ADD COLUMN IF NOT EXISTS `follow_operator` VARCHAR(100) NULL COMMENT '跟进人' 
AFTER `lead_channel`;

CREATE INDEX IF NOT EXISTS `idx_follow_operator` ON `leads`(`follow_operator`);

SELECT '迁移完成！' as message;