package com.imcys.foodchoice

import androidx.lifecycle.viewModelScope
import com.imcys.core.common.viewmodel.ComposeBaseViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivityViewModel : ComposeBaseViewModel<MainActivityState, MainActivityIntent>(
    MainActivityState(),
) {

    override fun handleEvent(event: MainActivityIntent, state: MainActivityState) {
        when (event) {
            is MainActivityIntent.SelectNavItem -> selectNavItem(event.index)
            is MainActivityIntent.SetShowBottomBar -> {
                viewStates.update { copy(isShowBottomBar = event.state) }
            }
        }
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
