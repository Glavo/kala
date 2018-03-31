package kala

import kala.collection.EmptyIterator
import kala.collection.SingletonIterator

sealed class Try<out T> : Iterable<T> {

    data class Success<out T>(val value: T) : Try<T>() {
        override fun iterator(): Iterator<T> = SingletonIterator(value)

        override fun toString(): String = "Success($value)"
    }

    data class Failure(val exception: Throwable) : Try<Nothing>() {
        override fun iterator(): Iterator<Nothing> = EmptyIterator

        override fun toString(): String = "Failure($exception)"
    }
}

