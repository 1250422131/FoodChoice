package com.imcys.core.model.cook

import com.squareup.moshi.Json

data class CookingIngredient(
    @Json(name = "emoji")
    val emoji: String = "",
    @Json(name = "image")
    val image: String? = null,
    @Json(name = "label")
    val label: String? = null,
    @Json(name = "name")
    val name: String = "",
    @Json(name = "alias")
    val alias: String? = null,
)
