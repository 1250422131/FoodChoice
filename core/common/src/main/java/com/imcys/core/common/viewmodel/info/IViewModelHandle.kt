package com.imcys.core.common.viewmodel.info

interface IViewModelHandle<S : UiState, I : UiIntent> {

    fun handleEvent(event: I, state: S)
}
