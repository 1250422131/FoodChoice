package com.imcys.foodchoice.ui.setting

import com.imcys.core.common.viewmodel.ComposeBaseViewModel

class SettingViewModel : ComposeBaseViewModel<SettingState, SettingIntent>(SettingState()) {
    override fun handleEvent(event: SettingIntent, state: SettingState) {
    }
}
