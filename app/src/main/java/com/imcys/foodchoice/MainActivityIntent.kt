package com.imcys.foodchoice

import com.imcys.core.common.viewmodel.info.UiIntent
import javax.inject.Inject

sealed class MainActivityIntent : UiIntent {
    data class SelectNavItem(var index: Int) : MainActivityIntent()
    data class SetShowBottomBar(val state: Boolean) : MainActivityIntent()
    data class SetPrivacyPolicyState(val state: Int) : MainActivityIntent()
}
