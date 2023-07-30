package com.imcys.foodchoice

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Settings
import com.imcys.core.common.viewmodel.info.UiState
import com.imcys.foodchoice.data.NavItem

data class MainActivityState(
    val navItemIndex: Int = 0,
    val navItems: MutableList<NavItem> = mutableListOf(
        NavItem("首页", Icons.Filled.Home, Icons.Outlined.Home),
        NavItem("设置", Icons.Filled.Settings, Icons.Outlined.Settings),
    ),
    val titleState: Boolean = true,
    val isShowBottomBar: Boolean = true,
) : UiState
