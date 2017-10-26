package org.glavo.kala.control

import java.util.*

sealed class Option<out T> {
    object None : Option<Nothing>() {
        override fun toString(): String = "None"
    }

    data class Some<out T>(val value: T) : Option<T>() {
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
            return Some(value!!)
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
}

