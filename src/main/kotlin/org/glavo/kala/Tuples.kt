package org.glavo.kala

import java.io.Serializable
import java.util.Comparator

interface Tuple {
    companion object {
        const val MAX_ARITY: Int = 8
    }

    /**
     * Returns the number of elements of this tuple.
     *
     * @return the number of elements.
     */
    fun arity(): Int
}

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
}

data class Tuple2<out T1, out T2>(
        @JvmField val _1: T1,
        @JvmField val _2: T2
)

data class Tuple3<out T1, out T2, out T3>(
        @JvmField val _1: T1,
        @JvmField val _2: T2,
        @JvmField val _3: T3
)


data class Tuple4<out T1, out T2, out T3, out T4>(
        @JvmField val _1: T1,
        @JvmField val _2: T2,
        @JvmField val _3: T3,
        @JvmField val _4: T4
)

data class Tuple5<out T1, out T2, out T3, out T4, out T5>(
        @JvmField val _1: T1,
        @JvmField val _2: T2,
        @JvmField val _3: T3,
        @JvmField val _4: T4,
        @JvmField val _5: T5
)

data class Tuple6<out T1, out T2, out T3, out T4, out T5, out T6>(
        @JvmField val _1: T1,
        @JvmField val _2: T2,
        @JvmField val _3: T3,
        @JvmField val _4: T4,
        @JvmField val _5: T5,
        @JvmField val _6: T6
)

data class Tuple7<out T1, out T2, out T3, out T4, out T5, out T6, out T7>(
        @JvmField val _1: T1,
        @JvmField val _2: T2,
        @JvmField val _3: T3,
        @JvmField val _4: T4,
        @JvmField val _5: T5,
        @JvmField val _6: T6,
        @JvmField val _7: T7
)

data class Tuple8<out T1, out T2, out T3, out T4, out T5, out T6, out T7, out T8>(
        @JvmField val _1: T1,
        @JvmField val _2: T2,
        @JvmField val _3: T3,
        @JvmField val _4: T4,
        @JvmField val _5: T5,
        @JvmField val _6: T6,
        @JvmField val _7: T7,
        @JvmField val _8: T8
)