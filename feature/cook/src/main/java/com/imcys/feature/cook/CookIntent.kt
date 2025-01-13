package com.imcys.feature.cook

import com.imcys.core.common.viewmodel.info.UiIntent
import com.imcys.core.database.entity.CookFoodEntity
import com.imcys.core.database.entity.CookingIngredientEntity
import com.imcys.feature.cook.menu.CookSearchType

sealed class CookIntent : UiIntent {
    data class SelectStuff(val stuff: String) : CookIntent()
    data class SelectTool(val tool: String) : CookIntent()
    data class InputSearchKeyword(val name: String) : CookIntent()
    data class ToBiliBili(val bvId: String) : CookIntent()
    data class UpdateSearchType(val type: CookSearchType) : CookIntent()
    data class PostOpenFoodInfo(val cookFoodEntity: CookFoodEntity) : CookIntent()
    data class PostSelectCookingIngredient(val cookingIngredientEntity: CookingIngredientEntity) : CookIntent()
}
