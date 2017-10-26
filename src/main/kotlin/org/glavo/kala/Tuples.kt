@file:JvmName("Tuples")

package org.glavo.kala

import java.io.Serializable
import java.util.*


object Tuple0 : Tuple, Comparable<Tuple0>, Serializable {
    private const val serialVersionUID: Long = 1L

    /**
     * The singleton Tuple0 comparator.
     */
    @JvmStatic
    private val COMPARATOR: Comparator<Tuple0> = Comparator { _, _ -> 0 }

    /**
     * Returns the singleton instance of Tuple0.
     *
     * @return The singleton instance of Tuple0.
     */
    @JvmStatic
    fun instance(): Tuple0 {
        return Tuple0
    }

    @JvmStatic
    fun comparator(): Comparator<Tuple0> {
        return COMPARATOR
    }

    override fun arity(): Int = 0

    override fun compareTo(other: Tuple0): Int = 0

    override fun equals(other: Any?): Boolean =
            other === this

    override fun hashCode(): Int = 1

    override fun toString(): String = "()"

    // -- Serializable implementation

    /**
     * Instance control for object serialization.
     *
     * @return The singleton instance of Tuple0.
     * @see java.io.Serializable
     */
    private fun readResolve(): Any {
        return this
    }
}

/**
 * A tuple of one element which can be seen as cartesian product of one component.
 *
 * @param <T1> type of the 1st element
 * @author Glavo
 */
data class Tuple1<out T1>(
        @JvmField val _1: T1
) : Tuple, Comparable<Tuple1<@kotlin.UnsafeVariance T1>>, Serializable {


    companion object {
        private const val serialVersionUID: Long = 1L

        @JvmStatic
        fun <T1> comparator(t1Comp: Comparator<in T1>): Comparator<Tuple1<T1>> {
            return Comparator { t1, t2 ->
                val check1 = t1Comp.compare(t1._1, t2._1)
                if (check1 != 0)
                    check1
                else
                    0   // all components are equal
            }
        }

        @JvmStatic
        @Suppress("UNCHECKED_CAST")
        private fun <U1 : Comparable<U1>> compareTo(o1: Tuple1<*>, o2: Tuple1<*>): Int {
            val (_1) = o1 as Tuple1<U1>
            val (_11) = o2 as Tuple1<U1>

            val check1 = _1.compareTo(_11)
            return if (check1 != 0)
                check1
            else 0 // all components are equal
        }
    }

    override fun arity(): Int = 1

    override fun compareTo(other: Tuple1<@UnsafeVariance T1>): Int =
            Tuple1.compareTo<Nothing>(this, other)

    /**
     * Getter of the 1st element of this tuple.
     *
     * @return the 1st element of this Tuple.
     */
    fun _1(): T1 = _1


    /**
     * Maps the components of this tuple using a mapper function.
     *
     * @param mapper the mapper function
     * @param <U1> new type of the 1st component
     * @return A new Tuple of same arity.
     * @throws NullPointerException if `mapper` is null
    </U1> */
    infix inline fun <U1> map(mapper: (T1) -> U1): Tuple1<U1> {
        return Tuple1(mapper(_1))
    }

    /**
     * Transforms this tuple to an object of type U.
     *
     * @param f Transformation which creates a new object of type U based on this tuple's contents.
     * @param <U> type of the transformation result
     * @return An object of type U
     * @throws NullPointerException if `f` is null
    </U> */
    inline infix fun <U> apply(f: (T1) -> U): U {
        return f(_1)
    }
}

/**
 * Sets the 1st element of this tuple to the given `value`.
 *
 * @param value the new value
 * @return a copy of this tuple with a new value for the 1st element of this Tuple.
 */
fun <T1> Tuple1<T1>.update1(value: T1): Tuple1<T1> {
    return Tuple1(value)
}


/**
 * A tuple of two elements which can be seen as cartesian product of two components.
 *
 * @param <T1> type of the 1st element
 * @param <T2> type of the 2nd element
 * @author Glavo
 */
