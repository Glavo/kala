@file:JvmName("Tuples")

package org.glavo.kala

import java.io.Serializable
import java.util.*

//
//******************** Tuple0 ********************
//

/**
 * A tuple of no elements which can be seen as cartesian product of no components.
 *
 * @author Glavo
 */
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

    override operator fun compareTo(other: Tuple0): Int = 0

    // -- Object

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

//
//******************** Tuple1 ********************
//

/**
 * A tuple of one element which can be seen as cartesian product of one component.
 *
 * @param <T1> type of the 1st element
 * @author Glavo
 */
data class Tuple1<out T1>(
        @JvmField val _1: T1
) : Tuple, Serializable {


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
    }

    override fun arity(): Int = 1

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

    // -- Object

    override fun toString(): String =
            "($_1)"
}

operator fun <U1 : Comparable<U1>> Tuple1<U1>.compareTo(other: Tuple1<U1>): Int {
    val check1 = this._1.compareTo(other._1)
    return if (check1 != 0)
        check1
    else 0 // all components are equal
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

//
//******************** Tuple2 ********************
//

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
) : Tuple, Serializable, Map.Entry<T1, T2> {

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
    }

    override fun arity(): Int {
        return 2
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

    fun toPair(): Pair<T1, T2> {
        return Pair(_1, _2)
    }

    /**
     * Maps the components of this tuple using a mapper function.
     *
     * @param mapper the mapper function
     * @param <U1> new type of the 1st component
     * @param <U2> new type of the 2nd component
     * @return A new Tuple of same arity.
     * @throws NullPointerException if `mapper` is null
     */
    inline infix fun <U1, U2> map(mapper: (T1, T2) -> Tuple2<U1, U2>): Tuple2<U1, U2> {
        return mapper(_1, _2)
    }

    /**
     * Maps the components of this tuple using a mapper function for each component.
     *
     * @param f1 the mapper function of the 1st component
     * @param f2 the mapper function of the 2nd component
     * @param <U1> new type of the 1st component
     * @param <U2> new type of the 2nd component
     * @return A new Tuple of same arity.
     * @throws NullPointerException if one of the arguments is null
     */
    inline fun <U1, U2> map(f1: (T1) -> U1, f2: (T2) -> U2): Tuple2<U1, U2> {
        return tuple(f1(_1), f2(_2))
    }

    /**
     * Maps the 1st component of this tuple to a new value.
     *
     * @param <U> new type of the 1st component
     * @param mapper A mapping function
     * @return a new tuple based on this tuple and substituted 1st component
    </U> */
    inline infix fun <U> map1(mapper: (T1) -> U): Tuple2<U, T2> {
        return tuple(mapper(_1), _2)
    }

    /**
     * Maps the 2nd component of this tuple to a new value.
     *
     * @param <U> new type of the 2nd component
     * @param mapper A mapping function
     * @return a new tuple based on this tuple and substituted 2nd component
    </U> */
    inline infix fun <U> map2(mapper: (T2) -> U): Tuple2<T1, U> {
        return tuple(_1, mapper(_2))
    }

    /**
     * Transforms this tuple to an object of type U.
     *
     * @param f Transformation which creates a new object of type U based on this tuple's contents.
     * @param <U> type of the transformation result
     * @return An object of type U
     * @throws NullPointerException if `f` is null
    </U> */
    inline infix fun <U> apply(f: (T1, T2) -> U): U {
        return f(_1, _2)
    }

    // -- Object

    override fun toString(): String =
            "($_1, $_2)"

}

