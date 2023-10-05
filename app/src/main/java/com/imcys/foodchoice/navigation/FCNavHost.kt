package com.imcys.foodchoice.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.pager.PagerState
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
import com.imcys.foodchoice.ui.index.IndexRoute
import com.imcys.foodchoice.ui.setting.SettingRoute

/**
 * 路由控制器
 * @param navController NavHostController
 * @param modifier Modifier
 * @param startDestination String
 */
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun FCNavHost(
    navController: NavHostController,
    startDestination: String = "app_index",
    modifier: Modifier,
    pageState: PagerState,
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier,
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None },
        popEnterTransition = {
            EnterTransition.None
        },
        popExitTransition = {
            ExitTransition.None
        },
    ) {
        composable("app_index") {
            IndexRoute(modifier = Modifier, navController = navController, pageState = pageState)
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
