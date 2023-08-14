package com.imcys.foodchoice.ui.index

import com.imcys.core.common.viewmodel.ComposeBaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class IndexViewModel @Inject constructor() :
    ComposeBaseViewModel<IndexState, IndexIntent>(IndexState()) {
    override fun handleEvent(event: IndexIntent, state: IndexState) {

    }

}
