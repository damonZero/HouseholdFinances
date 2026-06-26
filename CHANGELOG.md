# 更新日志

## [1.0.1] - 2026-06-26

### Bug 修复

#### 编译错误修复 (P0)
- 修复 `settings.gradle.kts` 中 `dependencyResolution` 应为 `dependencyResolutionManagement`
- 修复多个文件缺失 `ImageVector` 导入
- 修复 `HomeScreen.kt` 缺失 `clickable` 和 `Alignment` 导入
- 修复 7 个 ViewModel 中 `Flow.collect()` 在非 suspend 上下文调用的问题
- 修复 `HomeScreen.kt` 和 `AssetsScreen.kt` 中重复的 `getAssetTypeName` 函数
- 修复 Room TypeConverter 不支持 `Map<AssetType, Double>` 的问题
- 修复 `DataImportRepository` 中 `kotlinx.serialization` 无法序列化 `Any` 的问题

#### 运行时 Bug 修复 (P1)
- 修复 `DataExportRepository` 中 Flow collect 永远挂起的问题
- 修复 `HomeViewModel` 和 `ReportViewModel` 中顺序 collect 只处理第一个类型的问题
- 修复 `CashFlowViewModel` 中 Calendar 变异导致月份损坏的问题
- 修复 `LiabilitiesScreen` 中 FAB 无功能的问题
- 修复 `MainActivity` 中 Scaffold innerPadding 未应用的问题

#### 代码质量改进 (P2)
- 修复 `Charts.kt` 中 Double 与 Float 比较的问题
- 修复 `DonutChart` 中硬编码白色内圆不支持深色主题的问题
- 移除未使用的 MPAndroidChart 依赖

### 测试

#### 新增单元测试
- `FinancialCalculatorTest` - 16 个测试用例
- `CurrencyUtilsTest` - 14 个测试用例
- `DateUtilsTest` - 11 个测试用例
- `AssetTest` - 4 个测试用例
- `LiabilityTest` - 4 个测试用例
- `CashFlowTest` - 5 个测试用例
- `InvestmentTest` - 5 个测试用例
- `InsuranceTest` - 4 个测试用例
- `FinancialGoalTest` - 6 个测试用例

**总测试数**: 69 个

## [1.0.0] - 2026-06-26

### 新增功能
- 🏠 **资产管理**
  - 支持房产、现金、投资、保险等多种资产类型
  - 资产列表展示和筛选
  - 资产详情页
  - 添加/编辑/删除资产

- 💳 **负债管理**
  - 支持房贷、车贷、信用卡等多种负债类型
  - 负债列表展示和筛选
  - 还款信息计算（月供、利率、剩余期限）
  - 负债详情页

- 💰 **现金流管理**
  - 收入和支出记录
  - 月度收支统计
  - 月份切换浏览
  - 结余计算

- 📈 **投资跟踪**
  - 支持ETF、基金、股票等多种投资类型
  - 投资收益计算
  - 持仓价值统计
  - 投资组合概览

- 🛡️ **保险管理**
  - 保单信息管理
  - 保费和保额统计
  - 被保险人信息
  - 保险类型分类

- 🎯 **财务目标**
  - 储蓄、投资、债务偿还、退休规划等目标类型
  - 目标进度可视化
  - 目标状态管理（进行中、已完成、暂停、取消）

- 📊 **报表分析**
  - 资产负债表
  - 资产配置图（环形图）
  - 负债构成分析
  - 财务健康指标

- ⚙️ **设置功能**
  - 数据导出（CSV格式）
  - 数据导入
  - 备份恢复
  - AI配置
  - 个人偏好设置

- 🎨 **UI组件**
  - Material Design 3 设计规范
  - 深色/浅色主题支持
  - 响应式布局
  - 流畅动画效果

- 🔧 **技术特性**
  - Kotlin + Jetpack Compose
  - MVVM 架构
  - Room 数据库
  - Hilt 依赖注入
  - Navigation Compose 导航
  - Coroutines 异步处理

### 技术改进
- 完善的数据模型设计
- 类型安全的数据库操作
- 响应式数据流
- 统一的错误处理
- 代码模块化设计

### 文档
- 详细的 README 文档
- 项目结构说明
- 构建说明文档
- 需求文档

## 后续计划

### [1.1.0] - 计划中
- 📊 图表可视化增强
  - MPAndroidChart 集成
  - 趋势图表
  - 对比图表

- 🤖 AI 财务分析
  - 智能建议
  - 风险评估
  - 配置优化

- 📥 数据导入增强
  - Excel 文件导入
  - 飞书文档导入
  - 智能数据识别

- 📤 数据导出增强
  - PDF 报表
  - 图片导出
  - 自定义模板

### [1.2.0] - 计划中
- 🔄 数据同步
  - 本地网络同步
  - 云端备份

- 📱 多设备支持
  - 平板适配
  - 横屏模式

- 🔔 通知提醒
  - 账单提醒
  - 目标提醒
  - 定投提醒

### [2.0.0] - 远期规划
- 💹 实时市场数据
  - 股票行情
  - 基金净值
  - 汇率信息

- 🌐 多币种支持
  - 汇率转换
  - 多币种账户

- 👥 家庭成员
  - 多用户支持
  - 权限管理
  - 数据隔离

- 📚 财务知识库
  - 理财文章
  - 投资教程
  - 案例分析
