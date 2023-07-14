package com.imcys.core.network.retrofit

import com.imcys.core.model.CookFoodInfo
import com.imcys.core.model.CookingIngredientsInfo
import retrofit2.http.GET

interface RetrofitNiaNetworkApi {

    @GET("cookInfo.php")
    suspend fun getCookFoodData(): CookFoodInfo

    @GET("cookingIngredients.php")
    suspend fun getCookingIngredients(): CookingIngredientsInfo
}
