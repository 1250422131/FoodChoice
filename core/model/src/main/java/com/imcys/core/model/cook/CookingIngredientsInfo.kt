package com.imcys.core.model.cook

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.io.Serializable

/**
 * 烹饪材料类
 * @property code Int
 * @property `data` Data
 * @property msg String
 * @constructor
 */
@JsonClass(generateAdapter = true)
data class CookingIngredientsInfo(
    @Json(name = "code")
    val code: Int = 0,
    @Json(name = "data")
    val `data`: Data = Data(),
    @Json(name = "msg")
    val msg: String = "",
) : Serializable {
    @JsonClass(generateAdapter = true)
    data class Data(
        @Json(name = "meat")
        val meat: List<CookingIngredient> = listOf(),
        @Json(name = "staple")
        val staple: List<CookingIngredient> = listOf(),
        @Json(name = "tools")
        val tools: List<CookingIngredient> = listOf(),
        @Json(name = "vegetable")
        val vegetable: List<CookingIngredient> = listOf(),
    ): Serializable
}
