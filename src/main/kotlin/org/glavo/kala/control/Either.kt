package org.glavo.kala.control

import org.glavo.kala.collection.Iterators

sealed class Either<out L, out R> : Iterable<R> {
    open val leftValue: L
        get() = throw NoSuchElementException("leftValue on Right")

    open val rightValue: R
        get() = throw NoSuchElementException("rightValue on Left")
}

data class Left<out T>(val value: T) : Either<T, Nothing>() {
    override val leftValue: T
        get() = value

    override fun iterator(): Iterator<Nothing> = Iterators.emptyIterator
}

data class Right<out T>(val value: T) : Either<Nothing, T>() {
    override val rightValue: T
        get() = value

    override fun iterator(): Iterator<T> = object : Iterator<T> {
        private var flag = false

        override fun hasNext(): Boolean = flag

        override fun next(): T {
            flag = true
            return value
        }

    }
}