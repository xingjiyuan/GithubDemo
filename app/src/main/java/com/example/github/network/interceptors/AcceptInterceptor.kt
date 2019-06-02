package com.example.github.network.interceptors

import okhttp3.Interceptor
import okhttp3.Response

class AcceptInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        return chain.proceed(original.newBuilder().apply {
            header("accept", "application/vnd.github.v3.full+json, ${original.header("accept") ?: ""}")
        }.build())
    }

}