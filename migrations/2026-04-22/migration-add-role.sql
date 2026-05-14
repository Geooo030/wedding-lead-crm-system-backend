-- =====================================================
-- 迁移脚本: 2026-04-22 - 为用户表添加缺失字段 (MySQL 5.x 兼容版)
-- =====================================================
-- 目的: 修复登录错误，为用户表添加缺失字段
-- 影响表: users
-- 兼容: MySQL 5.6, 5.7
-- =====================================================

-- =====================================================
-- 检查并添加 email 字段
-- =====================================================
SET @column_exists = (
    SELECT COUNT(*)
    FROM INFORMATION_SCHEMA.COLUMNS
    WHERE TABLE_SCHEMA = DATABASE()
    AND TABLE_NAME = 'users'
    AND COLUMN_NAME = 'email'
);

SET @sql = IF(@column_exists = 0,
    'ALTER TABLE users ADD COLUMN email VARCHAR(100) NULL AFTER password',
    'SELECT "email column already exists"'
);
PREPARE stmt FROM @sql;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;

-- =====================================================
-- 检查并添加 role 字段
-- =====================================================
SET @column_exists = (
    SELECT COUNT(*)
    FROM INFORMATION_SCHEMA.COLUMNS
    WHERE TABLE_SCHEMA = DATABASE()
    AND TABLE_NAME = 'users'
    AND COLUMN_NAME = 'role'
);

SET @sql = IF(@column_exists = 0,
    'ALTER TABLE users ADD COLUMN role VARCHAR(20) DEFAULT ''AGENT'' AFTER email',
    'SELECT "role column already exists"'
);
PREPARE stmt FROM @sql;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;

-- =====================================================
-- 检查并添加 last_login_at 字段
-- =====================================================
SET @column_exists = (
    SELECT COUNT(*)
    FROM INFORMATION_SCHEMA.COLUMNS
    WHERE TABLE_SCHEMA = DATABASE()
    AND TABLE_NAME = 'users'
    AND COLUMN_NAME = 'last_login_at'
);

SET @sql = IF(@column_exists = 0,
    'ALTER TABLE users ADD COLUMN last_login_at DATETIME NULL AFTER updated_at',
    'SELECT "last_login_at column already exists"'
);
PREPARE stmt FROM @sql;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;

-- =====================================================
-- 更新 admin 用户
-- =====================================================
UPDATE users SET role = 'ADMIN' WHERE username = 'admin' AND (role IS NULL OR role = '');
UPDATE users SET email = 'admin@example.com' WHERE username = 'admin' AND (email IS NULL OR email = '');

-- =====================================================
-- 验证结果
-- =====================================================
SELECT
    COLUMN_NAME,
    DATA_TYPE,
    IS_NULLABLE,
    COLUMN_DEFAULT
FROM INFORMATION_SCHEMA.COLUMNS
WHERE TABLE_SCHEMA = DATABASE()
AND TABLE_NAME = 'users'
ORDER BY ORDINAL_POSITION;

SELECT 'migration-add-role.sql 执行完成' AS status;
