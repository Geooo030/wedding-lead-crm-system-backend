# PROD 环境已执行迁移记录

数据库: `lead_crm`
最后更新: 2026-04-22

## 执行历史

### ⚠️ 2026-04-22
- [ ] migration-add-role.sql (待执行 - 需尽快执行以修复登录错误)

### ✅ 2026-04-15
- [x] migration-add-agent-id.sql
- [x] migration-add-follow-operator.sql
- [x] migration-add-source-channel.sql

### ⚠️ 2026-04-10
- [ ] migration-to-auto-increment.sql (需确认是否已执行)

### ✅ 2026-04-01
- [x] ddl.sql (初始表结构)

## 紧急待执行
```sql
-- 必须执行！否则用户无法登录
USE lead_crm;
ALTER TABLE users ADD COLUMN IF NOT EXISTS role VARCHAR(20) DEFAULT 'AGENT';
UPDATE users SET role = 'ADMIN' WHERE username = 'admin';
```

## 备注
- 生产环境迁移需谨慎执行
- 建议在低峰期执行
- 执行前请备份数据库
