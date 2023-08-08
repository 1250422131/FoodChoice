package com.imcys.foodchoice.navigation

import androidx.compose.foundation.layout.Box
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
    startDestination: String = "app_index",
    modifier: Modifier,
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier,
    ) {
        composable("app_index") {
            Box {
            }
        }
        composable(homeRoute) {
            HomeRoute(modifier = Modifier, navController = navController)
        }
        composable(cookRoute) {
            CookRoute(modifier = Modifier, navController = navController)
        }
        composable(
            cookInfoRoute,
            arguments = listOf(
                navArgument("bvId") { type = NavType.StringType },
            ),
        ) {
            CookInfoRoute(
                it.arguments?.getString("bvId") ?: "",
                modifier = Modifier,
                navController = navController,
            )
        }
        composable(settingRoute) {
            SettingRoute(modifier = Modifier, navController = navController)
        }
    }
}
