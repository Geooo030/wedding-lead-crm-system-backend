-- =====================================================
-- 初始表结构: 2026-04-01
-- =====================================================
-- 目的: 创建所有初始表结构
-- 影响表: users, leads, follow_records, agent_tasks, reports
-- 执行环境: DEV, PROD
-- 执行状态: ✅ DEV 已执行 | ✅ PROD 已执行
-- =====================================================

-- 用户表
CREATE TABLE IF NOT EXISTS `users` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `username` VARCHAR(50) NOT NULL UNIQUE,
  `password_hash` VARCHAR(255) NOT NULL,
  `password` VARCHAR(100),
  `email` VARCHAR(100),
  `role` VARCHAR(20) DEFAULT 'AGENT',
  `created_at` DATETIME,
  `updated_at` DATETIME,
  `last_login_at` DATETIME,
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
  `status` VARCHAR(20) DEFAULT 'new_lead',
  `created_at` DATETIME,
  `updated_at` DATETIME,
  PRIMARY KEY (`id`),
  INDEX `idx_country` (`country`),
  INDEX `idx_status` (`status`),
  INDEX `idx_priority` (`priority_level`),
  INDEX `idx_created_at` (`created_at`)
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

-- 初始化管理员用户（密码：admin123）
INSERT INTO `users` (`id`, `username`, `password_hash`, `password`, `email`, `role`, `created_at`, `updated_at`)
VALUES (
  1,
  'admin',
  '$2a$10$EixZaYVK1fsbw1ZfbX3OXePaWxn96p36WQoeG6Lruj3vjPGga31lW',
  'admin123',
  'admin@example.com',
  'ADMIN',
  NOW(),
  NOW()
) ON DUPLICATE KEY UPDATE `role` = 'ADMIN', `email` = 'admin@example.com', `updated_at` = NOW();

SELECT 'ddl.sql 初始化完成' AS status;
