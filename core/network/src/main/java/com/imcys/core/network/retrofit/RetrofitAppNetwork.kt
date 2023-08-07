package com.imcys.core.network.retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

object RetrofitAppNetwork {

    val networkApi: RetrofitAppApi = Retrofit.Builder()
        .baseUrl("https://api.misakamoe.com/app/cook/")
        .addConverterFactory(MoshiConverterFactory.create())
        .build()
        .create(RetrofitAppApi::class.java)

    val bilibiliApi: RetrofitBiliBiliApi = Retrofit.Builder()
        .baseUrl("https://api.bilibili.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(RetrofitBiliBiliApi::class.java)
}