data class Tuple2<out T1, out T2>(
        @JvmField val _1: T1,
        @JvmField val _2: T2
) : Tuple, Comparable<Tuple2<@kotlin.UnsafeVariance T1, @kotlin.UnsafeVariance T2>>, Serializable, Map.Entry<T1, T2> {

    companion object {
        private const val serialVersionUID = 1L
        fun <T1, T2> comparator(t1Comp: Comparator<in T1>, t2Comp: Comparator<in T2>): Comparator<Tuple2<T1, T2>> {
            return Comparator { t1, t2 ->
                val check1 = t1Comp.compare(t1._1, t2._1)
                if (check1 != 0) {
                    return@Comparator check1
                }

                val check2 = t2Comp.compare(t1._2, t2._2)
                if (check2 != 0) {
                    return@Comparator check2
                }

                0// all components are equal
            }
        }

        @Suppress("UNCHECKED_CAST")
        private fun <U1 : Comparable<U1>, U2 : Comparable<U2>> compareTo(o1: Tuple2<*, *>, o2: Tuple2<*, *>): Int {
            val (_1, _2) = o1 as Tuple2<U1, U2>
            val (_11, _21) = o2 as Tuple2<U1, U2>

            val check1 = _1.compareTo(_11)
            if (check1 != 0) {
                return check1
            }

            val check2 = _2.compareTo(_21)
            return if (check2 != 0) {
                check2
            } else 0 // all components are equal
        }

    }

    override fun arity(): Int {
        return 2
    }

    override operator fun compareTo(other: Tuple2<@UnsafeVariance T1, @UnsafeVariance T2>): Int {
        return Tuple2.compareTo<Nothing, Nothing>(this, other)
    }

    /**
     * Getter of the 1st element of this tuple.
     *
     * @return the 1st element of this Tuple.
     */
    fun _1(): T1 {
        return _1
    }

    /**
     * Getter of the 2nd element of this tuple.
     *
     * @return the 2nd element of this Tuple.
     */
    fun _2(): T2 {
        return _2
    }

    override val key: T1
        get() = _1

    override val value: T2
        get() = _2

    /**
     * Swaps the elements of this `Tuple`.
     *
     * @return A new Tuple where the first element is the second element of this Tuple
     * and the second element is the first element of this Tuple.
     */
    fun swap(): Tuple2<T2, T1> {
        return Tuple2(_2, _1)
    }


}

/**
 * Sets the 1st element of this tuple to the given `value`.
 *
 * @param value the new value
 * @return a copy of this tuple with a new value for the 1st element of this Tuple.
 */
fun <T1, T2> Tuple2<T1, T2>.update1(value: T1): Tuple2<T1, T2> {
    return Tuple2(value, _2)
}

/**
 * Sets the 1st element of this tuple to the given `value`.
 *
 * @param value the new value
 * @return a copy of this tuple with a new value for the 1st element of this Tuple.
 */
fun <T1, T2> Tuple2<T1, T2>.update2(value: T2): Tuple2<T1, T2> {
    return Tuple2(_1, value)
}


data class Tuple3<out T1, out T2, out T3>(
        @JvmField val _1: T1,
        @JvmField val _2: T2,
        @JvmField val _3: T3
) {

}


data class Tuple4<out T1, out T2, out T3, out T4>(
        @JvmField val _1: T1,
        @JvmField val _2: T2,
        @JvmField val _3: T3,
        @JvmField val _4: T4
) {

}

data class Tuple5<out T1, out T2, out T3, out T4, out T5>(
        @JvmField val _1: T1,
        @JvmField val _2: T2,
        @JvmField val _3: T3,
        @JvmField val _4: T4,
        @JvmField val _5: T5
) {

}

data class Tuple6<out T1, out T2, out T3, out T4, out T5, out T6>(
        @JvmField val _1: T1,
        @JvmField val _2: T2,
        @JvmField val _3: T3,
        @JvmField val _4: T4,
        @JvmField val _5: T5,
        @JvmField val _6: T6
) {

}

data class Tuple7<out T1, out T2, out T3, out T4, out T5, out T6, out T7>(
        @JvmField val _1: T1,
        @JvmField val _2: T2,
        @JvmField val _3: T3,
        @JvmField val _4: T4,
        @JvmField val _5: T5,
        @JvmField val _6: T6,
        @JvmField val _7: T7
) {

}

data class Tuple8<out T1, out T2, out T3, out T4, out T5, out T6, out T7, out T8>(
        @JvmField val _1: T1,
        @JvmField val _2: T2,
        @JvmField val _3: T3,
        @JvmField val _4: T4,
        @JvmField val _5: T5,
        @JvmField val _6: T6,
        @JvmField val _7: T7,
        @JvmField val _8: T8
) {

}