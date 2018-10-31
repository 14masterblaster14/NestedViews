package com.example.apirecyclerviewtimber

import okhttp3.Interceptor
import okhttp3.Response

/**
 */
class RequestInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        var originalRequest = chain.request()

        var url = originalRequest.url()
                .newBuilder()
                .addQueryParameter("api_key", ApiConstant.API_KEY)
                .build()

        originalRequest = originalRequest.newBuilder().url(url).build()
        return chain.proceed(originalRequest)

    }
}