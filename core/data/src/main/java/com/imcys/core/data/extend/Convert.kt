package com.imcys.core.data.extend

import com.imcys.core.database.entity.CookFoodEntity
import com.imcys.core.database.entity.CookingIngredientEntity
import com.imcys.core.model.cook.CookFoodInfo
import com.imcys.core.model.cook.CookingIngredient

fun CookFoodInfo.Data.asCookFoodEntity() =
    CookFoodEntity(
        bv = this.bv ?: "",
        description = this.description,
        difficulty = this.difficulty,
        methods = this.methods,
        name = this.name,
        stuff = this.stuff,
        tags = this.tags,
        tools = this.tools,
        url = this.url ?: "",
    )

fun CookingIngredient.asCookingIngredientEntity() =
    CookingIngredientEntity(
        name = this.name,
        label = this.label,
        emoji = this.emoji,
        alias = this.alias,
        image = this.image,
    )
