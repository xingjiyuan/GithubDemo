package com.example

import com.example.common.Preferences
import com.example.github.AppContext

object Settings {
    var email: String by Preferences(AppContext, "email", "")
    var password: String by Preferences(AppContext, "password", "")
}