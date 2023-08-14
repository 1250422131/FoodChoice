package com.imcys.foodchoice.ui.index

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.imcys.foodchoice.ui.home.HomeRoute
import com.imcys.foodchoice.ui.setting.SettingRoute

@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun IndexRoute(
    modifier: Modifier = Modifier,
    viewModel: IndexViewModel = hiltViewModel(),
    navController: NavHostController,
    pageState: PagerState,
) {
    IndexScreen(
        modifier = modifier,
        viewModel = viewModel,
        viewState = viewModel.viewStates,
        navController = navController,
        pageState = pageState,
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun IndexScreen(
    modifier: Modifier,
    viewModel: IndexViewModel,
    viewState: IndexState,
    navController: NavHostController,
    pageState: PagerState,
) {
    Column(modifier = modifier) {
        Spacer(modifier = Modifier.width(10.dp))

        HorizontalPager(
            userScrollEnabled = false,
            state = pageState,
            modifier = Modifier.fillMaxSize(),
        ) { pager ->
            when (pager) {
                0 -> {
                    HomeRoute(navController = navController)
                }

                1 -> {
                    SettingRoute(navController = navController)
                }
            }
        }
    }
}
