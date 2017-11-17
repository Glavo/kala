package org.glavo.kala

interface Value<out T> : Iterable<T> {
    /**
     * Gets the underlying value or throws if no value is present.
     *
     *
     * **IMPORTANT! This method will throw an undeclared [Throwable] if `isEmpty() == true` is true.**
     *
     *
     * Because the 'empty' state indicates that there is no value present that can be returned,
     * `get()` has to throw in such a case. Generally, implementing classes should throw a
     * [java.util.NoSuchElementException] if `isEmpty()` returns true.
     *
     *
     * However, there exist use-cases, where implementations may throw other exceptions. See [Try.get].
     *
     *
     * **Additional note:** Dynamic proxies will wrap an undeclared exception in a [java.lang.reflect.UndeclaredThrowableException].
     *
     * @return the underlying value if this is not empty, otherwise `get()` throws a `Throwable`
     */
    fun get(): T


    /**
     * Checks if this `Value` is asynchronously (short: async) computed.
     *
     *
     * Methods of a `Value` instance that operate on the underlying value may block the current thread
     * until the value is present and the computation can be performed.
     *
     * @return true if this `Value` is async (like [Future]), false otherwise.
     */
    fun isAsync(): Boolean = false

    /**
     * Checks, this `Value` is empty, i.e. if the underlying value is absent.
     *
     * @return false, if no underlying value is present, true otherwise.
     */
    fun isEmpty(): Boolean

    /**
     * Checks if this `Value` is lazily evaluated.
     *
     * @return true if this `Value` is lazy (like [Lazy] and [Stream]), false otherwise.
     */
    fun isLazy(): Boolean = false

    /**
     * States whether this is a single-valued type.
     *
     * @return `true` if this is single-valued, `false` otherwise.
     */
    fun isSingleValued(): Boolean = true

    /**
     * Returns a rich `org.glavo.kala.collection.Iterator`.
     *
     * @return A new Iterator
     */
    override fun iterator(): Iterator<T>
}