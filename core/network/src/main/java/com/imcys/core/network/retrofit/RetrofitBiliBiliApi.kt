package com.imcys.core.network.retrofit

import com.imcys.core.model.cook.CookFoodVideoInfo
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitBiliBiliApi {
    // 获取B站视频信息
    @GET("x/web-interface/view")
    suspend fun getVideoInfo(@Query("bvid") bvId: String): CookFoodVideoInfo
}
