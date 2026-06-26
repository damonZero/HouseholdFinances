# 构建说明

## 环境要求

### 必需软件
- **Android Studio**: Hedgehog (2023.1.1) 或更高版本
- **JDK**: 17 或更高版本
- **Android SDK**: API 34 (Android 14)
- **Gradle**: 8.5 (项目自带)

### 可选软件
- **Git**: 用于版本控制
- **ADB**: 用于设备调试

## 构建步骤

### 1. 克隆项目
```bash
git clone <repository-url>
cd HouseholdFinances
```

### 2. 使用 Android Studio 打开项目
1. 启动 Android Studio
2. 选择 "Open an existing Android Studio project"
3. 浏览并选择 `HouseholdFinances` 目录
4. 点击 "OK"

### 3. 等待 Gradle 同步
- 首次打开项目时，Android Studio 会自动下载依赖
- 这可能需要几分钟时间
- 确保网络连接正常

### 4. 配置 Android SDK
1. 打开 SDK Manager (Tools -> SDK Manager)
2. 确保安装了以下组件：
   - Android SDK Platform 34
   - Android SDK Build-Tools 34.0.0
   - Android Emulator
   - Android SDK Platform-Tools

### 5. 创建模拟器（可选）
1. 打开 AVD Manager (Tools -> AVD Manager)
2. 点击 "Create Virtual Device"
3. 选择设备型号（推荐 Pixel 6）
4. 选择系统镜像（推荐 API 34）
5. 完成创建

### 6. 构建项目
1. 点击 Build -> Make Project (或按 Ctrl+F9)
2. 等待构建完成
3. 检查 Build 输出窗口是否有错误

### 7. 运行项目
1. 选择目标设备（模拟器或真机）
2. 点击 Run -> Run 'app' (或按 Shift+F10)
3. 等待安装和启动

## 常见问题

### Gradle 同步失败
**问题**: Gradle 同步超时或失败
**解决方案**:
1. 检查网络连接
2. 配置代理（如果需要）
3. 清除 Gradle 缓存：
   ```bash
   # Windows
   rd /s /q %USERPROFILE%\.gradle\caches
   
   # macOS/Linux
   rm -rf ~/.gradle/caches
   ```
4. 重新同步项目

### SDK 版本不匹配
**问题**: 编译错误提示 SDK 版本不匹配
**解决方案**:
1. 打开 SDK Manager
2. 安装所需版本的 SDK
3. 在 `build.gradle.kts` 中调整 `compileSdk` 和 `targetSdk`

### 模拟器启动失败
**问题**: 模拟器无法启动
**解决方案**:
1. 检查 BIOS 中是否启用了虚拟化（Intel VT-x 或 AMD-V）
2. 安装 Intel HAXM 或启用 Windows Hyper-V
3. 重新创建模拟器

### 依赖下载失败
**问题**: 依赖库下载失败
**解决方案**:
1. 检查网络连接
2. 配置 Maven 镜像（如阿里云镜像）：
   ```kotlin
   // settings.gradle.kts
   dependencyResolution {
       repositories {
           maven { url = uri("https://maven.aliyun.com/repository/public") }
           google()
           mavenCentral()
       }
   }
   ```

## 构建变体

### Debug 版本
- 包含调试信息
- 可以使用断点调试
- 不进行代码混淆
- 适合开发和测试

### Release 版本
- 优化后的代码
- 进行代码混淆（ProGuard）
- 签名后的 APK
- 适合发布

## 生成 APK

### Debug APK
1. Build -> Build Bundle(s) / APK(s) -> Build APK(s)
2. APK 位置: `app/build/outputs/apk/debug/app-debug.apk`

### Release APK
1. Build -> Generate Signed Bundle / APK
2. 选择 APK
3. 创建或选择密钥库
4. 选择 release 构建类型
5. 完成生成

## 代码签名

### 创建密钥库
```bash
keytool -genkey -v -keystore my-release-key.jks -keyalg RSA -keysize 2048 -validity 10000 -alias my-alias
```

### 配置签名
在 `app/build.gradle.kts` 中添加：
```kotlin
android {
    signingConfigs {
        create("release") {
            storeFile = file("my-release-key.jks")
            storePassword = "password"
            keyAlias = "my-alias"
            keyPassword = "password"
        }
    }
    buildTypes {
        release {
            signingConfig = signingConfigs.getByName("release")
        }
    }
}
```

## 测试

### 单元测试
```bash
./gradlew test
```

### 仪器化测试
```bash
./gradlew connectedAndroidTest
```

### 代码覆盖率
```bash
./gradlew jacocoTestReport
```

## 性能优化

### 启用 R8
在 `gradle.properties` 中添加：
```properties
android.enableR8=true
```

### 启用 Build Cache
在 `gradle.properties` 中添加：
```properties
org.gradle.caching=true
```

### 并行构建
在 `gradle.properties` 中添加：
```properties
org.gradle.parallel=true
```

## 发布到应用商店

### 准备工作
1. 更新 `versionCode` 和 `versionName`
2. 生成签名的 Release APK
3. 准备应用截图和描述
4. 测试所有功能

### Google Play
1. 创建 Google Play 开发者账户
2. 上传 APK 或 AAB
3. 填写应用信息
4. 提交审核

## 调试技巧

### 查看日志
```bash
adb logcat | grep "HouseholdFinances"
```

### 清除应用数据
```bash
adb shell pm clear com.household.finances
```

### 安装 APK
```bash
adb install app/build/outputs/apk/debug/app-debug.apk
```

### 截图
```bash
adb shell screencap -p /sdcard/screenshot.png
adb pull /sdcard/screenshot.png
```
