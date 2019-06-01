package com.example.common

import android.content.Context
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

class Preferences<T>(val context: Context, val name: String, val default: T, val prefName: String = "default") :
    ReadWriteProperty<Any?, T> {
    private val prefs by lazy {
        context.getSharedPreferences(prefName, Context.MODE_PRIVATE)
    }

    override fun getValue(thisRef: Any?, property: KProperty<*>): T {
        return findPreference(name)
    }

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        putPreference(name, value)
    }

    private fun findPreference(key: String): T {
        return when (default) {
            is Long -> prefs.getLong(key, default)
            is String -> prefs.getString(key, default)
            else -> throw  IllegalArgumentException("Unsupported type.")
        } as T
    }

    private fun putPreference(key: String, value: T) {
        with(prefs.edit()) {
            when (value) {
                is Long -> putLong(key, value)
                is String -> putString(key, value)
                else -> throw IllegalArgumentException("Unsupported type.")
            }
            apply()
        }
    }

}