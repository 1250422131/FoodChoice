package com.imcys.core.model.cook

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class CookFoodInfo(
    @SerializedName("code")
    val code: Int, // 0
    @SerializedName("data")
    val `data`: List<Data>,
    @SerializedName("msg")
    val msg: String, // 获取成功
) : Serializable {
    data class Data(
        @SerializedName("bv")
        val bv: String, // BV1NE411Q7Jj
        @SerializedName("difficulty")
        val difficulty: String, // 简单
        @SerializedName("methods")
        val methods: String, // 煲
        @SerializedName("name")
        val name: String, // 电饭煲版广式腊肠煲饭
        @SerializedName("stuff")
        val stuff: String, // 腊肠、米
        @SerializedName("tags")
        val tags: String, // 广式
        @SerializedName("tools")
        val tools: String, // 电饭煲
    ) : Serializable
}
