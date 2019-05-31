package com.example.github

import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun testBoolean() {
        val result = true.yes {
            1
        }.otherwise {
            2
        }
        Assert.assertEquals(result, 1)

        val result2 = false.yes {
            1
        }.otherwise {
            2
        }

        Assert.assertEquals(result2, 2)

        val result3 = true.no {
            1
        }.otherwise {
            2
        }

        Assert.assertEquals(result3, 2)

        val result4 = false.no {
            1
        }.otherwise {
            2
        }

        Assert.assertEquals(result4, 1)
    }

    fun getABoolean() = true
}
