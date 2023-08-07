package com.imcys.foodchoice.navigation

import androidx.navigation.NavController

const val settingRoute = "app_setting"

fun NavController.navigateToSetting() {
    this.popBackStack()
    this.navigate(
        route = settingRoute,
        builder = {
            saveState()
            restoreState = true
        },
    )
}
