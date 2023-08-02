package com.imcys.core.model.cook

import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * 烹饪材料类
 * @property code Int
 * @property `data` Data
 * @property msg String
 * @constructor
 */
data class CookingIngredientsInfo(
    @SerializedName("code")
    val code: Int, // 0
    @SerializedName("data")
    val `data`: Data,
    @SerializedName("msg")
    val msg: String, // 获取成功
) : Serializable {
    data class Data(
        @SerializedName("meat")
        val meat: List<CookingIngredient>,
        @SerializedName("staple")
        val staple: List<CookingIngredient>,
        @SerializedName("tools")
        val tools: List<CookingIngredient>,
        @SerializedName("vegetable")
        val vegetable: List<CookingIngredient>,
    ) : Serializable
}
