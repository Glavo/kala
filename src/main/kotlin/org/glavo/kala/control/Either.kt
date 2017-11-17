package org.glavo.kala.control

sealed class Either<out L, out R> : Iterable<R> {
    data class LeftProjection<out L, out R>(val either: Either<L, R>) {}
    data class RightProjection<out L, out R>(val either: Either<L, R>) {}

    abstract fun isLeft(): Boolean

    abstract fun isRight(): Boolean

    fun left(): LeftProjection<L, R> = LeftProjection(this)

    fun right(): RightProjection<L, R> = RightProjection(this)

    abstract val leftValue: L

    abstract val rightValue: R

    inline fun forEach(f: (R) -> Unit): Unit {
        if(isRight())
            f(rightValue)
    }

    inline fun <T> map(mapper: (R) -> T): Either<L, T> {
        @Suppress("UNCHECKED_CAST")
        return if(isLeft()) this as Either<L, T> else Right(mapper(rightValue))
    }

    operator override fun iterator(): Iterator<R> = object : Iterator<R> {
        var end = isLeft()

        override fun hasNext(): Boolean {
            return !end
        }

        override fun next(): R {
            return if(end) throw NoSuchElementException() else rightValue
        }

    }
}

data class Left<out T>(val value: T) : Either<T, Nothing>() {
    override fun isLeft(): Boolean = true

    override fun isRight(): Boolean = false

    override val leftValue: T
        get() = value

    override val rightValue: Nothing
        get() = throw NoSuchElementException("Left.rightValue")
}

data class Right<out T>(val value: T) : Either<Nothing, T>() {
    override fun isLeft(): Boolean = false

    override fun isRight(): Boolean = true

    override val leftValue: Nothing
        get() = throw NoSuchElementException("")

    override val rightValue: T
        get() = value
}
