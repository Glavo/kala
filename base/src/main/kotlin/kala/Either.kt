@file:JvmName("Eithers")

package kala

import kala.collection.EmptyIterator
import kala.collection.SingletonIterator

sealed class Either<out L, out R> : Iterable<R> {
    data class Left<out T>(val value: T) : Either<T, Nothing>() {
        override fun isLeft(): Boolean = true

        override val leftValue: T
            get() = value

        override fun iterator(): Iterator<Nothing> = EmptyIterator

        override fun toString(): String = "Left($value)"
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

        override fun toString(): String = "Right($value)"
    }

    inner class LeftProjection : Iterable<L> {
        inline fun <U> map(mapper: (L) -> U): Either<U, R> {
            @Suppress("UNCHECKED_CAST")
            return if (isLeft()) Left(mapper(leftValue))
            else this@Either as Either<U, R>
        }

        override fun iterator(): Iterator<L> {
            return when {
                isLeft() -> SingletonIterator(leftValue)
                else -> EmptyIterator
            }
        }

    }

    inner class RightProjection : Iterable<R> {
        inline fun <U> map(mapper: (R) -> U): Either<L, U> {
            @Suppress("UNCHECKED_CAST")
            return if (isRight()) Right(mapper(rightValue))
            else this@Either as Either<L, U>
        }

        override fun iterator(): Iterator<R> {
            return when {
                isRight() -> SingletonIterator(rightValue)
                else -> EmptyIterator
            }
        }
    }

    open val leftValue: L
        get() = throw NoSuchElementException("leftValue on Right")

    open val rightValue: R
        get() = throw NoSuchElementException("rightValue on Left")

    fun left(): LeftProjection {
        return LeftProjection()
    }

    fun right(): RightProjection {
        return RightProjection()
    }

    open fun isLeft(): Boolean = false

    fun isRight(): Boolean = !isLeft()

    fun getOrNull(): R? = if (isRight()) rightValue else null

    fun getLeftOrNull(): L? = if (isLeft()) leftValue else null

    fun getRightOrNull(): R? = if (isRight()) rightValue else null

    inline fun <X, Y> bimap(mapper1: (L) -> X, mapper2: (R) -> Y): Either<X, Y> {
        return if (isLeft())
            Left(mapper1(leftValue))
        else
            Right(mapper2(rightValue))
    }

    inline fun <U> map(mapper: (R) -> U): Either<L, U> {
        @Suppress("UNCHECKED_CAST")
        return if (isRight()) Right(mapper(rightValue))
        else this as Either<L, U>
    }
}

fun <T> left(value: T): Either<T, Nothing> = Either.Left(value)

fun <T> right(value: T): Either<Nothing, T> = Either.Right(value)

fun <R> Either<*, R>.getOrElse(other: R): R {
    return if (this.isRight()) this.rightValue else other
}

inline fun <L, R> Either<L, R>.getOrElseGet(other: (L) -> R): R {
    return if (this.isRight()) this.rightValue else other(this.leftValue)
}

inline fun <L, R> Either<L, R>.getOrElseThrow(other: (L) -> Throwable): R {
    return if (this.isRight()) this.rightValue else throw other(this.leftValue)
}
