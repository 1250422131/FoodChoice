package com.imcys.foodchoice

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import androidx.lifecycle.viewModelScope
import com.imcys.core.common.viewmodel.ComposeBaseViewModel
import com.microsoft.appcenter.AppCenter
import com.microsoft.appcenter.analytics.Analytics
import com.microsoft.appcenter.crashes.Crashes
import com.microsoft.appcenter.distribute.Distribute
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
) : ComposeBaseViewModel<MainActivityState, MainActivityIntent>(
    MainActivityState(),
) {
    private val shardedPreferences: SharedPreferences =
        context.getSharedPreferences("app_config", Context.MODE_PRIVATE)

    init {
        val privacyPolicyState = shardedPreferences.getInt("privacy_policy_state", -1)
        viewStates =
            viewStates.copy(privacyPolicyState = privacyPolicyState)
    }

    override fun handleEvent(event: MainActivityIntent, state: MainActivityState) {
        when (event) {
            is MainActivityIntent.SelectNavItem -> selectNavItem(event.index)
            is MainActivityIntent.SetShowBottomBar -> {
                viewStates.update { copy(isShowBottomBar = event.state) }
            }

            is MainActivityIntent.SetPrivacyPolicyState -> {
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

    private fun selectNavItem(index: Int) {
        viewStates = viewStates.copy(titleState = false)
        viewModelScope.launch {
            delay(250L)
            viewStates.update {
                copy(titleState = true)
            }
        }
        viewStates.update {
            copy(navItemIndex = index)
        }
    }
}
