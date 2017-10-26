package org.glavo.kala.control

import java.util.*

sealed class Option<out T> {
    object None : Option<Nothing>() {
        override val isEmpty: Boolean
            get() = true

        override fun get(): Nothing {
            throw NoSuchElementException("No value present")
        }

        override fun toString(): String = "None"
    }

    data class Some<out T>(val value: T) : Option<T>() {
        override val isEmpty: Boolean
            get() = false

        override fun get(): T {
            return value
        }

        override fun toString(): String =
                "Some($value)"
    }

    companion object {
        /**
         * Creates a new {@code Option} of a given value.
         *
         * @param value A value
         * @param <T>   type of the value
         * @return {@code Some(value)} if value is not {@code null}, {@code None} otherwise
         */
        @JvmStatic
        fun <T> of(value: T?): Option<T> {
            return if (value == null) None else Some(value)
        }

        /**
         * Creates a new {@code Some} of a given value.
         * <p>
         * The only difference to {@link Option#of(Object)} is, when called with argument {@code null}.
         * <pre>
         * <code>
         * Option.of(null);   // = None
         * Option.some(null); // = Some(null)
         * </code>
         * </pre>
         *
         * @param value A value
         * @param <T>   type of the value
         * @return {@code Some(value)}
         */
        @JvmStatic
        fun <T> some(value: T): Option<T> {
            return Some(value)
        }

        /**
         * Returns the single instance of {@code None}
         *
         * @param <T> component type
         * @return the single instance of {@code None}
         */
        @JvmStatic
        fun <T> none(): Option<T> {
            return None
        }

        /**
         * Narrows a widened `Option<? extends T>` to `Option<T>`
         * by performing a type-safe cast. This is eligible because immutable/read-only
         * collections are covariant.
         *
         * @param option A `Option`.
         * @param <T>    Component type of the `Option`.
         * @return the given `option` instance as narrowed type `Option<T>`.
        </T> */
        fun <T> narrow(option: Option<T>): Option<T> {
            return option
        }

        /**
         * Creates `Some` of suppliers value if condition is true, or `None` in other case
         *
         * @param <T>       type of the optional value
         * @param condition A boolean value
         * @param supplier  An optional value supplier, may supply `null`
         * @return return `Some` of supplier's value if condition is true, or `None` in other case
         * @throws NullPointerException if the given `supplier` is null
         */
        @JvmStatic
        @JvmName("when")
        inline fun <T> whenIt(condition: Boolean, supplier: () -> T): Option<T> {
            return if (condition) some(supplier()) else none()
        }

        /**
         * Creates `Some` of value if condition is true, or `None` in other case
         *
         * @param <T>       type of the optional value
         * @param condition A boolean value
         * @param value     An optional value, may be `null`
         * @return return `Some` of value if condition is true, or `None` in other case
         */
        @JvmStatic
        @JvmName("when")
        fun <T> whenIt(condition: Boolean, value: T): Option<T> {
            return if (condition) some(value) else none()
        }

        /**
         * Wraps a Java Optional to a new Option
         *
         * @param optional a given optional to wrap in `Option`
         * @param <T>      type of the value
         * @return `Some(optional.get())` if value is Java `Optional` is present, `None` otherwise
         */
        fun <T> ofOptional(optional: Optional<out T>): Option<T> {
            return if (optional.isPresent) some(optional.get()) else none()
        }

    }

    /**
     * Is true, if this is `None`, otherwise false, if this is `Some`.
     */
    abstract val isEmpty: Boolean

    /**
     * Is true, if this is `Some`, otherwise false, if this is `None`.
     * <p>
     * Please note that it is possible to create `Some(null)`, which is defined.
     */
    val isDefined: Boolean
        get() = !isEmpty

    /**
     * Runs a Java Runnable passed as parameter if this `Option` is empty.
     *
     * @param action a given Runnable to be run
     * @return this `Option`
     */
    inline infix fun onEmpty(action: () -> Unit): Option<T> {
        if (isEmpty) {
            action()
        }
        return this
    }

