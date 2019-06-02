package com.example.github.presenter

import com.example.github.BuildConfig
import com.example.github.model.account.AccountManager
import com.example.github.view.LoginActivity
import com.example.mvp.impl.BasePresenter

class LoginPresenter : BasePresenter<LoginActivity>() {

    fun doLogin(name: String, password: String) {
        AccountManager.username = name
        AccountManager.passwd = password

        view.onLoginStart()
        AccountManager.login()
            .subscribe({ view.onLoginSuccess() }, { view.onLoginError(it) })
    }

    fun checkUserName(name: String): Boolean {
        return true
    }

    fun checkPassword(password: String): Boolean {
        return true
    }

    override fun onResume() {
        super.onResume()
        if (BuildConfig.DEBUG) {
            view.onDataInit(BuildConfig.testUserName, BuildConfig.testPassword)
        } else {
            view.onDataInit(AccountManager.username, AccountManager.passwd)
        }
    }
}
