# 2026-04-10 迁移

## 迁移内容

### 1. migration-to-auto-increment.sql
**目的**: 将所有表主键改为 AUTO_INCREMENT
**变更**:
- `users` 表 id 改为自增
- `leads` 表 id 改为自增
- `follow_records` 表 id 改为自增
- `agent_tasks` 表 id 改为自增
- `reports` 表 id 改为自增

**执行状态**: ✅ DEV 已执行 | ⚠️ PROD 需确认

**注意事项**:
- 执行前请备份数据库
- 执行时确保没有其他程序在访问数据库
