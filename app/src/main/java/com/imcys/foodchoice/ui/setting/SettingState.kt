package com.imcys.foodchoice.ui.setting

import com.imcys.core.common.viewmodel.info.UiState

data class SettingState(
    val privacyPolicyState: Int = -1, // -1未知 0拒绝 1接受
) : UiState
