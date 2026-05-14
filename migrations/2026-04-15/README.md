# 2026-04-15 迁移

## 迁移内容

本次迁移包含以下变更：

### 1. migration-add-agent-id.sql
**目的**: 为 follow_records 表添加 agent_id 字段
**变更**: `follow_records` 表添加 `agent_id` 字段 (VARCHAR 36)
**执行状态**: ✅ DEV 已执行 | ✅ PROD 已执行

### 2. migration-add-follow-operator.sql
**目的**: 为 leads 表添加跟进人字段
**变更**: `leads` 表添加 `follow_operator` 字段，并创建索引
**执行状态**: ✅ DEV 已执行 | ✅ PROD 已执行

### 3. migration-add-source-channel.sql
**目的**: 为 leads 表添加客户来源和渠道字段
**变更**: `leads` 表添加 `lead_source` 和 `lead_channel` 字段
**执行状态**: ✅ DEV 已执行 | ✅ PROD 已执行
