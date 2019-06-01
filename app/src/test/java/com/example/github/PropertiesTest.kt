package com.example.github

import com.example.common.AbsProperties
import org.junit.Test

class InfoProps : AbsProperties("info.properties") {
    var name: String by prop
    var age: Int by prop
    val point: Float by prop
    val Student: Boolean by prop
}

class TestProperties {
    @Test
    fun testProperties() {
        InfoProps().let {
            println(it.name)
            println(it.age)
            it.age = 20
        }
    }
}