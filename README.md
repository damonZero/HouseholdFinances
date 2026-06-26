# 家庭财务分析 APP

一款专为家庭设计的财务分析应用，帮助您全方位管理家庭资产、负债、现金流和投资组合。

## 功能特性

### 🏠 资产管理
- 支持多种资产类型：房产、现金、投资、保险等
- 资产分类和标签管理
- 资产价值跟踪和历史记录
- 资产配置图可视化

### 💳 负债管理
- 支持多种负债类型：房贷、车贷、信用卡等
- 利率和月供计算
- 还款计划跟踪
- 总利息计算

### 💰 现金流管理
- 收入和支出记录
- 月度收支统计
- 分类管理
- 结余分析

### 📈 投资跟踪
- 支持多种投资类型：ETF、基金、股票等
- 投资收益计算
- 持仓价值跟踪
- 投资组合分析

### 🛡️ 保险管理
- 保单信息管理
- 保费和保额跟踪
- 保险配置分析
- 被保险人管理

### 🎯 财务目标
- 储蓄目标设定
- 投资目标跟踪
- 债务偿还计划
- 进度可视化

### 📊 净资产计算
- 自动计算净资产 = 总资产 - 总负债
- 净资产变化趋势展示
- 资产构成比例分析

### 📈 报表分析
- 资产负债表
- 资产配置图（饼图/环形图）
- 财务健康度评分
- 财务指标分析

### 📥 数据导入导出
- CSV格式导出资产、负债、现金流
- JSON格式全量数据导出
- 数据导入功能
- 备份恢复

## 技术架构

- **平台**：Android
- **语言**：Kotlin
- **UI框架**：Jetpack Compose
- **架构模式**：MVVM
- **数据库**：Room (SQLite)
- **依赖注入**：Hilt
- **图表库**：MPAndroidChart

## 项目结构

```
app/
├── data/
│   ├── local/
│   │   ├── dao/          # 数据访问对象
│   │   ├── FinancesDatabase.kt
│   │   └── Converters.kt
│   ├── model/            # 数据模型
│   └── repository/       # 仓库层
├── di/                   # 依赖注入
├── ui/
│   ├── components/       # 可复用组件
│   │   ├── Charts.kt     # 图表组件
│   │   ├── CommonComponents.kt
│   │   └── FinancialCard.kt
│   ├── navigation/       # 导航
│   ├── screens/          # 页面
│   │   ├── HomeScreen.kt
│   │   ├── AssetsScreen.kt
│   │   ├── LiabilitiesScreen.kt
│   │   ├── CashFlowScreen.kt
│   │   ├── InvestmentScreen.kt
│   │   ├── InsuranceScreen.kt
│   │   ├── GoalsScreen.kt
│   │   ├── ReportsScreen.kt
│   │   └── SettingsScreen.kt
│   ├── theme/            # 主题
│   └── viewmodel/        # 视图模型
├── util/                 # 工具类
│   ├── CurrencyUtils.kt
│   ├── DateUtils.kt
│   └── FinancialCalculator.kt
└── MainActivity.kt
```

## 开发环境

- Android Studio Hedgehog | 2023.1.1
- JDK 17
- Gradle 8.5
- Kotlin 1.9.22

## 快速开始

1. 克隆项目
```bash
git clone https://github.com/yourusername/household-finances.git
```

2. 使用 Android Studio 打开项目

3. 等待 Gradle 同步完成

4. 运行项目到模拟器或真机

## 主要页面

### 首页
- 净资产总览卡片
- 资产配置图（环形图）
- 快捷操作入口
- 财务健康度指标

### 资产页
- 资产列表（按类型分组）
- 添加/编辑/删除资产
- 资产筛选
- 资产详情页

### 负债页
- 负债列表（按类型分组）
- 添加/编辑/删除负债
- 还款信息展示
- 负债详情页

### 现金流页
- 月度收支统计
- 收支记录列表
- 月份切换
- 添加收支记录

### 投资页
- 投资组合概览
- 投资收益计算
- 持仓明细
- 添加投资记录

### 保险页
- 保险配置概览
- 保单列表
- 保费和保额统计
- 添加保险记录

### 目标页
- 财务目标列表
- 进度可视化
- 目标类型筛选
- 添加财务目标

### 报表页
- 资产负债表
- 资产构成分析
- 负债构成分析
- 财务健康指标

### 设置页
- 数据导出（CSV/JSON）
- 数据导入
- 备份恢复
- AI配置
- 个人偏好

## 数据模型

### 资产 (Asset)
- 房产、现金、投资、保险等类型
- 包含名称、价值、购入价格等字段

### 负债 (Liability)
- 房贷、车贷、信用卡等类型
- 包含金额、利率、月供等字段

### 现金流 (CashFlow)
- 收入和支出记录
- 分类和账户管理

### 投资 (Investment)
- ETF、基金、股票等
- 份额和价格跟踪

### 保险 (Insurance)
- 保单信息管理
- 保费和保额跟踪

## 贡献指南

1. Fork 项目
2. 创建功能分支 (`git checkout -b feature/AmazingFeature`)
3. 提交更改 (`git commit -m 'Add some AmazingFeature'`)
4. 推送到分支 (`git push origin feature/AmazingFeature`)
5. 创建 Pull Request

## 许可证

本项目采用 MIT 许可证 - 详见 [LICENSE](LICENSE) 文件

## 联系方式

- 项目链接：https://github.com/yourusername/household-finances
- 问题反馈：Issues

## 致谢

- [Jetpack Compose](https://developer.android.com/jetpack/compose)
- [Room Database](https://developer.android.com/training/data-storage/room)
- [Hilt](https://dagger.dev/hilt/)
- [MPAndroidChart](https://github.com/PhilJay/MPAndroidChart)
