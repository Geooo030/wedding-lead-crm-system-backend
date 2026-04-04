-- 迁移脚本：为 follow_records 表添加 agent_id 字段
-- 如果字段已存在则跳过

ALTER TABLE `follow_records` 
ADD COLUMN IF NOT EXISTS `agent_id` VARCHAR(36) NULL 
AFTER `operator_id`;
