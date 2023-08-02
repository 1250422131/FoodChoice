package com.imcys.feature.cook.ui.info

import com.imcys.core.common.viewmodel.info.UiIntent
import com.imcys.core.common.viewmodel.info.UiState
import com.imcys.core.database.entity.CookFoodEntity

data class CookInfoState(
    val isShowBottomBar: Boolean = false,
    val foodVideoBvId: String = "",
    val foodEntity: CookFoodEntity? = null,
) : UiState

open class CookInfoIntent : UiIntent {
    data class LoadFoodVideoInfo(val bvId: String) : CookInfoIntent()
}
