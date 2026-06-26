# 家庭财务分析APP

## What This Is

一个专为个人家庭设计的财务分析和规划Android应用，基于现有家庭财务档案数据，提供全方位的资产负债分析、资产配置评估、现金流管理和投资组合跟踪功能。

## Core Value

**核心价值**：帮助用户从多个维度清晰了解家庭财务状况，基于专业的金融视角提供资产配置分析和优化建议，实现财务健康度的持续监控和提升。

## Context

### 家庭财务档案（2026年）

**个人情况**：
- 年龄：37岁
- 职业：游戏客户端程序员（系统架构、性能优化方向）
- 家庭：已婚，有孩子
- 收入：每年可新增存款约25万元（扣除开支）
- 职业风险：游戏行业周期性、公司经营风险、程序员年龄压力

**资产概况**：
- 房产：6套（红牌楼维港、桐梓林欧城、双发金英汇、郫都铂金时代、攀枝花、荷花池商铺）
- 现金：约70万元（目标100万元）
- 负债：公积金贷款35万，月供1700元
- 保险：5人17份保单，年保费约41,946元

**资产配置方案**（四池模型）：
- 应急池：40万（高流动、低波动、本金安全优先）
- 稳定池：40万（低久期、高信用、分散配置）
- 增值池：持续做大（ETF定投，不择时重仓）
- 机会池：5万-10万（市场大跌时加仓）

### 技术架构

**开发平台**：Android（仅Android）
**技术栈**：
- 语言：Kotlin
- UI框架：Jetpack Compose
- 架构模式：MVVM
- 数据库：Room (SQLite)
- UI组件库：Material Design 3
- 图表库：MPAndroidChart

**开发环境**：
- IDE：Android Studio
- 版本控制：Git
- 操作系统：Windows

### 数据管理

**数据导入**：
- 飞书文档导入
- Excel/CSV导入
- 手动录入
- 模板导入

**数据导出**：
- Excel导出
- CSV导出
- PDF报表
- 图片导出

**数据备份**：
- 本地备份
- 自动备份
- 手动备份

### 用户界面

**设计风格**：Material Design 3
**主题颜色**：专业蓝色
**首页布局**：卡片式布局
**数据展示**：数字表格、图表可视化、卡片摘要、多种方式结合
**操作流程**：引导式操作

### 核心功能

**MVP功能（第一阶段）**：
- 资产录入：支持手动录入各类资产（房产、现金、投资等）
- 负债录入：支持录入各类负债（房贷、信用卡等）
- 净资产计算：自动计算净资产，展示资产配置
- 基础报表：生成简单的资产负债表和资产配置图

**后续功能**：
- 现金流管理：收支记录、预算管理、现金流预测、结余分析
- 投资跟踪：ETF/基金跟踪、配置再平衡提醒、定投计划管理、市场数据集成
- 保险管理：保单信息管理、保费支出跟踪、保障缺口分析、理赔提醒
- AI分析：多模型支持（OpenAI、Claude、本地模型等），提供智能财务建议

**扩展功能**：
- 数据导入导出：支持Excel、CSV等格式的数据导入导出
- 自定义分类：支持自定义资产、负债、收支分类

### 报表类型

- 资产负债表：家庭版资产负债表，展示净资产和资产构成
- 资产配置图：资产配置饼图或环形图，展示各类资产占比
- 现金流报表：月度/年度现金流入流出分析
- 财务健康度评分：综合评估家庭财务健康状况

### 性能要求

- 启动速度：APP启动时间不超过2秒
- 数据加载：大量数据加载时保持流畅
- 内存占用：控制内存占用，避免内存泄漏
- 离线支持：部分离线，核心功能离线可用

### 安全与隐私

- 权限管理：最小权限原则
- 数据备份：支持手动备份、自动备份、备份恢复
- 更新策略：直接覆盖安装，数据不丢即可

## Requirements

### Validated

(None yet — ship to validate)

### Active

- [ ] **ASSET-01**: 支持手动录入各类资产（房产、现金、投资等）
- [ ] **ASSET-02**: 支持录入各类负债（房贷、信用卡等）
- [ ] **ASSET-03**: 自动计算净资产，展示资产配置
- [ ] **ASSET-04**: 生成简单的资产负债表和资产配置图
- [ ] **DATA-01**: 支持飞书文档导入财务数据
- [ ] **DATA-02**: 支持Excel/CSV导入财务数据
- [ ] **DATA-03**: 支持手动录入财务数据
- [ ] **DATA-04**: 支持模板导入财务数据
- [ ] **DATA-05**: 支持Excel导出财务数据
- [ ] **DATA-06**: 支持CSV导出财务数据
- [ ] **DATA-07**: 支持PDF报表导出
- [ ] **DATA-08**: 支持图片导出
- [ ] **DATA-09**: 支持本地备份
- [ ] **DATA-10**: 支持自动备份
- [ ] **DATA-11**: 支持手动备份
- [ ] **DATA-12**: 支持备份恢复
- [ ] **UI-01**: Material Design 3设计风格
- [ ] **UI-02**: 专业蓝色主题
- [ ] **UI-03**: 卡片式首页布局
- [ ] **UI-04**: 数字表格展示
- [ ] **UI-05**: 图表可视化展示
- [ ] **UI-06**: 卡片摘要展示
- [ ] **UI-07**: 引导式操作流程
- [ ] **PERF-01**: 启动时间不超过2秒
- [ ] **PERF-02**: 数据加载流畅
- [ ] **PERF-03**: 内存占用控制
- [ ] **PERF-04**: 部分离线支持

### Out of Scope

- **账户管理**：不需要管理银行账户、证券账户等，只关注数据分析和展示
- **iOS支持**：仅支持Android平台
- **自动数据同步**：暂不支持自动云端同步，采用文件导出/导入方式
- **实时市场数据**：暂不支持实时市场数据集成，后续迭代添加

## Key Decisions

| Decision | Rationale | Outcome |
|----------|-----------|---------|
| 仅Android平台 | 用户明确要求，减少开发复杂度 | — Pending |
| Kotlin + Jetpack Compose | 现代Android开发，声明式UI，开发效率高 | — Pending |
| MVVM架构 | Android官方推荐架构，状态管理清晰 | — Pending |
| Room数据库 | Android官方推荐本地数据库，简单易用 | — Pending |
| Material Design 3 | Google最新设计系统，现代化UI组件 | — Pending |
| MPAndroidChart | Android最流行的图表库，功能丰富 | — Pending |
| 文件导出/导入备份 | 用户明确要求，简单可靠 | — Pending |
| AI多模型支持 | 支持OpenAI、Claude、本地模型等，灵活性强 | — Pending |

## Evolution

This document evolves at phase transitions and milestone boundaries.

**After each phase transition** (via `/gsd-transition`):
1. Requirements invalidated? → Move to Out of Scope with reason
2. Requirements validated? → Move to Validated with phase reference
3. New requirements emerged? → Add to Active
4. Decisions to log? → Add to Key Decisions
5. "What This Is" still accurate? → Update if drifted

**After each milestone** (via `/gsd-complete-milestone`):
1. Full review of all sections
2. Core Value check — still the right priority?
3. Audit Out of Scope — reasons still valid?
4. Update Context with current state

---
*Last updated: 2026-06-26 after initialization*
