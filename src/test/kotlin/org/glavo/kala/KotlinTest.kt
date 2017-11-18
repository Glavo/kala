package org.glavo.kala

fun main(args: Array<String>) {
    val m = mapOf(
            0 to "abc",
            1 to "bcd",
            2 to "Hello"

    )

    val v = For(m) collect { k, _ ->
        k
    }
    println(v)
}