package com.imcys.core.data.repository

import com.imcys.core.data.repository.extend.asCookFoodEntity
import com.imcys.core.database.dao.CookFoodDao
import com.imcys.core.network.retrofit.RetrofitNiaNetwork
import javax.inject.Inject

class CookFoodInfoRepository @Inject constructor(
    private val cookFoodDao: CookFoodDao,
) {
    suspend fun getCookingFoods(stuff: String) =
        run { cookFoodDao.selectByStuffList(stuff) }

    suspend fun getCookingFoods() =
        run { cookFoodDao.selectList() }

    suspend fun syncWith(): Boolean {
        val cookingFoodInfoResult = runCatching {
            RetrofitNiaNetwork.networkApi.getCookFoodData()
        }
        // 成功的前提下进行
        if (cookingFoodInfoResult.isSuccess) {
            // 较为复杂的但写法清爽语法糖
            cookingFoodInfoResult.getOrNull()?.data?.forEach {
                cookFoodDao.selectByNameList(it.name)?.apply {
                    cookFoodDao.update(it.asCookFoodEntity().copy(id = id))
                } ?: apply {
                    cookFoodDao.inserts(it.asCookFoodEntity())
                }
            }
        }

        return cookingFoodInfoResult.isSuccess
    }
}
