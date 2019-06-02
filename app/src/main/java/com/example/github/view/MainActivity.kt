package com.example.github.view

import android.content.Intent
import android.os.Bundle
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import com.bennyhuo.tieguanyin.annotations.ActivityBuilder
import com.example.common.no
import com.example.common.otherwise
import com.example.github.R
import com.example.github.entities.User
import com.example.github.model.account.AccountManager
import com.example.github.model.account.OnAccountStateChangeListener
import com.example.github.utils.doOnLayoutAvailable
import com.example.github.utils.loadWithGlide
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.nav_header_main.*
import org.jetbrains.anko.imageResource
import org.jetbrains.anko.toast

@ActivityBuilder(flags = [Intent.FLAG_ACTIVITY_CLEAR_TOP])
class MainActivity : AppCompatActivity(), OnAccountStateChangeListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toggle = ActionBarDrawerToggle(
            this,
            drawer_layout,
            toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawer_layout.setDrawerListener(toggle)
        toggle.syncState()

        initNavigationView()

        AccountManager.onAccountStateChangeListeners.add(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        AccountManager.onAccountStateChangeListeners.remove(this)
    }

    private fun initNavigationView() {
        AccountManager.currentUser?.let(::updateNavigationView) ?: clearNavigationView()
        initNavigationHeaderEvent()
    }

    private fun initNavigationHeaderEvent() {
        navigationView.doOnLayoutAvailable {
            navigationHeader.setOnClickListener {
                AccountManager.isLoggedIn().no {
                    startLoginActivity()
                }.otherwise {
                    AccountManager.logout()
                        .subscribe({
                            toast("注销成功")
                        }, {
                            it.printStackTrace()
                        })
                }
            }
        }
    }

    private fun updateNavigationView(user: User) {
        navigationView.doOnLayoutAvailable {
            usernameView.text = user.login
            emailView.text = user.email ?: ""
            avatarView.loadWithGlide(user.avatar_url, user.login.first())
        }
    }

    private fun clearNavigationView() {
        navigationView.doOnLayoutAvailable {
            usernameView.text = "请登录"
            emailView.text = ""
            avatarView.imageResource = R.drawable.ic_github
        }
    }

    override fun onLogin(user: User) {
        updateNavigationView(user)
    }

    override fun onLogout() {
        clearNavigationView()
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }
}
