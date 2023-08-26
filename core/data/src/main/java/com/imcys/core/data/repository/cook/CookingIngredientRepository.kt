package com.imcys.core.data.repository.cook

import com.imcys.core.common.data.BaseRepository
import com.imcys.core.data.extend.asCookingIngredientEntity
import com.imcys.core.data.extend.makeRequestInFlow
import com.imcys.core.database.dao.CookingIngredientDao
import com.imcys.core.database.entity.CookingIngredientEntity
import com.imcys.core.model.cook.CookingIngredient
import com.imcys.core.network.retrofit.RetrofitAppNetwork
import kotlinx.coroutines.flow.catch
import javax.inject.Inject

class CookingIngredientRepository @Inject constructor(
    private val cookingIngredientDao: CookingIngredientDao,
) : BaseRepository() {

    suspend fun getCookingIngredients(type: Int) =
        run { cookingIngredientDao.selectByTypeList(type) }

    suspend fun getCookingIngredients() = run { cookingIngredientDao.selectList() }

    suspend fun getCookingIngredient(name: String) = run { cookingIngredientDao.selectByName(name) }

    override suspend fun syncWithData(): Boolean {
        var isSuccess = true // 默认为 true，表示成功

        makeRequestInFlow {
            emit(RetrofitAppNetwork.networkApi.getCookingIngredients())
        }.catch {
            // 出现异常
            isSuccess = true
        }.collect {
            it.data.meat.let { meat -> updateCheck(meat, CookingIngredientEntity.MEAT) }
            it.data.staple.let { staple -> updateCheck(staple, CookingIngredientEntity.STAPLE) }
            it.data.vegetable.let { vegetable ->
                updateCheck(
                    vegetable,
                    CookingIngredientEntity.VEGETABLE,
                )
            }
            it.data.tools.let { tools -> updateCheck(tools, CookingIngredientEntity.TOOL) }
        }

        return isSuccess
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
