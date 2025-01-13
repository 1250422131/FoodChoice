package com.imcys.core.model.cook

import com.squareup.moshi.Json
import java.io.Serializable

data class CookFoodInfo(
    val code: Int = 0,
    val `data`: List<Data> = listOf(),
    val msg: String = "",
) : Serializable {
    data class Data(
        val bv: String = "",
        val difficulty: String = "",
        val methods: String = "",
        val name: String = "",
        val stuff: String = "",
        val tags: String = "",
        val tools: String = "",
    ) : Serializable
}
