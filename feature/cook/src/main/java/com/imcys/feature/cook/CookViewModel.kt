package com.imcys.feature.cook

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.lifecycle.viewModelScope
import com.imcys.core.common.viewmodel.ComposeBaseViewModel
import com.imcys.core.data.repository.CookFoodInfoRepository
import com.imcys.core.data.repository.CookingIngredientRepository
import com.imcys.core.database.entity.CookFoodEntity
import com.imcys.feature.cook.menu.CookSearchType
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.Collections.addAll
import javax.inject.Inject

@HiltViewModel
class CookViewModel @Inject constructor(
    private val cookingIngredientRepository: CookingIngredientRepository,
    private val cookFoodInfoRepository: CookFoodInfoRepository,
    @ApplicationContext private val context: Context,
) :
    ComposeBaseViewModel<CookState, CookIntent>(CookState()) {

    init {
        // appbar动画
        viewModelScope.launch {
            delay(200L)
            viewStates = viewStates.copy(isShowBottomBar = true)
        }

        viewModelScope.launch {
            // 初始化数据
            // 加载食材
            initCookInfo()
            // 加载食物
            initFoodsInfo()
        }
    }

    private suspend fun initFoodsInfo() {
        // 无网络数据加载
        viewStates =
            viewStates.copy(foodsEntity = cookFoodInfoRepository.getCookingFoods())

        if (cookFoodInfoRepository.syncWithData()) {
            viewStates =
                viewStates.copy(foodsEntity = cookFoodInfoRepository.getCookingFoods())
        }
    }

    private suspend fun initCookInfo() {
        // 无网络数据加载
        viewStates =
            viewStates.copy(cookingIngredientsEntity = cookingIngredientRepository.getCookingIngredients())

        // 同步后更新加载
        if (cookingIngredientRepository.syncWithData()) {
            // 成功
            viewStates =
                viewStates.copy(cookingIngredientsEntity = cookingIngredientRepository.getCookingIngredients())
        }
    }

    override fun handleEvent(event: CookIntent, state: CookState) {
        when (event) {
            is CookIntent.SelectStuff -> selectStuff(event.stuff)
            is CookIntent.SelectTool -> selectTool(event.tool)
            is CookIntent.InputSearchKeyword -> searchFood(event.name)
            is CookIntent.ToBiliBili -> toBiliBili(event.bvId)
            is CookIntent.UpdateSearchType -> {
                viewStates = viewStates.copy(searchType = event.type)
                updateSearchResult()
            }
        }
    }

    @SuppressLint("QueryPermissionsNeeded", "IntentReset")
    private fun toBiliBili(bvId: String) {
        val intent = Intent(
            Intent.ACTION_VIEW,
            Uri.parse("https://www.bilibili.com/video/$bvId"),
        ).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        context.startActivity(intent)
    }

    private fun searchFood(name: String) {
        viewStates.update { copy(searchName = name) }
        updateSearchResult()
    }

    private fun selectTool(tool: String) {
        viewStates.update { copy(searchTool = tool) }
        updateSearchResult()
    }

    private fun selectStuff(stuff: String) {
        if (!viewStates.searchStuffs.remove(stuff)) {
            // 这里注意，由于viewStates没有产生新的对象，这样做并不会导致界面更新，但事实上viewStates已经发生了变化
            viewStates.searchStuffs.add(stuff)
        }
        // 因此，我们用一个取巧的办法更新
        viewStates.update { copy(updateUiState = !viewStates.updateUiState) }
        updateSearchResult()
    }

    // 下面逻辑有待优化
    private fun updateSearchResult() {
        val resultList = mutableListOf<CookFoodEntity>().apply { addAll(viewStates.foodsEntity) }
        val startIndex = viewStates.foodsEntity.size

        // 通过选择的食材进行过滤
        applySearchStuffsFilter(resultList)
        // 通过选择的厨具过滤
        applySearchToolFilter(resultList)
        // 通过搜索的内容
        applySearchNameFilter(resultList)

        clearResultListIfNoSearchConditions(resultList, startIndex)
        replaceIngredientsAndToolsIcons(resultList)

        viewStates.update { copy(searchResultList = resultList.toList().toMutableList()) }
    }

    /**
     * 过滤搜索的食材
     */
    private fun applySearchStuffsFilter(resultList: MutableList<CookFoodEntity>) {
        // 看看是否存在搜索内容
        if (viewStates.searchStuffs.isNotEmpty()) {
            // 对每一个元素都检查
            resultList.retainAll { food ->
                // 拿到这个食物需要的食材
                val foodStuffs = food.stuff.split("、")
                // 根据搜索形式进行不同的匹配
                if (viewStates.searchType == CookSearchType.FUZZY_MATCHING) {
                    foodStuffs.any { it in viewStates.searchStuffs }
                } else {
                    foodStuffs.all { it in viewStates.searchStuffs }
                }
            }
        }
    }

    /**
     * 根据用户选择的烹饪厨具进行过滤
     */
    private fun applySearchToolFilter(resultList: MutableList<CookFoodEntity>) {
        if (!viewStates.searchTool.isNullOrBlank()) {
            resultList.retainAll { it.tools.contains(viewStates.searchTool ?: "") }
        }
    }

    /**
     * 根据用户输入的名称进行模糊搜索
     */
    private fun applySearchNameFilter(resultList: MutableList<CookFoodEntity>) {
        if (!viewStates.searchName.isNullOrBlank()) {
            resultList.retainAll { it.name.contains(viewStates.searchName ?: "") }
        }
    }

    /**
     * 校验是否有搜索条件
     */
    private fun clearResultListIfNoSearchConditions(
        resultList: MutableList<CookFoodEntity>,
        startIndex: Int,
    ) {
        // 方式就是判断原resultList长度是否改变
        if (resultList.size == startIndex) {
            // 不改变相当于什么也没搜，我们全部清除掉
            resultList.clear()
        }
    }

    private fun replaceIngredientsAndToolsIcons(resultList: MutableList<CookFoodEntity>) {
        resultList.forEach { food ->
            if (food.emoji.isBlank()) {
                replaceIngredientsIcons(food)
            }
            if (food.image.isBlank()) {
                replaceToolsIcon(food)
            }
        }
    }

    private fun replaceIngredientsIcons(food: CookFoodEntity) {
        val emojis = food.stuff.split("、")
        val matchingIngredient = viewStates.cookingIngredientsEntity.find { it.name == emojis[0] }
        if (emojis.size > 3 && matchingIngredient != null) {
            food.emoji += matchingIngredient.emoji
        } else {
            emojis.forEach { stuff ->
                val matchingIngredient =
                    viewStates.cookingIngredientsEntity.find { it.name == stuff }
                if (matchingIngredient != null) {
                    food.emoji += matchingIngredient.emoji
                }
            }
        }
    }

    private fun replaceToolsIcon(food: CookFoodEntity) {
        val matchingCookingIngredient =
            viewStates.cookingIngredientsEntity.find { it.name == food.tools }
        if (viewStates.searchTool.isNullOrBlank() && matchingCookingIngredient != null) {
            food.image = matchingCookingIngredient.image ?: ""
        } else if (matchingCookingIngredient != null && matchingCookingIngredient.name == viewStates.searchTool) {
            food.image = matchingCookingIngredient.image ?: ""
        }
    }
}