operator fun <U1 : Comparable<U1>, U2 : Comparable<U2>> Tuple2<U1, U2>.compareTo(other: Tuple2<U1, U2>): Int {
    val (_1, _2) = this
    val (_11, _21) = other

    val check1 = _1.compareTo(_11)
    if (check1 != 0) {
        return check1
    }

    val check2 = _2.compareTo(_21)
    return if (check2 != 0) {
        check2
    } else 0 // all components are equal
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

/**
 * Converts the tuple to java.util.Map.Entry `Tuple`.
 *
 * @return A  java.util.Map.Entry where the first element is the key and the second
 * element is the value.
 */
fun <T1, T2> Tuple2<T1, T2>.toEntry(): MutableMap.MutableEntry<T1, T2> {
    return AbstractMap.SimpleEntry(_1, _2)
}

//
//******************** Tuple3 ********************
//

data class Tuple3<out T1, out T2, out T3>(
        @JvmField val _1: T1,
        @JvmField val _2: T2,
        @JvmField val _3: T3
) {

}

//
//******************** Tuple4 ********************
//

data class Tuple4<out T1, out T2, out T3, out T4>(
        @JvmField val _1: T1,
        @JvmField val _2: T2,
        @JvmField val _3: T3,
        @JvmField val _4: T4
) {

}

//
//******************** Tuple5 ********************
//

data class Tuple5<out T1, out T2, out T3, out T4, out T5>(
        @JvmField val _1: T1,
        @JvmField val _2: T2,
        @JvmField val _3: T3,
        @JvmField val _4: T4,
        @JvmField val _5: T5
) {

}

//
//******************** Tuple6 ********************
//

data class Tuple6<out T1, out T2, out T3, out T4, out T5, out T6>(
        @JvmField val _1: T1,
        @JvmField val _2: T2,
        @JvmField val _3: T3,
        @JvmField val _4: T4,
        @JvmField val _5: T5,
        @JvmField val _6: T6
) {

}

//
//******************** Tuple7 ********************
//

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

//
//******************** Tuple8 ********************
//

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


//
//******************** Tuples ********************
//

/**
 * Creates a tuple of one element.
 *
 * @param <T1> type of the 1st element
 * @param t1 the 1st element
 * @return a tuple of one element.
</T1> */
fun <T1> tuple(t1: T1): Tuple1<T1> {
    return Tuple1(t1)
}

/**
 * Creates a tuple of two elements.
 *
 * @param <T1> type of the 1st element
 * @param <T2> type of the 2nd element
 * @param t1 the 1st element
 * @param t2 the 2nd element
 * @return a tuple of two elements.
</T2></T1> */
fun <T1, T2> tuple(t1: T1, t2: T2): Tuple2<T1, T2> {
    return Tuple2(t1, t2)
}

/**
 * Creates a tuple of three elements.
 *
 * @param <T1> type of the 1st element
 * @param <T2> type of the 2nd element
 * @param <T3> type of the 3rd element
 * @param t1 the 1st element
 * @param t2 the 2nd element
 * @param t3 the 3rd element
 * @return a tuple of three elements.
</T3></T2></T1> */
fun <T1, T2, T3> tuple(t1: T1, t2: T2, t3: T3): Tuple3<T1, T2, T3> {
    return Tuple3(t1, t2, t3)
}

/**
 * Creates a tuple of 4 elements.
 *
 * @param <T1> type of the 1st element
 * @param <T2> type of the 2nd element
 * @param <T3> type of the 3rd element
 * @param <T4> type of the 4th element
 * @param t1 the 1st element
 * @param t2 the 2nd element
 * @param t3 the 3rd element
 * @param t4 the 4th element
 * @return a tuple of 4 elements.
</T4></T3></T2></T1> */
fun <T1, T2, T3, T4> tuple(t1: T1, t2: T2, t3: T3, t4: T4): Tuple4<T1, T2, T3, T4> {
    return Tuple4(t1, t2, t3, t4)
}

/**
 * Creates a tuple of 5 elements.
 *
 * @param <T1> type of the 1st element
 * @param <T2> type of the 2nd element
 * @param <T3> type of the 3rd element
 * @param <T4> type of the 4th element
 * @param <T5> type of the 5th element
 * @param t1 the 1st element
 * @param t2 the 2nd element
 * @param t3 the 3rd element
 * @param t4 the 4th element
 * @param t5 the 5th element
 * @return a tuple of 5 elements.
</T5></T4></T3></T2></T1> */
fun <T1, T2, T3, T4, T5> tuple(t1: T1, t2: T2, t3: T3, t4: T4, t5: T5): Tuple5<T1, T2, T3, T4, T5> {
    return Tuple5(t1, t2, t3, t4, t5)
}

/**
 * Creates a tuple of 6 elements.
 *
 * @param <T1> type of the 1st element
 * @param <T2> type of the 2nd element
 * @param <T3> type of the 3rd element
 * @param <T4> type of the 4th element
 * @param <T5> type of the 5th element
 * @param <T6> type of the 6th element
 * @param t1 the 1st element
 * @param t2 the 2nd element
 * @param t3 the 3rd element
 * @param t4 the 4th element
 * @param t5 the 5th element
 * @param t6 the 6th element
 * @return a tuple of 6 elements.
</T6></T5></T4></T3></T2></T1> */
fun <T1, T2, T3, T4, T5, T6> tuple(t1: T1, t2: T2, t3: T3, t4: T4, t5: T5, t6: T6): Tuple6<T1, T2, T3, T4, T5, T6> {
    return Tuple6(t1, t2, t3, t4, t5, t6)
}

/**
 * Creates a tuple of 7 elements.
 *
 * @param <T1> type of the 1st element
 * @param <T2> type of the 2nd element
 * @param <T3> type of the 3rd element
 * @param <T4> type of the 4th element
 * @param <T5> type of the 5th element
 * @param <T6> type of the 6th element
 * @param <T7> type of the 7th element
 * @param t1 the 1st element
 * @param t2 the 2nd element
 * @param t3 the 3rd element
 * @param t4 the 4th element
 * @param t5 the 5th element
 * @param t6 the 6th element
 * @param t7 the 7th element
 * @return a tuple of 7 elements.
</T7></T6></T5></T4></T3></T2></T1> */
fun <T1, T2, T3, T4, T5, T6, T7> tuple(t1: T1, t2: T2, t3: T3, t4: T4, t5: T5, t6: T6, t7: T7): Tuple7<T1, T2, T3, T4, T5, T6, T7> {
    return Tuple7(t1, t2, t3, t4, t5, t6, t7)
}

/**
 * Creates a tuple of 8 elements.
 *
 * @param <T1> type of the 1st element
 * @param <T2> type of the 2nd element
 * @param <T3> type of the 3rd element
 * @param <T4> type of the 4th element
 * @param <T5> type of the 5th element
 * @param <T6> type of the 6th element
 * @param <T7> type of the 7th element
 * @param <T8> type of the 8th element
 * @param t1 the 1st element
 * @param t2 the 2nd element
 * @param t3 the 3rd element
 * @param t4 the 4th element
 * @param t5 the 5th element
 * @param t6 the 6th element
 * @param t7 the 7th element
 * @param t8 the 8th element
 * @return a tuple of 8 elements.
</T8></T7></T6></T5></T4></T3></T2></T1> */
fun <T1, T2, T3, T4, T5, T6, T7, T8> tuple(t1: T1, t2: T2, t3: T3, t4: T4, t5: T5, t6: T6, t7: T7, t8: T8): Tuple8<T1, T2, T3, T4, T5, T6, T7, T8> {
    return Tuple8(t1, t2, t3, t4, t5, t6, t7, t8)
}
