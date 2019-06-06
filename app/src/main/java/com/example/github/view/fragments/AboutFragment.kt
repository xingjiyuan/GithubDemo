package com.example.github.view.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.github.R
import com.example.github.utils.markdownText
import org.jetbrains.anko.*
import org.jetbrains.anko.support.v4.UI
import org.jetbrains.anko.support.v4.nestedScrollView

/**
 * Created by benny on 7/9/17.
 */
class AboutFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return UI {
            nestedScrollView {
                verticalLayout {
                    imageView {
                        imageResource = R.mipmap.ic_launcher
                    }.lparams(width = wrapContent, height = wrapContent) {
                        gravity = Gravity.CENTER_HORIZONTAL
                    }
                    textView("GitHub") {
                        textColor = R.color.colorPrimary
                    }.lparams(width = wrapContent, height = wrapContent) {
                        gravity = Gravity.CENTER_HORIZONTAL
                    }
                    textView("By Demo") {
                        textColor = R.color.colorPrimary
                    }.lparams(width = wrapContent, height = wrapContent) {
                        gravity = Gravity.CENTER_HORIZONTAL
                    }
                    textView(R.string.open_source_licenses) {
                        textColor = R.color.colorPrimary
                        setOnClickListener {
                            alert {
                                customView {
                                    scrollView {
                                        textView {
                                            padding = dip(10)
                                            markdownText =
                                                context.assets.open("licenses.md").bufferedReader().readText()
                                        }
                                    }
                                }
                            }.show()
                        }
                    }.lparams(width = wrapContent, height = wrapContent) {
                        gravity = Gravity.CENTER_HORIZONTAL
                    }
                }.lparams(width = wrapContent, height = wrapContent) {
                    gravity = Gravity.CENTER
                }
            }
        }.view
    }
}