package com.imcys.foodchoice.ui.home

import com.imcys.core.common.viewmodel.info.UiState
import com.imcys.core.model.HomeItemInfo
import com.imcys.feature.cook.navigation.cookRoute

data class HomeState(
    val homeItems: MutableList<HomeItemInfo> = mutableListOf(
        HomeItemInfo(
            "烹饪指南",
            "不知道做什么菜？来这里看看",
            "https://message.biliimg.com/bfs/im/b9d53ed962c7734c2803ac4a9c409994a4a491b9.jpg",
            route = cookRoute,
        ),
    ),
) : UiState
