package org.glavo.kala

fun main(args: Array<String>) {
    val v = For(1..10).yield {
        it + 10
    }
}