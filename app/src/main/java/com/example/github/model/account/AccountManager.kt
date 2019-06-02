package com.example.github.model.account

import com.example.github.entities.AuthorizationReq
import com.example.github.entities.AuthorizationRsp
import com.example.github.entities.User
import com.example.github.network.services.AuthService
import com.example.github.network.services.UserService
import com.example.github.utils.fromJson
import com.example.github.utils.pref
import com.google.gson.Gson
import retrofit2.HttpException
import rx.Observable
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

interface OnAccountStateChangeListener {
    fun onLogin(user: User)

    fun onLogout()
}

object AccountManager {
    var username by pref("")
    var passwd by pref("")
    var token by pref("")
    var authId by pref(-1)

    private var userJson by pref("")

    val onAccountStateChangeListeners = ArrayList<OnAccountStateChangeListener>()

    var currentUser: User? = null
        get() {
            if (field == null && userJson.isNotEmpty()) {
                field = Gson().fromJson<User>(userJson)
            }
            return field
        }
        set(value) {
            if (value == null) {
                userJson = ""
            } else {
                userJson = Gson().toJson(value)
            }
            field = value
        }

    fun isLoggedIn(): Boolean = token.isNotEmpty()

    fun notifyLogin(user: User) {
        onAccountStateChangeListeners.forEach { it.onLogin(user) }
    }

    fun notifyLogout() {
        onAccountStateChangeListeners.forEach { it.onLogout() }
    }

    fun login() {
        AuthService.createAuthorization(AuthorizationReq())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .doOnNext {
                if (it.token.isEmpty()) throw AccountException(it)
            }
            .retryWhen {
                it.flatMap {
                    if (it is AccountException) {
                        AuthService.deleteAuthorization(it.authorizationRsp.id)
                    } else {
                        Observable.error(it)
                    }
                }
            }.flatMap {
                token = it.token
                authId = it.id
                UserService.getAuthenticatedUser()
            }.map {
                currentUser = it
                notifyLogin(it)
            }
    }

    fun logout() = AuthService.deleteAuthorization(authId)
        .observeOn(AndroidSchedulers.mainThread())
        .subscribeOn(Schedulers.io())
        .doOnNext {
            if (it.isSuccessful) {
                authId = -1
                token = ""
                currentUser = null
                notifyLogout()
            } else {
                throw HttpException(it)
            }
        }

    class AccountException(val authorizationRsp: AuthorizationRsp) : Exception("Already log in.")
}