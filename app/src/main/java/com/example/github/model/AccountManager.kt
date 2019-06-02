package com.example.github.model

import com.example.github.utils.pref

object AccountManager {
    var username by pref("")
    var passwd by pref("")
    var token by pref("")

    fun isLoggedIn(): Boolean = TODO()
}