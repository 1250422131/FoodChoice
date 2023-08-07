package com.imcys.core.model.cook

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.io.Serializable

@JsonClass(generateAdapter = true)
data class CookFoodInfo(
    @Json(name = "code")
    val code: Int = 0,
    @Json(name = "data")
    val `data`: List<Data> = listOf(),
    @Json(name = "msg")
    val msg: String = "",
) : Serializable {
    @JsonClass(generateAdapter = true)
    data class Data(
        @Json(name = "bv")
        val bv: String = "",
        @Json(name = "difficulty")
        val difficulty: String = "",
        @Json(name = "methods")
        val methods: String = "",
        @Json(name = "name")
        val name: String = "",
        @Json(name = "stuff")
        val stuff: String = "",
        @Json(name = "tags")
        val tags: String = "",
        @Json(name = "tools")
        val tools: String = "",
    ) : Serializable
}
