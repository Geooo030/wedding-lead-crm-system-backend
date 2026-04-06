-- 从生产环境同步数据到开发环境的脚本

-- 1. 确保开发环境数据库存在
CREATE DATABASE IF NOT EXISTS `lead_crm_dev` CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- 2. 切换到开发环境数据库
USE `lead_crm_dev`;

-- 3. 确保开发环境的表结构与生产环境一致
-- 用户表
CREATE TABLE IF NOT EXISTS `users` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `username` VARCHAR(50) NOT NULL UNIQUE,
  `password_hash` VARCHAR(255) NOT NULL,
  `password` VARCHAR(100),
  `created_at` DATETIME,
  `updated_at` DATETIME,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- 客户线索表
CREATE TABLE IF NOT EXISTS `leads` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `company_name` VARCHAR(255) NOT NULL,
  `company_type` VARCHAR(100),
  `country` VARCHAR(100),
  `region` VARCHAR(100),
  `address` VARCHAR(500),
  `contact_phone` VARCHAR(50),
  `contact_email` VARCHAR(100),
  `website` VARCHAR(255),
  `business_scope` TEXT,
  `intent_signals` TEXT,
  `decision_maker_role` VARCHAR(100),
  `priority_score` INT DEFAULT 0,
  `priority_level` VARCHAR(10) DEFAULT 'warm',
  `source_url` VARCHAR(500),
  `notes` TEXT,
  `lead_source` VARCHAR(20) DEFAULT 'manual',
  `lead_channel` VARCHAR(100),
  `follow_operator` VARCHAR(100),
  `status` VARCHAR(20) DEFAULT 'new_lead',
  `created_at` DATETIME,
  `updated_at` DATETIME,
  PRIMARY KEY (`id`),
  INDEX `idx_country` (`country`),
  INDEX `idx_status` (`status`),
  INDEX `idx_priority` (`priority_level`),
  INDEX `idx_created_at` (`created_at`),
  INDEX `idx_lead_source` (`lead_source`),
  INDEX `idx_follow_operator` (`follow_operator`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- 跟进记录表
CREATE TABLE IF NOT EXISTS `follow_records` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `lead_id` BIGINT,
  `operator_id` BIGINT,
  `agent_id` BIGINT,
  `contact_method` VARCHAR(20) NOT NULL,
  `contact_result` VARCHAR(20) NOT NULL,
  `customer_intention` VARCHAR(20) DEFAULT 'medium',
  `current_stage` VARCHAR(20) DEFAULT 'new_lead',
  `notes` TEXT,
  `next_action` TEXT,
  `next_action_date` DATE,
  `created_at` DATETIME,
  PRIMARY KEY (`id`),
  INDEX `idx_lead_id` (`lead_id`),
  INDEX `idx_operator_id` (`operator_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- 代理任务表
CREATE TABLE IF NOT EXISTS `agent_tasks` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `task_type` VARCHAR(20) NOT NULL,
  `task_params` TEXT,
  `status` VARCHAR(20) DEFAULT 'pending',
  `result` TEXT,
  `scheduled_at` DATETIME,
  `started_at` DATETIME,
  `completed_at` DATETIME,
  `error_message` TEXT,
  `created_at` DATETIME,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- 报表表
CREATE TABLE IF NOT EXISTS `reports` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `report_type` VARCHAR(20) NOT NULL,
  `report_date` DATE NOT NULL,
  `metrics` TEXT,
  `agent_summary` TEXT,
  `created_at` DATETIME,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_type_date` (`report_type`, `report_date`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- 4. 禁用外键约束，以确保数据同步的顺利进行
SET FOREIGN_KEY_CHECKS = 0;

-- 5. 清空开发环境的现有数据
TRUNCATE TABLE `users`;
TRUNCATE TABLE `leads`;
TRUNCATE TABLE `follow_records`;
TRUNCATE TABLE `agent_tasks`;
TRUNCATE TABLE `reports`;

-- 6. 从生产环境复制数据到开发环境
-- 复制用户数据
INSERT INTO `lead_crm_dev`.`users` (`id`, `username`, `password_hash`, `password`, `created_at`, `updated_at`)
SELECT `id`, `username`, `password_hash`, `password`, `created_at`, `updated_at`
FROM `lead_crm`.`users`;

-- 复制客户线索数据
INSERT INTO `lead_crm_dev`.`leads` (`id`, `company_name`, `company_type`, `country`, `region`, `address`, 
`contact_phone`, `contact_email`, `website`, `business_scope`, `intent_signals`, `decision_maker_role`, 
`priority_score`, `priority_level`, `source_url`, `notes`, `lead_source`, `lead_channel`, `follow_operator`, 
`status`, `created_at`, `updated_at`)
SELECT `id`, `company_name`, `company_type`, `country`, `region`, `address`, 
`contact_phone`, `contact_email`, `website`, `business_scope`, `intent_signals`, `decision_maker_role`, 
`priority_score`, `priority_level`, `source_url`, `notes`, `lead_source`, `lead_channel`, `follow_operator`, 
`status`, `created_at`, `updated_at`
FROM `lead_crm`.`leads`;

-- 复制跟进记录数据
INSERT INTO `lead_crm_dev`.`follow_records` (`id`, `lead_id`, `operator_id`, `agent_id`, `contact_method`, 
`contact_result`, `customer_intention`, `current_stage`, `notes`, `next_action`, `next_action_date`, `created_at`)
SELECT `id`, `lead_id`, `operator_id`, `agent_id`, `contact_method`, 
`contact_result`, `customer_intention`, `current_stage`, `notes`, `next_action`, `next_action_date`, `created_at`
FROM `lead_crm`.`follow_records`;

-- 复制代理任务数据
INSERT INTO `lead_crm_dev`.`agent_tasks` (`id`, `task_type`, `task_params`, `status`, `result`, 
`scheduled_at`, `started_at`, `completed_at`, `error_message`, `created_at`)
SELECT `id`, `task_type`, `task_params`, `status`, `result`, 
`scheduled_at`, `started_at`, `completed_at`, `error_message`, `created_at`
FROM `lead_crm`.`agent_tasks`;

-- 复制报表数据
INSERT INTO `lead_crm_dev`.`reports` (`id`, `report_type`, `report_date`, `metrics`, `agent_summary`, `created_at`)
SELECT `id`, `report_type`, `report_date`, `metrics`, `agent_summary`, `created_at`
FROM `lead_crm`.`reports`;

-- 7. 重新启用外键约束
SET FOREIGN_KEY_CHECKS = 1;

-- 8. 重置自增主键的起始值
ALTER TABLE `users` AUTO_INCREMENT = (SELECT MAX(`id`) + 1 FROM `users`);
ALTER TABLE `leads` AUTO_INCREMENT = (SELECT MAX(`id`) + 1 FROM `leads`);
ALTER TABLE `follow_records` AUTO_INCREMENT = (SELECT MAX(`id`) + 1 FROM `follow_records`);
ALTER TABLE `agent_tasks` AUTO_INCREMENT = (SELECT MAX(`id`) + 1 FROM `agent_tasks`);
ALTER TABLE `reports` AUTO_INCREMENT = (SELECT MAX(`id`) + 1 FROM `reports`);

-- 9. 验证同步结果
SELECT 
  (SELECT COUNT(*) FROM `users`) AS `users_count`,
  (SELECT COUNT(*) FROM `leads`) AS `leads_count`,
  (SELECT COUNT(*) FROM `follow_records`) AS `follow_records_count`,
  (SELECT COUNT(*) FROM `agent_tasks`) AS `agent_tasks_count`,
  (SELECT COUNT(*) FROM `reports`) AS `reports_count`;

SELECT '数据同步完成！' as message;