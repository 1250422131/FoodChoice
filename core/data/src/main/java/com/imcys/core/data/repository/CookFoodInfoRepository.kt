package com.imcys.core.data.repository

import com.imcys.core.common.data.BaseRepository
import com.imcys.core.data.repository.extend.asCookFoodEntity
import com.imcys.core.database.dao.CookFoodDao
import com.imcys.core.network.retrofit.RetrofitNiaNetwork
import javax.inject.Inject

class CookFoodInfoRepository @Inject constructor(
    private val cookFoodDao: CookFoodDao,
) : BaseRepository() {
    suspend fun getCookingFoods(stuff: String) =
        run {
            cookFoodDao.selectByStuffList(stuff)
        }

    suspend fun getCookingFoods() =
        run { cookFoodDao.selectList() }

    override suspend fun syncWithData(): Boolean {
        val cookingFoodDataResult = runCatching {
            RetrofitNiaNetwork.networkApi.getCookFoodData()
        }
        // 成功的前提下进行
        if (cookingFoodDataResult.isSuccess) {
            // 较为复杂的但写法清爽语法糖
            val cookingFoodData = cookingFoodDataResult.getOrNull()?.data
            cookingFoodData?.forEach { food ->
                cookFoodDao.selectByName(food.name)?.apply {
                    // 查询到了就更新
                    cookFoodDao.update(food.asCookFoodEntity().copy(id = id))
                } ?: apply {
                    // 没查到就插入
                    cookFoodDao.inserts(food.asCookFoodEntity())
                }
            }
        }

        return cookingFoodDataResult.isSuccess
    }
}
