package com.imcys.foodchoice.navigation

import androidx.navigation.NavController

const val homeRoute = "app_home"

fun NavController.navigateToHome() {
    this.navigate(
        route = homeRoute,
        builder = {
            popUpTo(homeRoute) { inclusive = true }
        },
    )
}
