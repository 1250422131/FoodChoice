package com.imcys.core.data.repository

import com.imcys.core.database.dao.CookingIngredientDao
import com.imcys.core.database.entity.CookingIngredientEntity
import com.imcys.core.model.CookingIngredient
import com.imcys.core.network.retrofit.RetrofitNiaNetwork
import javax.inject.Inject

class CookingIngredientRepository @Inject constructor(
    private val cookingIngredientDao: CookingIngredientDao,
) {

    suspend fun getCookingIngredients(type: Int) =
        run { cookingIngredientDao.selectByTypeList(type) }

    suspend fun getCookingIngredients() = run { cookingIngredientDao.selectList() }

    suspend fun getCookingIngredient(name: String) = run { cookingIngredientDao.selectByName(name) }

    suspend fun syncWith(): Boolean {
        val cookingIngredientResult = runCatching {
            RetrofitNiaNetwork.networkApi.getCookingIngredients()
        }

        // 成功的前提下进行
        if (cookingIngredientResult.isSuccess) {
            val cookingIngredientInfo = cookingIngredientResult.getOrNull()

            cookingIngredientInfo?.data?.meat?.let { updateCheck(it, CookingIngredientEntity.MEAT) }

            cookingIngredientInfo?.data?.staple?.let {
                updateCheck(
                    it,
                    CookingIngredientEntity.STAPLE,
                )
            }

            // STAPLE_FOOD
            cookingIngredientInfo?.data?.vegetable?.let {
                updateCheck(
                    it,
                    CookingIngredientEntity.VEGETABLE,
                )
            }
            cookingIngredientInfo?.data?.tools?.let {
                updateCheck(
                    it,
                    CookingIngredientEntity.TOOL,
                )
            }
        }

        return cookingIngredientResult.isSuccess
    }

    private suspend fun updateCheck(cookingIngredientList: List<CookingIngredient>, type: Int) {
        cookingIngredientList.forEach {
            cookingIngredientDao.selectByName(it.name)?.apply {
                name = it.name
                label = it.label
                emoji = it.emoji
                alias = it.alias
                image = it.image
                this.type = type
                cookingIngredientDao.update(this)
            } ?: apply {
                // 反之插入
                cookingIngredientDao.inserts(
                    CookingIngredientEntity(
                        name = it.name,
                        label = it.label,
                        emoji = it.emoji,
                        alias = it.alias,
                        image = it.image,
                        type = type,
                    ),
                )
            }
        }
    }
}
