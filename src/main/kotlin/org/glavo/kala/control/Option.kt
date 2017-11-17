package org.glavo.kala.control

import java.util.*
import kotlin.reflect.KProperty

sealed class Option<out T>(
        @JvmField
        internal val _value: T?
) : Iterable<T> {

    companion object {
        @JvmStatic
        @JvmName("of")
        operator fun <T> invoke(value: T): Option<T> {
            return if (value == null) None
            else Some(value)
        }
    }

    val value: T
        get() = _value ?: throw NoSuchElementException("None.get")

    fun get(): T = value

    fun isEmpty(): Boolean =
            _value == null

    fun isDefined(): Boolean =
            !isEmpty()

    fun orNull(): T? {
        return _value
    }

    inline fun forEach(f: (T) -> Unit) {
        if (!isEmpty())
            f(value)
    }

    inline fun <V> map(mapper: (T) -> V): Option<V> {
        return if (isEmpty()) None else Some(mapper(value))
    }

    inline fun filter(p: (T) -> Boolean): Option<T> {
        return when {
            isEmpty() -> None
            p(value) -> this
            else -> None
        }
    }

    inline fun filterNot(p: (T) -> Boolean): Option<T> {
        return when {
            isEmpty() -> None
            !p(value) -> this
            else -> None
        }
    }

    operator override fun iterator(): Iterator<T> = object : Iterator<T> {
        private var end = _value == null

        override fun hasNext(): Boolean {
            return end
        }

        override fun next(): T {
            end = true
            return _value!!
        }
    }

    override abstract fun equals(other: Any?): Boolean

    override final fun hashCode(): Int {
        return Objects.hashCode(_value)
    }
}

@Suppress("EqualsOrHashCode")
class Some<out T>(_value: T) : Option<T>(_value) {
    init {
        this._value!!
    }

    override fun equals(other: Any?): Boolean {
        return other is Some<*> && other._value == _value
    }

    override fun toString(): String {
        return "Option($_value)"
    }
}

object None : Option<Nothing>(null) {
    override fun equals(other: Any?): Boolean {
        return other === this
    }

    override fun toString(): String {
        return "None"
    }
}

operator fun <T> Option<T>.getValue(thisRef: Any?, property: KProperty<*>): T? {
    return orNull()
}

fun <T> Option<T>.getOrElse(default: T): T {
    return if (isEmpty()) default else get()
}

inline fun <T> Option<T>.getOrElse(default: () -> T): T {
    return if (isEmpty()) default() else get()
}

fun <T> Option<Option<T>>.flatten(): Option<T> {
    return getOrElse(None)
}

