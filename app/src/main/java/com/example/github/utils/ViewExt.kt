package com.example.github.utils

import android.widget.TextView
import com.zzhoujay.richtext.RichText

var TextView.markdownText: String
    set(value) {
        RichText.fromMarkdown(value).into(this)
    }
    get() = text.toString()