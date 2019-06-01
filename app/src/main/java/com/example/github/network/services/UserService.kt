package com.example.github.network.services

import com.example.github.entities.User
import com.example.github.network.retrofit
import retrofit2.http.GET
import rx.Observable

interface UserApi {
    @GET("/user")
    fun getAuthenticatedUser(): Observable<User>
}

object UserService : UserApi by retrofit.create(UserApi::class.java)