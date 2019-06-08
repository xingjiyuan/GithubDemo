package com.example.github.view.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.github.R
import com.example.github.utils.avatarImageView
import com.example.github.utils.markdownText
import org.jetbrains.anko.*
import org.jetbrains.anko.support.v4.nestedScrollView

/**
 * Created by benny on 7/9/17.
 */
class AboutFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return AboutFragmentUI().createView(AnkoContext.Companion.create(context!!, this))
    }
}

class AboutFragmentUI : AnkoComponent<AboutFragment> {
    override fun createView(ui: AnkoContext<AboutFragment>): View = ui.apply {
        nestedScrollView {
            verticalLayout {

                avatarImageView {
                    setTextAndColorSeed("D", "11")
                }.lparams(width = dip(60), height = dip(60)) {
                    gravity = Gravity.CENTER_HORIZONTAL
                }

                imageView {
                    imageResource = R.mipmap.ic_launcher
                }.lparams(width = wrapContent, height = wrapContent) {
                    gravity = Gravity.CENTER_HORIZONTAL
                }
                themedTextView("GitHub", R.style.detail_title) {
                    textColor = R.color.colorPrimary
                }.lparams(width = wrapContent, height = wrapContent) {
                    gravity = Gravity.CENTER_HORIZONTAL
                }
                themedTextView("By Demo", R.style.detail_description) {
                    textColor = R.color.colorPrimary
                }.lparams(width = wrapContent, height = wrapContent) {
                    gravity = Gravity.CENTER_HORIZONTAL
                }
                themedTextView(R.string.open_source_licenses, R.style.detail_description) {
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

