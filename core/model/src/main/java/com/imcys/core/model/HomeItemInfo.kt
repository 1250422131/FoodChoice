package com.imcys.core.model

import androidx.compose.ui.Modifier

data class HomeItemInfo(
    val title: String,
    val content: String,
    val faceUrl: String,
    val route: String,
    val modifier: Modifier = Modifier,
)
