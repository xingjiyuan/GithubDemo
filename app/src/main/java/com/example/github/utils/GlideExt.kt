package com.example.github.utils

import cn.carbs.android.avatarimageview.library.AvatarImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

fun AvatarImageView.loadWithGlide(
    url: String,
    textPlaceholder: Char,
    requestOptions: RequestOptions = RequestOptions()
) {
    textPlaceholder.toString().let {
        setTextAndColorSeed(it.toUpperCase(), it.hashCode().toString())
    }
    Glide.with(this.context).load(url).apply(requestOptions).into(this)
}