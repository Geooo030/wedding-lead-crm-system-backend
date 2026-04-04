# Lead CRM 环境配置说明

## 环境概述

后端支持两种环境：
- **dev** (开发环境) - 本地开发使用，连接 `lead_crm_dev` 数据库
- **prod** (生产环境) - Docker 部署使用，连接生产数据库

## 配置文件

- `application.yml` - 主配置文件
- `application-dev.yml` - 开发环境配置
- `application-prod.yml` - 生产环境配置

---

## 开发环境 (Dev)

### 数据库配置
- 数据库名: `lead_crm_dev`
- 地址: `localhost:3306`
- 用户名: `root`
- 密码: `root`

### 特性
- 显示 SQL 日志
- JPA 自动更新表结构 (ddl-auto: update)
- DEBUG 级别日志
- JWT Secret: `lead-crm-dev-jwt-secret-key-for-development`

### 启动方式

**方式 1: IDEA / Maven 启动**
```bash
# 方式 A: 在 application.yml 中设置 spring.profiles.active=dev (默认)
mvn spring-boot:run

# 方式 B: 通过启动参数指定
mvn spring-boot:run -Dspring-boot.run.profiles=dev

# 方式 C: 打包后运行
java -jar target/lead-crm-backend.jar --spring.profiles.active=dev
```

**方式 2: IDEA 启动配置**
1. 打开 Run/Debug Configurations
2. 添加 VM Options: `-Dspring.profiles.active=dev`
3. 或添加 Program arguments: `--spring.profiles.active=dev`

---

## 生产环境 (Prod)

### 数据库配置
- 数据库名: `lead_crm`
- 地址: `43.155.154.87:3306`

### 特性
- 不显示 SQL 日志
- JPA 仅验证表结构 (ddl-auto: validate)
- INFO 级别日志
- JWT Secret: 环境变量 `JWT_SECRET` 或默认值

### 启动方式

**Docker 部署 (推荐)**
```bash
# 构建镜像
docker build -t lead-crm-backend:latest .

# 运行容器（已配置默认使用 prod）
docker run -d -p 8080:8080 lead-crm-backend:latest

# 或通过环境变量显式指定
docker run -d -p 8080:8080 -e SPRING_PROFILES_ACTIVE=prod lead-crm-backend:latest

# 指定 JWT Secret
docker run -d -p 8080:8080 -e JWT_SECRET=your-secret-key lead-crm-backend:latest
```

**直接运行 JAR**
```bash
java -jar target/lead-crm-backend.jar --spring.profiles.active=prod

# 或通过系统属性
java -Dspring.profiles.active=prod -jar target/lead-crm-backend.jar
```

---

## 数据库初始化

### 开发环境初始化
执行 `ddl-dev.sql` 文件：
```bash
mysql -u root -p < ddl-dev.sql
```

或在 MySQL 客户端中：
```sql
source d:/agent/code/wedding-lead-crm-system-backend/ddl-dev.sql
```

### 生产环境初始化
执行 `ddl.sql` 文件（需要先执行 `migration-to-auto-increment.sql` 迁移脚本）。

---

## 默认账号

- 用户名: `admin`
- 密码: `admin123`

---

## 环境切换速查表

| 环境 | 配置文件 | 数据库 | JPA ddl-auto | SQL 日志 |
|------|----------|--------|---------------|----------|
| dev | application-dev.yml | lead_crm_dev | update | true |
| prod | application-prod.yml | lead_crm | validate | false |
