package com.imcys.core.data.repository.cook

import com.imcys.core.common.data.BaseRepository
import com.imcys.core.data.extend.asCookFoodEntity
import com.imcys.core.data.extend.makeRequestInFlow
import com.imcys.core.database.dao.CookFoodDao
import com.imcys.core.network.retrofit.RetrofitAppNetwork
import kotlinx.coroutines.flow.catch
import javax.inject.Inject

class CookFoodInfoRepository @Inject constructor(
    private val cookFoodDao: CookFoodDao,
) : BaseRepository() {
    suspend fun getCookingFood(bvId: String) =
        run {
            cookFoodDao.selectByBvId(bvId)
        }

    suspend fun getCookingFoods() =
        run { cookFoodDao.selectList() }

    override suspend fun syncWithData(): Boolean {
        var isSuccess = true

        makeRequestInFlow {
            emit(RetrofitAppNetwork.networkApi.getCookFoodData())
        }.catch {
            isSuccess = false
        }.collect {
            it.data.forEach { food ->
                cookFoodDao.selectByName(food.name)?.apply {
                    // 查询到了就更新
                    cookFoodDao.update(food.asCookFoodEntity().copy(id = id))
                } ?: apply {
                    // 没查到就插入
                    cookFoodDao.inserts(food.asCookFoodEntity())
                }
            }
        }
        return isSuccess
    }
}
