package com.imcys.feature.cook.ui.info

import com.imcys.core.common.viewmodel.info.UiIntent
import com.imcys.core.common.viewmodel.info.UiState
import com.imcys.core.database.entity.CookFoodEntity
import com.imcys.core.model.cook.CookFoodVideoInfo

data class CookInfoState(
    val isShowBottomBar: Boolean = false,
    val foodVideoBvId: String = "",
    val foodEntity: CookFoodEntity? = null,
    val foodVideoInfo: CookFoodVideoInfo? = null,
) : UiState

sealed class CookInfoIntent : UiIntent {
    data class LoadFoodVideoInfo(val bvId: String) : CookInfoIntent()
    data class ToBiliBiliPlay(val bvId: String) : CookInfoIntent()
}
