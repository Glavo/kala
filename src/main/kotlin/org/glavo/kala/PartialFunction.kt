package org.glavo.kala

/**
 * Represents a partial function T -&gt; R that is not necessarily defined for all input values of type T.
 * The caller is responsible for calling the method isDefinedAt() before this function is applied to the value.
 * <p>
 * If the function <em>is not defined</em> for a specific value, apply() may produce an arbitrary result.
 * More specifically it is not guaranteed that the function will throw an exception.
 * <p>
 * If the function <em>is defined</em> for a specific value, apply() may still throw an exception.
 *
 * @param <T> type of the function input, called <em>domain</em> of the function
 * @param <R> type of the function output, called <em>codomain</em> of the function
 * @author Daniel Dietrich
 */
interface PartialFunction<in T, out R> : (T) -> R {

    /**
     * Tests if a value is contained in the function's domain.
     *
     * @param value a potential function argument
     * @return true, if the given value is contained in the function's domain, false otherwise
     */
    fun isDefinedAt(value: T): Boolean
}

inline fun <A, B> PartialFunction<A, B>.applyOrElse(x: A, default: (A) -> B): B {
    return if (isDefinedAt(x)) this(x) else default(x)
}

infix fun <T1, R, U> PartialFunction<T1, R>.andThen(g: (R) -> U): PartialFunction<T1, U> {
    return object : PartialFunction<T1, U> {
        override fun invoke(p1: T1): U {
            return g(this@andThen(p1))
        }

        override fun isDefinedAt(value: T1): Boolean {
            return this@andThen.isDefinedAt(value)
        }

    }
}