package org.glavo.kala

import org.junit.Assert.*
import org.junit.*

class ForTest {
    @Test
    fun testFor(): Unit {
        assertEquals(
                For(listOf(0, 10, 20)) collect { it + 10 },
                listOf(0, 10, 20).map { it + 10 }
        )
    }
}