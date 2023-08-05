package com.imcys.core.data.repository.cook

import com.imcys.core.common.data.BaseRepository
import com.imcys.core.data.extend.asCookingIngredientEntity
import com.imcys.core.database.dao.CookingIngredientDao
import com.imcys.core.database.entity.CookingIngredientEntity
import com.imcys.core.model.cook.CookingIngredient
import com.imcys.core.network.retrofit.RetrofitAppNetwork
import javax.inject.Inject

class CookingIngredientRepository @Inject constructor(
    private val cookingIngredientDao: CookingIngredientDao,
) : BaseRepository() {

    suspend fun getCookingIngredients(type: Int) =
        run { cookingIngredientDao.selectByTypeList(type) }

    suspend fun getCookingIngredients() = run { cookingIngredientDao.selectList() }

    suspend fun getCookingIngredient(name: String) = run { cookingIngredientDao.selectByName(name) }

    override suspend fun syncWithData(): Boolean {
        val cookingIngredientResult = runCatching {
            RetrofitAppNetwork.networkApi.getCookingIngredients()
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
                cookingIngredientDao.update(
                    it.asCookingIngredientEntity().copy(id = this.id, type = type),
                )
            } ?: apply {
                // 反之插入
                cookingIngredientDao.inserts(it.asCookingIngredientEntity().copy(type = type))
            }
        }
    }
}