package com.imcys.feature.cook.navigation

import androidx.navigation.NavController

const val cookInfoRoute = "feature/cook/cookInfo/{bvId}"

fun NavController.navigateToCookInfoRoute(bvId: String) {
    this.navigate("feature/cook/cookInfo/$bvId")
}
