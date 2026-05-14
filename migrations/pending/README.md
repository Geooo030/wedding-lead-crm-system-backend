# 待执行迁移

本目录包含尚未在任何环境执行的迁移脚本。

## 当前待执行

### 无

## 未来可能需要的迁移

### 1. agent_tasks 表结构改造 (计划中)
当 AgentTask 领域模型完全重构后，可能需要:
- 重命名 task_type -> title
- 添加 description 字段
- 添加 priority 字段
- 添加 deadline 字段
- 调整 status 枚举值

**状态**: 计划中，等待业务确认

---

## 如何添加新迁移

1. 在 `migrations/` 下创建新日期目录
2. 创建迁移 SQL 文件
3. 更新对应日期的 README.md
4. 执行后更新 `executed/DEV.md` 和 `executed/PROD.md`
