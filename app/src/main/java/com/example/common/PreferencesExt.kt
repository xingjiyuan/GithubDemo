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
        return findPreference(finPropertyName(property))
    }

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        putPreference(finPropertyName(property), value)
    }

    private fun finPropertyName(property: KProperty<*>) = if (name.isEmpty()) property.name else name

    private fun findPreference(key: String): T {
        return when (default) {
            is Long -> prefs.getLong(key, default)
            is String -> prefs.getString(key, default)
            is Int -> prefs.getInt(key, default)
            else -> throw  IllegalArgumentException("Unsupported type.")
        } as T
    }

    private fun putPreference(key: String, value: T) {
        with(prefs.edit()) {
            when (value) {
                is Long -> putLong(key, value)
                is String -> putString(key, value)
                is Int -> putInt(key, value)
                else -> throw IllegalArgumentException("Unsupported type. ${value!!}")
            }
            apply()
        }
    }

}