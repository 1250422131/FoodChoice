package com.imcys.core.ui.base

import android.content.Context
import androidx.activity.ComponentActivity
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Composable
fun getWidthSizeClass(context: Context = LocalContext.current): WindowWidthSizeClass {
    return calculateWindowSizeClass(context as ComponentActivity).widthSizeClass
}
