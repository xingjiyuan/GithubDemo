package com.example.github.utils

import com.example.github.AppContext
import kotlin.reflect.jvm.jvmName


inline fun <reified R, T> R.pref(default: T) = com.example.common.Preferences(AppContext, "", default, R::class.jvmName)