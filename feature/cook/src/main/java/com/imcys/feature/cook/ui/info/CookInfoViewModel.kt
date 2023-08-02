package com.imcys.feature.cook.ui.info

import androidx.lifecycle.viewModelScope
import com.imcys.core.common.viewmodel.ComposeBaseViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class CookInfoViewModel : ComposeBaseViewModel<CookInfoState, CookInfoIntent>(CookInfoState()) {
    override fun handleEvent(event: CookInfoIntent, state: CookInfoState) {
        when (event) {
            is CookInfoIntent.LoadFoodVideoInfo -> {
                loadFoodVideoInfo(event.bvId)
            }
        }
    }

    private fun loadFoodVideoInfo(bvId: String) {
    }

    init {
        // appbar动画
        viewModelScope.launch {
            delay(200L)
            viewStates.update { copy(isShowBottomBar = true) }
        }
    }
}
