package com.imcys.core.data.extend

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlin.experimental.ExperimentalTypeInference

@OptIn(ExperimentalTypeInference::class)
fun <T> makeRequestInFlow(@BuilderInference requestBlock: suspend FlowCollector<T>.() -> Unit): Flow<T> {
    // 切换流到IO线程，特别的，由于协程上下文传递，ViewModel处理意图本身就是在IO里，可以不可以这么做，观察后是否
    return flow(block = requestBlock).flowOn(Dispatchers.IO)
}
