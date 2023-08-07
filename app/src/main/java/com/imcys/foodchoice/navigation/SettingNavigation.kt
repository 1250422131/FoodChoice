package com.imcys.foodchoice.navigation

import androidx.navigation.NavController

const val settingRoute = "app_setting"

fun NavController.navigateToSetting() {
    this.navigate(
        route = settingRoute,
        builder = {
            restoreState = true
            saveState()
            popBackStack()
        },
    )
}
