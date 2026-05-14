# 数据库迁移目录

本目录包含所有数据库迁移脚本，按日期分类管理。

## 目录结构

```
migrations/
├── README.md                          # 本说明文件
├── 2026-04-22/                       # 2026年4月22日的迁移
│   ├── README.md                      # 该批次迁移说明
│   ├── migration-add-role.sql         # 为用户表添加 role 和 email 字段
│   └── migration-add-last-login-at.sql # 为用户表添加 last_login_at 字段
├── 2026-04-15/                       # 2026年4月15日的迁移
│   ├── README.md
│   ├── migration-add-agent-id.sql      # 为 follow_records 表添加 agent_id 字段
│   ├── migration-add-follow-operator.sql # 为 leads 表添加 follow_operator 字段
│   └── migration-add-source-channel.sql  # 为 leads 表添加 lead_source 和 lead_channel 字段
├── 2026-04-10/                       # 2026年4月10日的迁移
│   ├── README.md
│   └── migration-to-auto-increment.sql # 将所有表主键改为 AUTO_INCREMENT
├── 2026-04-01/                       # 2026年4月1日的迁移 (初始表结构)
│   ├── README.md
│   └── ddl.sql                        # 初始表结构定义
├── pending/                           # 待执行的迁移 (尚未在任何环境执行)
│   └── README.md
└── executed/                          # 已执行迁移的记录
    ├── DEV.md                         # DEV 环境已执行的迁移
    └── PROD.md                        # PROD 环境已执行的迁移
```

## 执行状态标记

### DEV 环境 (开发环境)
- 数据库: `lead_crm_dev`
- 状态文件: `executed/DEV.md`

### PROD 环境 (生产环境)
- 数据库: `lead_crm`
- 状态文件: `executed/PROD.md`

## 迁移执行原则

1. **按日期顺序执行** - 确保依赖关系正确
2. **先 DEV 后 PROD** - 新迁移先在开发环境验证
3. **记录执行结果** - 在 `executed/` 目录标记已执行的迁移
4. **保留历史** - 所有迁移脚本永久保留，不可删除

## 快速开始

### 查看待执行迁移
```bash
cat migrations/pending/README.md
```

### 查看环境执行状态
```bash
cat migrations/executed/DEV.md
cat migrations/executed/PROD.md
```
