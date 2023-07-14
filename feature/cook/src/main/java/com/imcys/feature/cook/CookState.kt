package com.imcys.feature.cook

import com.imcys.core.common.viewmodel.info.UiState
import com.imcys.core.database.entity.CookFoodEntity
import com.imcys.core.database.entity.CookingIngredientEntity
import com.imcys.feature.cook.menu.CookSearchType

// 踩坑，务必让这里的属性是val而不是var，否则因为出现竞态条件造成线程不安全
data class CookState constructor(
    val isShowBottomBar: Boolean = false,
    val cookingIngredientsEntity: MutableList<CookingIngredientEntity> = mutableListOf(),
    val searchStuffs: MutableList<String> = mutableListOf(),
    val searchResultList: MutableList<CookFoodEntity> = mutableListOf(),
    val searchTool: String? = null,
    val searchName: String? = null,
    val searchType: CookSearchType = CookSearchType.FUZZY_MATCHING,
    val foodsEntity: MutableList<CookFoodEntity> = mutableListOf(),

    val updateUiState: Boolean = false,
) : UiState
