package com.household.finances.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.household.finances.ui.screens.*

@Composable
fun NavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        // Bottom Nav Screens
        composable(Screen.Home.route) {
            HomeScreen()
        }

        composable(Screen.Assets.route) {
            AssetsScreen(
                onAssetClick = { assetId ->
                    navController.navigate(Screen.AssetDetail.createRoute(assetId))
                }
            )
        }

        composable(Screen.Reports.route) {
            ReportsScreen()
        }

        composable(Screen.Settings.route) {
            SettingsScreen()
        }

        // 新增页面
        composable(Screen.Liabilities.route) {
            LiabilitiesScreen(
                onLiabilityClick = { liabilityId ->
                    navController.navigate(Screen.LiabilityDetail.createRoute(liabilityId))
                }
            )
        }

        composable(Screen.CashFlow.route) {
            CashFlowScreen()
        }

        composable(Screen.Investments.route) {
            InvestmentScreen()
        }

        composable(Screen.Insurance.route) {
            InsuranceScreen()
        }

        composable(Screen.Goals.route) {
            GoalsScreen()
        }

        // Detail Screens
        composable(
            route = Screen.AssetDetail.route,
            arguments = listOf(
                navArgument("assetId") { type = NavType.LongType }
            )
        ) { backStackEntry ->
            val assetId = backStackEntry.arguments?.getLong("assetId") ?: 0L
            AssetDetailScreen(
                assetId = assetId,
                onBack = { navController.popBackStack() }
            )
        }

        composable(
            route = Screen.LiabilityDetail.route,
            arguments = listOf(
                navArgument("liabilityId") { type = NavType.LongType }
            )
        ) { backStackEntry ->
            val liabilityId = backStackEntry.arguments?.getLong("liabilityId") ?: 0L
            LiabilityDetailScreen(
                liabilityId = liabilityId,
                onBack = { navController.popBackStack() }
            )
        }

        composable(Screen.AddAsset.route) {
            AddAssetScreen(
                onBack = { navController.popBackStack() }
            )
        }

        composable(Screen.AddLiability.route) {
            AddLiabilityScreen(
                onBack = { navController.popBackStack() }
            )
        }
    }
}
