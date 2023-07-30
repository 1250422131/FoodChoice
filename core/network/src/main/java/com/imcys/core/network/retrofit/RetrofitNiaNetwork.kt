package com.imcys.core.network.retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitNiaNetwork {

    val networkApi: RetrofitNiaNetworkApi = Retrofit.Builder()
        .baseUrl("https://api.misakamoe.com/app/cook/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(RetrofitNiaNetworkApi::class.java)
}
