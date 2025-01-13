package com.imcys.core.designsystem.theme

import androidx.annotation.FloatRange
import androidx.compose.ui.graphics.Color
import androidx.core.graphics.ColorUtils

val Purple80 = Color(0xFFD0BCFF)
val PurpleGrey80 = Color(0xFFCCC2DC)
val Pink80 = Color(0xFFEFB8C8)

val Purple40 = Color(0xFF6650a4)
val PurpleGrey40 = Color(0xFF625b71)
val Pink40 = Color(0xFF7D5260)

fun Int.blend(
    color: Int,
    @FloatRange(from = 0.0, to = 1.0) fraction: Float = 0.5f,
): Int = ColorUtils.blendARGB(this, color, fraction)
