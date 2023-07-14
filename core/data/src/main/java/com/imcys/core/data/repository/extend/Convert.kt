package com.imcys.core.data.repository.extend

import com.imcys.core.database.entity.CookFoodEntity
import com.imcys.core.model.CookFoodInfo

fun CookFoodInfo.Data.asCookFoodEntity() =
    CookFoodEntity(
        bv = this.bv,
        difficulty = this.difficulty,
        methods = this.methods,
        name = this.name,
        stuff = this.stuff,
        tags = this.tags,
        tools = this.tools,
    )
