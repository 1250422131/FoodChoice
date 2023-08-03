package com.imcys.feature.cook.navigation

import androidx.navigation.NavController

const val cookInfoRoute = "feature/cook/cookInfo/{bvId}"

fun NavController.navigateToCookInfoRoute(bvId: String) {
    this.navigate(
        route = "feature/cook/cookInfo/$bvId",
        builder = {
            popUpTo("feature/cook/cookInfo/$bvId") { inclusive = true }
        },
    )
}
