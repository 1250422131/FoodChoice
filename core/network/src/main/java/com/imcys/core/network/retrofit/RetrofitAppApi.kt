package com.imcys.core.network.retrofit

import com.imcys.core.model.cook.CookFoodInfo
import com.imcys.core.model.cook.CookingIngredientsInfo
import retrofit2.http.GET

interface RetrofitAppApi {

    @GET("cookInfo.php")
    suspend fun getCookFoodData(): CookFoodInfo

    @GET("cookingIngredients.php")
    suspend fun getCookingIngredients(): CookingIngredientsInfo
}
