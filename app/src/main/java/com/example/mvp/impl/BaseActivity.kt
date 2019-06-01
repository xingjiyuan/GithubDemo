package com.example.mvp.impl

import android.content.res.Configuration
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.mvp.IMvpView
import com.example.mvp.IPresenter
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type
import kotlin.reflect.KClass
import kotlin.reflect.full.isSubclassOf
import kotlin.reflect.full.primaryConstructor
import kotlin.reflect.jvm.jvmErasure

abstract class BaseActivity<out P : BasePresenter<BaseActivity<P>>> : IMvpView<P>, AppCompatActivity() {

    override val presenter: P

    init {
        presenter = createPresenterKt()
        presenter.view = this
    }

    private fun createPresenterKt(): P {
        sequence {
            var thisClass: KClass<*> = this@BaseActivity::class
            while (true) {
                yield(thisClass.supertypes)
                thisClass = thisClass.supertypes.firstOrNull()?.jvmErasure ?: break
            }
        }.flatMap {
            it.flatMap { it.arguments }.asSequence()
        }.first {
            it.type?.jvmErasure?.isSubclassOf(IPresenter::class) ?: false
        }.let {
            return it.type!!.jvmErasure.primaryConstructor!!.call() as P
        }
    }

    private fun createPresenter(): P {
        sequence<Type> {
            var thisClass: Class<*> = this@BaseActivity.javaClass
            while (true) {
                yield(thisClass.genericSuperclass)
                thisClass = thisClass.superclass ?: break
            }
        }.filter {
            it is ParameterizedType
        }.flatMap {
            (it as ParameterizedType).actualTypeArguments.asSequence()
        }.first {
            it is Class<*> && IPresenter::class.java.isAssignableFrom(it)
        }.let { return (it as Class<P>).newInstance() }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter.onCreate(savedInstanceState)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        presenter.onSaveInstanceState(outState)
    }


    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        presenter.onConfigurationChanged(newConfig)
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDestroy()
    }

    override fun onStart() {
        super.onStart()
        presenter.onStart()
    }

    override fun onStop() {
        super.onStop()
        presenter.onStop()
    }

    override fun onResume() {
        super.onResume()
        presenter.onResume()
    }

    override fun onPause() {
        super.onPause()
        presenter.onPause()
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        presenter.onViewStateRestored(savedInstanceState)
    }
}