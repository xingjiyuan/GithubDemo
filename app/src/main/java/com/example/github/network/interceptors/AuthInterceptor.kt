package com.example.github.network.interceptors

import android.util.Base64
import com.example.github.model.AccountManager
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        return chain.proceed(original.newBuilder().apply {
            when {
                original.url().pathSegments().contains("authorizations") -> {
                    val userCredentials = AccountManager.username + ":" + AccountManager.passwd
                    var auth = "Basic " + String(Base64.encode(userCredentials.toByteArray(), Base64.DEFAULT)).trim()
                    header("Authorization", auth)
                }
                AccountManager.isLoggedIn() -> {
                    val userCredentials = AccountManager.username + ":" + AccountManager.passwd
                    val auth = "Token " + AccountManager.token
                    header("Authorization", auth)
                }
                else -> removeHeader("Authorization")
            }
        }.build())
    }

}