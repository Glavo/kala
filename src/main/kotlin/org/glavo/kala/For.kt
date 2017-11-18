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
        operator fun <K, V> invoke(value: Map<K, V>): OfMap<K, V> {
            return OfMap(value)
        }

        @JvmStatic
        @JvmName("of")
        inline operator fun <T> invoke(value: Iterable<T>, f: (T) -> Unit): Unit {
            value.forEach(f)
        }
    }

    class OfIterable<out E>(val value: Iterable<E>) : For<E>() {
        infix inline fun <T> collect(f: (E) -> T): Iterable<T> {
            return value.map(f)
        }
    }

    class OfMap<K, out V>(val value: Map<K, V>) {
        infix inline fun <R> collect(f: (K, V) -> R): List<R> {
            return value.map { f(it.key, it.value) }
        }
    }
}