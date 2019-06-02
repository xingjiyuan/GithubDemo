package com.example.github.view

import android.os.Bundle
import android.view.View
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import com.example.common.otherwise
import com.example.common.yes
import com.example.github.R
import com.example.github.presenter.LoginPresenter
import com.example.github.utils.hideSoftInput
import com.example.mvp.impl.BaseActivity
import org.jetbrains.anko.toast

class LoginActivity : BaseActivity<LoginPresenter>() {

    private val signInButton by lazy { findViewById<Button>(R.id.signInButton) }
    private val username by lazy { findViewById<AutoCompleteTextView>(R.id.username) }
    private val password by lazy { findViewById<EditText>(R.id.password) }
    private val loginForm by lazy { findViewById<View>(R.id.loginForm) }
    private val loginProgress by lazy { findViewById<ProgressBar>(R.id.loginProgress) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        signInButton.setOnClickListener {
            presenter.checkUserName(username.text.toString())
                .yes {
                    presenter.checkPassword(password.text.toString()).yes {
                        hideSoftInput()
                        presenter.doLogin(username.text.toString(), password.text.toString())
                    }.otherwise {
                        showTips(password, "密码不合法")
                    }
                }
                .otherwise {
                    showTips(password, "用户名不合法")
                }
        }
    }

    private fun showTips(view: EditText, tips: String) {
        view.requestFocus()
        view.error = tips
    }

    fun onLoginStart() {
        loginProgress.visibility = View.VISIBLE
    }

    fun onLoginError(e: Throwable) {
        e.printStackTrace()
        toast("登录失败")
        loginProgress.visibility = View.GONE
    }

    fun onLoginSuccess() {
        toast("登录成功")
        loginProgress.visibility = View.GONE
    }

    fun onDataInit(name: String, passwd: String) {
        username.setText(name)
        password.setText(passwd)
    }
}
