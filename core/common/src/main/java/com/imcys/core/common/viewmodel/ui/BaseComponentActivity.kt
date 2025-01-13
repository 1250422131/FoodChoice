package com.imcys.core.common.viewmodel.ui

import android.os.Bundle
import androidx.activity.ComponentActivity

open class BaseComponentActivity<T> : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
}