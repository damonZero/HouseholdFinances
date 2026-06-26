package com.household.finances.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(
    val route: String,
    val title: String,
    val icon: ImageVector
) {
    object Home : Screen(
        route = "home",
        title = "首页",
        icon = Icons.Default.Home
    )

    object Assets : Screen(
        route = "assets",
        title = "资产",
        icon = Icons.Default.AccountBalance
    )

    object Reports : Screen(
        route = "reports",
        title = "报表",
        icon = Icons.Default.Assessment
    )

    object Settings : Screen(
        route = "settings",
        title = "设置",
        icon = Icons.Default.Settings
    )

    // 负债页面
    object Liabilities : Screen(
        route = "liabilities",
        title = "负债",
        icon = Icons.Default.CreditCard
    )

    // 现金流页面
    object CashFlow : Screen(
        route = "cashflow",
        title = "现金流",
        icon = Icons.Default.Receipt
    )

    // 投资页面
    object Investments : Screen(
        route = "investments",
        title = "投资",
        icon = Icons.Default.TrendingUp
    )

    // 保险页面
    object Insurance : Screen(
        route = "insurance",
        title = "保险",
        icon = Icons.Default.Security
    )

    // 目标页面
    object Goals : Screen(
        route = "goals",
        title = "目标",
        icon = Icons.Default.Flag
    )

    // Detail screens
    object AssetDetail : Screen(
        route = "asset/{assetId}",
        title = "资产详情",
        icon = Icons.Default.Info
    ) {
        fun createRoute(assetId: Long) = "asset/$assetId"
    }

    object LiabilityDetail : Screen(
        route = "liability/{liabilityId}",
        title = "负债详情",
        icon = Icons.Default.Info
    ) {
        fun createRoute(liabilityId: Long) = "liability/$liabilityId"
    }

    object AddAsset : Screen(
        route = "add_asset",
        title = "添加资产",
        icon = Icons.Default.Add
    )

    object AddLiability : Screen(
        route = "add_liability",
        title = "添加负债",
        icon = Icons.Default.Add
    )

    companion object {
        val bottomNavItems = listOf(Home, Assets, Reports, Settings)
    }
}