    /**
     * Gets the value if this is a `Some` or throws if this is a `None`.
     *
     * @return the value
     * @throws NoSuchElementException if this is a `None`.
     */
    abstract fun get(): T


    /**
     * Returns the value if this is a `Some`, otherwise throws an exception.
     *
     * @param exceptionSupplier An exception supplier
     * @param <X>               A throwable
     * @return This value, if this Option is defined, otherwise throws X
     * @throws X a throwable
    </X> */
    inline infix fun <X : Throwable> getOrElseThrow(exceptionSupplier: () -> X): T {
        return if (isEmpty) {
            throw exceptionSupplier()
        } else {
            get()
        }
    }

    /**
     * Returns `Some(value)` if this is a `Some` and the value satisfies the given predicate.
     * Otherwise `None` is returned.
     *
     * @param predicate A predicate which is used to test an optional value
     * @return `Some(value)` or `None` as specified
     */
    inline infix fun filter(predicate: (T) -> Boolean): Option<T> {
        return if (isEmpty || predicate(get())) this else none()
    }

    /**
     * Maps the value to a new `Option` if this is a `Some`, otherwise returns `None`.
     *
     * @param mapper A mapper
     * @param <U>    Component type of the resulting Option
     * @return a new `Option`
    </U> */
    inline infix fun <U> flatMap(mapper: (T) -> Option<U>): Option<U> {
        return if (isEmpty) none() else mapper(get())
    }

    /**
     * Maps the value and wraps it in a new `Some` if this is a `Some`, returns `None`.
     *
     * @param mapper A value mapper
     * @param <U>    The new value type
     * @return a new `Some` containing the mapped value if this Option is defined, otherwise `None`, if this is empty.
    </U> */
    inline fun <U> map(mapper: (T) -> U): Option<U> {
        return if (isEmpty) none() else some(mapper(get()))
    }

    /**
     * Folds either the `None` or the `Some` side of the Option value.
     *
     * @param ifNone  maps the left value if this is a None
     * @param f maps the value if this is a Some
     * @param <U>         type of the folded value
     * @return A value of type U
    </U> */
    inline fun <U> fold(ifNone: () -> U, f: (T) -> U): U {
        return this.map(f).getOrElse(ifNone)
    }

    /**
     * Applies an action to this value, if this option is defined, otherwise does nothing.
     *
     * @param action An action which can be applied to an optional value
     * @return this `Option`
     */
    inline infix fun peek(action: (T) -> Unit): Option<T> {
        if (isDefined) {
            action(get())
        }
        return this
    }
}

/**
 * Returns the value if this is a `Some` or the `other` value if this is a `None`.
 *
 *
 * Please note, that the other value is eagerly evaluated.
 *
 * @param other An alternative value
 * @return This value, if this Option is defined or the `other` value, if this Option is empty.
 */
fun <T> Option<T>.getOrElse(other: T): T {
    return if (isEmpty) other else get()
}

/**
 * Returns this `Option` if it is nonempty, otherwise return the alternative.
 *
 * @param other An alternative `Option`
 * @return this `Option` if it is nonempty, otherwise return the alternative.
 */
fun <T> Option<T>.orElse(other: Option<T>): Option<T> {
    Objects.requireNonNull(other, "other is null")
    return if (isEmpty) other else this
}


/**
 * Returns this `Option` if it is nonempty, otherwise return the result of evaluating supplier.
 *
 * @param supplier An alternative `Option` supplier
 * @return this `Option` if it is nonempty, otherwise return the result of evaluating supplier.
 */
inline infix fun <T> Option<T>.orElse(supplier: () -> Option<T>): Option<T> {
    return if (isEmpty) supplier() else this
}

/**
 * Returns the value if this is a `Some`, otherwise the `other` value is returned,
 * if this is a `None`.
 *
 *
 * Please note, that the other value is lazily evaluated.
 *
 * @param supplier An alternative value supplier
 * @return This value, if this Option is defined or the `other` value, if this Option is empty.
 */
inline infix fun <T> Option<T>.getOrElse(supplier: () -> T): T {
    return if (isEmpty) supplier() else get()
}