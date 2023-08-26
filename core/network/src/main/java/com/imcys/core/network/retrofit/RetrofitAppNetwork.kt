package com.imcys.core.network.retrofit

import com.imcys.core.network.interceptor.RFErrorHandlingInterceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

object RetrofitAppNetwork {

    private val client = OkHttpClient.Builder()
        .addInterceptor(RFErrorHandlingInterceptor())
        .build()

    val networkApi: RetrofitAppApi = Retrofit.Builder()
        .client(client)
        .baseUrl("https://api.misakamoe.com/app/cook/")
        .addConverterFactory(MoshiConverterFactory.create())
        .build()
        .create(RetrofitAppApi::class.java)

    val bilibiliApi: RetrofitBiliBiliApi = Retrofit.Builder()
        .client(client)
        .baseUrl("https://api.bilibili.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(RetrofitBiliBiliApi::class.java)
}
