# 2026-04-22 迁移

## 迁移内容

本次迁移包含以下变更：

### 1. migration-add-role.sql
**目的**: 为用户表添加 role 和 email 字段

**变更**:
- `users` 表添加 `email` 字段 (VARCHAR 100)
- `users` 表添加 `role` 字段 (VARCHAR 20, 默认 'AGENT')
- `users` 表添加 `last_login_at` 字段 (DATETIME)
- 更新 admin 用户 role 为 'ADMIN'

**影响范围**: users 表

**执行前状态**:
- DEV: ❌ 未执行
- PROD: ❌ 未执行

**执行时间**: 2026-04-22

---

## 执行步骤

### DEV 环境
```sql
USE lead_crm_dev;

-- 添加字段
ALTER TABLE users ADD COLUMN IF NOT EXISTS email VARCHAR(100) NULL AFTER password;
ALTER TABLE users ADD COLUMN IF NOT EXISTS role VARCHAR(20) DEFAULT 'AGENT' AFTER email;
ALTER TABLE users ADD COLUMN IF NOT EXISTS last_login_at DATETIME NULL AFTER updated_at;

-- 更新 admin 用户
UPDATE users SET role = 'ADMIN' WHERE username = 'admin';
UPDATE users SET email = 'admin@example.com' WHERE username = 'admin';

-- 确保所有用户有默认值
UPDATE users SET role = 'AGENT' WHERE role IS NULL OR role = '';
```

### PROD 环境
```sql
USE lead_crm;

-- 添加字段
ALTER TABLE users ADD COLUMN IF NOT EXISTS email VARCHAR(100) NULL AFTER password;
ALTER TABLE users ADD COLUMN IF NOT EXISTS role VARCHAR(20) DEFAULT 'AGENT' AFTER email;
ALTER TABLE users ADD COLUMN IF NOT EXISTS last_login_at DATETIME NULL AFTER updated_at;

-- 更新 admin 用户
UPDATE users SET role = 'ADMIN' WHERE username = 'admin';
UPDATE users SET email = 'admin@example.com' WHERE username = 'admin';

-- 确保所有用户有默认值
UPDATE users SET role = 'AGENT' WHERE role IS NULL OR role = '';
```
