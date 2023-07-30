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

    /**
     * 界面状态初始化，这里没用flow，mutableState看上去已经帮我们完成了既定目的
     * 你可以考虑在这里切换为MutableStateFlow，但感觉差不多，MutableStateFlow无非是可以有多个订阅者观察，当然你的布局假如是需要
     */
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

    /**
     * 更新意图
     */
    fun S.update(content: S.() -> S) {
        viewStates = content.invoke(this)
    }

    /**
     * 发送意图
     */
    fun sendIntent(viewIntent: I) {
        viewModelScope.launch(Dispatchers.IO) {
            intentChannel.send(viewIntent)
        }
    }
}
