# 项目结构说明

## 目录结构

```
HouseholdFinances/
├── app/                                    # 应用模块
│   ├── build.gradle.kts                   # 应用构建配置
│   ├── proguard-rules.pro                 # ProGuard 规则
│   └── src/
│       └── main/
│           ├── AndroidManifest.xml        # 应用清单
│           ├── java/com/household/finances/
│           │   ├── FinancesApp.kt         # Application 类
│           │   ├── MainActivity.kt        # 主 Activity
│           │   ├── data/                  # 数据层
│           │   │   ├── local/             # 本地数据源
│           │   │   │   ├── dao/           # 数据访问对象
│           │   │   │   ├── Converters.kt  # 类型转换器
│           │   │   │   └── FinancesDatabase.kt
│           │   │   ├── model/             # 数据模型
│           │   │   └── repository/        # 仓库层
│           │   ├── di/                    # 依赖注入
│           │   └── ui/                    # UI 层
│           │       ├── components/        # 可复用组件
│           │       ├── navigation/        # 导航
│           │       ├── screens/           # 页面
│           │       ├── theme/             # 主题
│           │       └── viewmodel/         # 视图模型
│           └── res/                       # 资源文件
├── build.gradle.kts                       # 根项目构建配置
├── settings.gradle.kts                    # 项目设置
├── gradle.properties                      # Gradle 配置
├── gradle/                                # Gradle 包装器
├── README.md                              # 项目说明
└── REQUIREMENTS.md                        # 需求文档
```

## 核心模块说明

### 1. 数据层 (data/)

#### 本地数据源 (local/)
- **FinancesDatabase.kt**: Room 数据库定义
- **Converters.kt**: 类型转换器，处理枚举和列表类型
- **dao/**: 数据访问对象，定义数据库操作

#### 数据模型 (model/)
- **Asset.kt**: 资产模型（房产、现金、投资等）
- **Liability.kt**: 负债模型（房贷、车贷等）
- **CashFlow.kt**: 现金流模型（收入、支出）
- **Investment.kt**: 投资模型（ETF、基金等）
- **Insurance.kt**: 保险模型
- **FinancialGoal.kt**: 财务目标模型
- **AssetSnapshot.kt**: 资产快照模型

#### 仓库层 (repository/)
- 提供统一的数据访问接口
- 处理数据源的切换和缓存

### 2. UI 层 (ui/)

#### 页面 (screens/)
- **HomeScreen.kt**: 首页，展示净资产和资产配置
- **AssetsScreen.kt**: 资产列表页
- **ReportsScreen.kt**: 报表页
- **SettingsScreen.kt**: 设置页
- **AssetDetailScreen.kt**: 资产详情页
- **AddAssetScreen.kt**: 添加资产页
- **AddLiabilityScreen.kt**: 添加负债页

#### 视图模型 (viewmodel/)
- **HomeViewModel.kt**: 首页逻辑
- **AssetViewModel.kt**: 资产管理逻辑
- **LiabilityViewModel.kt**: 负债管理逻辑
- **ReportViewModel.kt**: 报表逻辑

#### 组件 (components/)
- **FinancialCard.kt**: 财务卡片组件
- **NetWorthCard.kt**: 净资产卡片

#### 导航 (navigation/)
- **Screen.kt**: 页面路由定义
- **NavGraph.kt**: 导航图

#### 主题 (theme/)
- **Color.kt**: 颜色定义
- **Theme.kt**: 主题配置
- **Type.kt**: 字体样式

### 3. 依赖注入 (di/)
- **AppModule.kt**: Hilt 模块，提供依赖注入

## 技术栈

| 技术 | 用途 |
|------|------|
| Kotlin | 开发语言 |
| Jetpack Compose | UI 框架 |
| Room | 本地数据库 |
| Hilt | 依赖注入 |
| Navigation Compose | 页面导航 |
| Lifecycle | 生命周期管理 |
| Coroutines | 异步处理 |
| MPAndroidChart | 图表展示 |

## 数据流

```
UI (Compose) → ViewModel → Repository → DAO → Room Database
```

## 构建和运行

1. 使用 Android Studio 打开项目
2. 等待 Gradle 同步完成
3. 选择模拟器或真机
4. 点击运行按钮

## 开发规范

- 遵循 MVVM 架构模式
- 使用 Kotlin 协程处理异步操作
- 使用 StateFlow 管理 UI 状态
- 遵循 Material Design 3 设计规范
