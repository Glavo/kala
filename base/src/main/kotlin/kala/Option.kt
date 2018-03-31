@file:JvmName("Options")
@file:Suppress("NOTHING_TO_INLINE")

package kala

import kala.collection.*
import java.util.*
import kotlin.reflect.KProperty

class Option<out T> internal constructor(val value: T?) : Iterable<T> {

    val isEmpty: Boolean
        inline get() = value != null

    inline fun getOrNull(): T? = value

    inline operator fun getValue(thisRef: Any, property: KProperty<*>): T? = value

    override fun iterator(): Iterator<T> {
        return if (value != null) {
            SingletonIterator(value)
        } else {
            EmptyIterator
        }
    }

    override fun toString(): String {
        return if (value == null) {
            "None"
        } else {
            "Some($value)"
        }
    }
}

@JvmName("of")
fun <T> option(v: T?): Option<T> = if (v != null) Option(v) else none

fun <T : Any> some(v: T): Option<T> = Option(v)

val none: Option<Nothing> = Option(null)

@Suppress("UNCHECKED_CAST")
inline fun <T : Any> Option<T?>.narrow(): Option<T> = this as Option<T>

inline fun <T : Any> Option<T>.getOrElse(v: T): T = this.value ?: v

inline fun <T : Any> Option<T>.getOrElse(v: () -> T): T = this.value ?: v()

inline fun <T : Any> Option<T>.orElse(v: Option<T>): Option<T> {
    return if (value != null) this else v
}

inline fun <T : Any> Option<T>.orElse(v: () -> Option<T>): Option<T> {
    return if (value != null) this else v()
}

inline fun <T : Any> Option<T>.onEmpty(f: () -> Unit): Option<T> {
    if (isEmpty) f()
    return this
}

inline fun <T> Option<T>.asJava(): Optional<T> = Optional.ofNullable(value)

inline fun <T, U : Any> Option<T>.map(mapper: (T) -> U?): Option<U> {
    @Suppress("UNCHECKED_CAST")
    return if (isEmpty) none
    else option(mapper(value!!))
}

inline fun <T> Option<T>.filter(p: (T) -> Boolean): Option<T> {
    return when {
        isEmpty -> none
        p(value!!) -> this
        else -> none
    }
}