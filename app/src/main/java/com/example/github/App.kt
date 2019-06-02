package com.example.github

import android.app.Application
import android.content.Context
import android.content.ContextWrapper
import android.support.multidex.MultiDex

private lateinit var INSTANCE: Application

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }
}

object AppContext : ContextWrapper(INSTANCE)