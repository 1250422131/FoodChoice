package com.imcys.core.network.interceptor

import okhttp3.Interceptor
import okhttp3.Response

class RFErrorHandlingInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        return chain.proceed(request)
    }
}
