package com.imcys.core.common.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.imcys.core.common.viewmodel.info.IViewModelHandle
import com.imcys.core.common.viewmodel.info.UiIntent
import com.imcys.core.common.viewmodel.info.UiState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch

abstract class ComposeBaseViewModel<S : UiState, I : UiIntent>(viewState: S) :
    IViewModelHandle<S, I>,
    ViewModel() {

    private val intentChannel = Channel<I>(Channel.UNLIMITED)

    /**
     * 界面状态初始化，这里没用flow，mutableState看上去已经帮我们完成了既定目的
     * 你可以考虑在这里切换为MutableStateFlow，但感觉差不多
     */
    var viewStates by mutableStateOf(viewState)
        protected set

    init {
        handleIntent()
    }

    private fun handleIntent() {
        launchIO {
            intentChannel.consumeAsFlow().filterNotNull().collect {
                handleEvent(it, viewStates)
            }
        }
    }

    /**
     * 更新意图
     */
    fun S.update(content: S.() -> S) {
        viewStates = content.invoke(this@update)
    }

    /**
     * 发送意图
     */
    fun sendIntent(viewIntent: I) {
        launchIO {
            intentChannel.send(viewIntent)
        }
    }

    protected fun launchIO(
        start: CoroutineStart = CoroutineStart.DEFAULT,
        block: suspend CoroutineScope.() -> Unit,
    ) {
        viewModelScope.launch(Dispatchers.IO, start, block)
    }

    protected fun launchUI(
        start: CoroutineStart = CoroutineStart.DEFAULT,
        block: suspend CoroutineScope.() -> Unit,
    ) {
        viewModelScope.launch(Dispatchers.Main, start, block)
    }

}
