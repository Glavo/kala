package org.glavo.kala.control

import org.glavo.kala.collection.EmptyIterator
import org.glavo.kala.collection.OneElementIterator

sealed class Try<out T> : Iterable<T>

data class Success<out T>(val value: T) : Try<T>() {
    override fun iterator(): Iterator<T> = OneElementIterator(value)

    override fun toString(): String = "Success($value)"
}

data class Failure(val exception: Throwable) : Try<Nothing>() {
    override fun iterator(): Iterator<Nothing> = EmptyIterator
}