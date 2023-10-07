package com.imcys.foodchoice

import com.imcys.core.common.viewmodel.info.UiIntent
import javax.inject.Inject

open class MainActivityIntent @Inject constructor() : UiIntent {
    data class SelectNavItem(var index: Int) : MainActivityIntent()
    data class SetShowBottomBar(val state: Boolean) : MainActivityIntent()

    data class SetOpenHelpDialog(val state: Boolean) : MainActivityIntent()
}
