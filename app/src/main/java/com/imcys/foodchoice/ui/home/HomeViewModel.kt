package com.imcys.foodchoice.ui.home

import com.imcys.core.common.viewmodel.ComposeBaseViewModel
import javax.inject.Inject

class HomeViewModel @Inject constructor() :
    ComposeBaseViewModel<HomeState, HomeIntent>(HomeState()) {

    override fun handleEvent(event: HomeIntent, state: HomeState) {
    }
}
