package com.imcys.foodchoice.ui.setting

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import com.imcys.core.common.viewmodel.ComposeBaseViewModel
import com.imcys.foodchoice.FoodApplication
import com.microsoft.appcenter.AppCenter
import com.microsoft.appcenter.analytics.Analytics
import com.microsoft.appcenter.crashes.Crashes
import com.microsoft.appcenter.distribute.Distribute
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
) : ComposeBaseViewModel<SettingState, SettingIntent>(SettingState()) {
    private val shardedPreferences: SharedPreferences =
        context.getSharedPreferences("app_config", Context.MODE_PRIVATE)

    init {
        val privacyPolicyState = shardedPreferences.getInt("privacy_policy_state", -1)
        viewStates =
            viewStates.copy(privacyPolicyState = privacyPolicyState)
    }

    override fun handleEvent(event: SettingIntent, state: SettingState) {
        when(event){
            is SettingIntent.SetPrivacyPolicyState -> {
                setPrivacyPolicyState(event.state)
            }
        }
    }

    private fun setPrivacyPolicyState(state: Int) {
        viewStates = viewStates.copy(privacyPolicyState = state)
        shardedPreferences.edit {
            putInt("privacy_policy_state", state)
        }
       FoodApplication.initAppCenter(state)
    }
}
