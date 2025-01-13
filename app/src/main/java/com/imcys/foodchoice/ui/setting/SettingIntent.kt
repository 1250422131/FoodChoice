package com.imcys.foodchoice.ui.setting

import com.imcys.core.common.viewmodel.info.UiIntent
import com.imcys.foodchoice.MainActivityIntent

sealed class SettingIntent : UiIntent{
    data class SetPrivacyPolicyState(val state: Int) : SettingIntent()
}
