package com.imcys.foodchoice.ui.setting

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController

@Composable
internal fun SettingRoute(
    modifier: Modifier = Modifier,
    viewModel: SettingViewModel = hiltViewModel(),
    navController: NavHostController,
) {
    SettingScreen(
        modifier = modifier,
        viewModel = viewModel,
        viewState = viewModel.viewStates,
        navController = navController,
    )
}

@Composable
fun SettingScreen(
    modifier: Modifier,
    viewModel: SettingViewModel,
    viewState: SettingState,
    navController: NavHostController,
) {
    Box {

    }
}
