package com.example.mvp

import android.content.res.Configuration
import android.os.Bundle

interface ILifecycle {
    fun onCreate(saveInstansState: Bundle?)
    fun onSaveInstanceState(outState: Bundle)
    fun onViewStateRestored(savedInstanceState: Bundle?)
    fun onConfigurationChanged(newConfig: Configuration)
    fun onDestroy()
    fun onStart()
    fun onStop()
    fun onResume()
    fun onPause()
}