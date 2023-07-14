package com.imcys.foodchoice

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material.icons.twotone.Home
import androidx.compose.material.icons.twotone.Settings
import com.imcys.core.common.viewmodel.info.UiState
import com.imcys.foodchoice.data.NavItem

data class MainActivityState(
    var navItemIndex: Int = 0,
    var navItems: MutableList<NavItem> = mutableListOf(
        NavItem("首页", Icons.Filled.Home, Icons.Outlined.Home),
        NavItem("设置", Icons.Filled.Settings, Icons.Outlined.Settings),
    ),
    var titleState: Boolean = true,
    var isShowBottomBar: Boolean = true,
) : UiState
