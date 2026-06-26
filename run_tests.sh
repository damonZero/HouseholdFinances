#!/bin/bash

# 家庭财务APP - 测试运行脚本
# 使用方法: bash run_tests.sh

echo "=========================================="
echo "  家庭财务APP - 单元测试"
echo "=========================================="
echo ""

# 检查 Android SDK
if [ -z "$ANDROID_HOME" ]; then
    echo "警告: ANDROID_HOME 环境变量未设置"
    echo "请确保 Android SDK 已安装并配置"
fi

# 检查 Java
if ! command -v java &> /dev/null; then
    echo "错误: 未找到 Java，请安装 JDK 17+"
    exit 1
fi

echo "Java 版本:"
java -version 2>&1 | head -1
echo ""

# 运行单元测试
echo "运行单元测试..."
echo ""

# 使用 Gradle Wrapper 运行测试
if [ -f "gradlew" ]; then
    ./gradlew test --console=plain
else
    echo "Gradle Wrapper 未找到，请先生成:"
    echo "  gradle wrapper --gradle-version 8.5"
    echo ""
    echo "或者使用 Android Studio 运行测试"
fi

echo ""
echo "=========================================="
echo "  测试完成"
echo "=========================================="
