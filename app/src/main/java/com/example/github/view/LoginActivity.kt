package com.example.github.view

import android.os.Bundle
import com.example.github.R
import com.example.github.presenter.LoginPresenter
import com.example.mvp.impl.BaseActivity

class LoginActivity : BaseActivity<LoginPresenter>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
    }
}
