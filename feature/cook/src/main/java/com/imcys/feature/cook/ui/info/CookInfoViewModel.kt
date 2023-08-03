package com.imcys.feature.cook.ui.info

import androidx.lifecycle.viewModelScope
import com.imcys.core.common.viewmodel.ComposeBaseViewModel
import com.imcys.core.data.repository.cook.CookFoodInfoRepository
import com.imcys.core.network.retrofit.RetrofitAppNetwork.bilibiliApi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class CookInfoViewModel @Inject constructor(
    private val cookFoodInfoRepository: CookFoodInfoRepository,
) : ComposeBaseViewModel<CookInfoState, CookInfoIntent>(CookInfoState()) {
    override fun handleEvent(event: CookInfoIntent, state: CookInfoState) {
        when (event) {
            is CookInfoIntent.LoadFoodVideoInfo -> {
                viewModelScope.launch { loadFoodVideoInfo(event.bvId) }
            }
        }
    }

    private suspend fun loadFoodVideoInfo(bvId: String) {
        // 获取数据
        bilibiliApi.getVideoInfo(bvId).apply {
            viewStates.update { copy(foodVideoInfo = this@apply) }
        }

        cookFoodInfoRepository.getCookingFood(bvId)?.apply {
        }
    }

    init {
        // appbar动画
        viewModelScope.launch {
            delay(200L)
            viewStates.update { copy(isShowBottomBar = true) }
        }
    }
}
