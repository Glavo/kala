package org.glavo.kala

sealed class For<out E> {
    companion object {

        @JvmStatic
        @JvmName("of")
        operator fun <T> invoke(value: Iterable<T>): OfIterable<T> {
            return OfIterable(value)
        }

        @JvmStatic
        @JvmName("of")
        inline operator fun <T> invoke(value: Iterable<T>, f: (T) -> Unit): Unit {
            value.forEach(f)
        }
    }

    class OfIterable<out E>(val value: Iterable<E>) : For<E>() {
        inline fun <T> yield(f: (E) -> T): Iterable<T> {
            return value.map(f)
        }
    }
}