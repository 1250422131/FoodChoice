package com.imcys.core.model.cook

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class CookingIngredient(

    @SerializedName("emoji")
    val emoji: String,
    @SerializedName("name")
    val name: String,
    val image: String?,
    val alias: String?,
    val label: String?,

) : Serializable
