package org.glavo.kala.control

import org.glavo.kala.collection.EmptyIterator

sealed class Either<out L, out R> : Iterable<R> {
    open val leftValue: L
        get() = throw NoSuchElementException("leftValue on Right")

    open val rightValue: R
        get() = throw NoSuchElementException("rightValue on Left")

    open fun isLeft(): Boolean = false

    fun isRight(): Boolean = !isLeft()

    fun orNull(): R? = if (isRight()) rightValue else null
}

data class Left<out T>(val value: T) : Either<T, Nothing>() {
    override fun isLeft(): Boolean = true

    override val leftValue: T
        get() = value

    override fun iterator(): Iterator<Nothing> = EmptyIterator
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