package com.imcys.foodchoice.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.imcys.feature.cook.CookRoute
import com.imcys.feature.cook.navigation.cookInfoRoute
import com.imcys.feature.cook.navigation.cookRoute
import com.imcys.feature.cook.ui.info.CookInfoRoute
import com.imcys.foodchoice.ui.home.HomeRoute
import com.imcys.foodchoice.ui.setting.SettingRoute

/**
 * 路由控制器
 * @param navController NavHostController
 * @param modifier Modifier
 * @param startDestination String
 */
@Composable
fun FCNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    startDestination: String = "app_home",
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier,
    ) {
        composable(homeRoute) {
            HomeRoute(modifier = modifier, navController = navController)
        }
        composable(cookRoute) {
            CookRoute(modifier = modifier, navController = navController)
        }
        composable(
            cookInfoRoute,
            arguments = listOf(
                navArgument("bvId") { type = NavType.StringType },
            ),
        ) {
            CookInfoRoute(
                it.arguments?.getString("bvId") ?: "",
                modifier = modifier,
                navController = navController,
            )
        }
        composable(settingRoute) {
            SettingRoute(modifier = modifier, navController = navController)
        }
    }
}
