package com.example.mvp.impl

import android.support.v4.app.Fragment
import com.example.mvp.IMvpView

class BaseFragment<out P : BasePresenter<BaseFragment<P>>> : IMvpView<P>, Fragment() {

    override val presenter: P

    init {
        presenter = createPresenter()
        presenter.view = this
    }

    fun createPresenter(): P = TODO()
}