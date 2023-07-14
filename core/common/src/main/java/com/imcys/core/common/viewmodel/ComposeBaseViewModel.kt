package com.imcys.core.common.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.imcys.core.common.viewmodel.info.IViewModelHandle
import com.imcys.core.common.viewmodel.info.UiIntent
import com.imcys.core.common.viewmodel.info.UiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch

abstract class ComposeBaseViewModel<S : UiState, I : UiIntent>(viewState: S) :
    IViewModelHandle<S, I>,
    ViewModel() {

    private val intentChannel = Channel<I>(Channel.UNLIMITED)

    var viewStates by mutableStateOf(viewState)
        protected set

    init {
        handleIntent()
    }

    private fun handleIntent() {
        viewModelScope.launch(Dispatchers.IO) {
            intentChannel.consumeAsFlow().collect {
                handleEvent(it, viewStates)
            }
        }
    }

    fun sendIntent(viewIntent: I) {
        viewModelScope.launch(Dispatchers.IO) {
            intentChannel.send(viewIntent)
        }
    }
}
