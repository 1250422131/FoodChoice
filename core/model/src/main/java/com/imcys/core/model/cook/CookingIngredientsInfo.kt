package com.imcys.core.model.cook

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * 烹饪材料类
 * @property code Int
 * @property `data` Data
 * @property msg String
 * @constructor
 */
data class CookingIngredientsInfo(
    val code: Int = 0,
    val `data`: Data = Data(),
    val msg: String = "",
) {
    data class Data(
        val meat: List<CookingIngredient> = listOf(),
        val staple: List<CookingIngredient> = listOf(),
        val tools: List<CookingIngredient> = listOf(),
        val vegetable: List<CookingIngredient> = listOf(),
    )
}
