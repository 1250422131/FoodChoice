package com.imcys.feature.cook

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.lifecycle.viewModelScope
import com.imcys.core.common.viewmodel.ComposeBaseViewModel
import com.imcys.core.data.repository.CookFoodInfoRepository
import com.imcys.core.data.repository.CookingIngredientRepository
import com.imcys.feature.cook.menu.CookSearchType
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
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

        if (cookFoodInfoRepository.syncWith()) {
            viewStates =
                viewStates.copy(foodsEntity = cookFoodInfoRepository.getCookingFoods())
        }
    }

    private suspend fun initCookInfo() {
        // 无网络数据加载
        viewStates =
            viewStates.copy(cookingIngredientsEntity = cookingIngredientRepository.getCookingIngredients())

        // 同步后更新加载
        if (cookingIngredientRepository.syncWith()) {
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
        viewStates = viewStates.copy(searchName = name)
        updateSearchResult()
    }

    private fun selectTool(tool: String) {
        viewStates = viewStates.copy(searchTool = tool)
        updateSearchResult()
    }

    private fun selectStuff(stuff: String) {
        if (!viewStates.searchStuffs.remove(stuff)) {
            // 这里注意，由于viewStates没有产生新的对象，这样做并不会导致界面更新，但事实上viewStates已经发生了变化
            viewStates.searchStuffs.add(stuff)
        }
        // 因此，我们用一个取巧的办法更新
        viewStates = viewStates.copy(updateUiState = !viewStates.updateUiState)
        updateSearchResult()
    }

    // 下面逻辑有待优化
    private fun updateSearchResult() {
        var resultList = (viewStates.foodsEntity + mutableListOf()).toMutableList()
        val starIndex = viewStates.foodsEntity.size

        // 模糊匹配
        if (viewStates.searchStuffs.isNotEmpty()) {
            resultList =
                // 对所有食物进行检查
                resultList.filter {
                    // 假如这个食品所需的食材有任意一个在搜索的条件内就可以
                    if (viewStates.searchType == CookSearchType.FUZZY_MATCHING) {
                        it.stuff.split("、").any { mIt -> mIt in viewStates.searchStuffs }
                    } else {
                        it.stuff.split("、").all { mIt -> mIt in viewStates.searchStuffs }
                    }
                }.toMutableList()
        }

        // 检查食材需要的厨具
        if (!viewStates.searchTool.isNullOrBlank()) {
            resultList =
                resultList.filter { it.tools.contains(viewStates.searchTool.toString()) }
                    .toMutableList()
        }

        // 检查食材需要的厨具
        if (!viewStates.searchName.isNullOrBlank()) {
            resultList =
                resultList.filter { it.name.contains(viewStates.searchName.toString()) }
                    .toMutableList()
        }

        // 这里假如没有任何搜索条件，就直接清空数据，相当于没有搜索
        if (resultList.size == starIndex) resultList.clear()

        // 即使是搜索结束也不行，因为我们得替换出食材和厨具的图标，因此这里有需要检查
        resultList.forEach {
            // 确保这个食物还没有被替换过图标
            if (it.emoji.isBlank()) {
                // 事实上是返回的数据中，食材：土豆、番茄、茄子，这样的数据就导致我必须要切割一下
                val emojis = it.stuff.split("、")
                // 这里给run给了个标签，待会停止foreach需要用到
                run loop@{
                    // 大于3个图标就展示1个
                    if (emojis.size > 3) {
                        viewStates.cookingIngredientsEntity.forEach { mIt ->
                            if (mIt.name == emojis[0]) {
                                it.emoji += mIt.emoji
                                return@loop
                            }
                        }
                    } else {
                        // 反之就都展示
                        emojis.forEach { stuff ->
                            run loop@{
                                viewStates.cookingIngredientsEntity.forEach { mIt ->

                                    if (mIt.name == stuff) {
                                        it.emoji += mIt.emoji
                                        return@loop
                                    }
                                }
                            }
                        }
                    }
                }
            }
            // 这块也是一样，只不过是看看厨具信息
            if (it.image.isBlank()) {
                run loop@{
                    viewStates.cookingIngredientsEntity.forEach { cooking ->
                        // 没搜索厨具
                        if (viewStates.searchTool.isNullOrBlank() && it.tools == cooking.name) {
                            it.image = cooking.image ?: ""
                            return@loop
                        } else if (cooking.name == viewStates.searchTool) {
                            it.image = cooking.image ?: ""
                            return@loop
                        }
                    }
                }
            }
        }

        viewStates = viewStates.copy(searchResultList = resultList)
    }
}
