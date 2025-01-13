package com.imcys.core.model.cook

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

data class CookingIngredient(
    val emoji: String = "",
    val image: String? = null,
    val label: String? = null,
    val name: String = "",
    val alias: String? = null,
)
