package com.example.github.utils

import android.content.Context
import android.view.ViewManager
import android.widget.TextView
import cn.carbs.android.avatarimageview.library.AvatarImageView
import com.zzhoujay.richtext.RichText
import org.jetbrains.anko.AnkoViewDslMarker
import org.jetbrains.anko.custom.ankoView

var TextView.markdownText: String
    set(value) {
        RichText.fromMarkdown(value).into(this)
    }
    get() = text.toString()

inline fun ViewManager.avatarImageView(): AvatarImageView {
    return avatarImageView { }
}

inline fun ViewManager.avatarImageView(init: (@AnkoViewDslMarker AvatarImageView).() -> Unit): AvatarImageView {
    return ankoView({ ctx: Context -> AvatarImageView(ctx) }, theme = 0) {
        init()
    }
}