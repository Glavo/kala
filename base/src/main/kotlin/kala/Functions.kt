@file:JvmName("Functions")
package kala

typealias Predicate<T> = (T) -> Boolean

fun <T> Predicate<T>.test(t: T): Boolean {
    return this.invoke(t)
}

infix fun <R, U> (() -> R).andThen(g: (R) -> U): () -> U {
    return { g(this()) }
}

infix fun <T1, R, U> ((T1) -> R).andThen(g: (R) -> U): (T1) -> U {
    return { g(this(it)) }
}

infix fun <T1, T2, R, U> ((T1, T2) -> R).andThen(g: (R) -> U): (T1, T2) -> U {
    return { t1, t2 -> g(this(t1, t2)) }
}

infix fun <T1, T2, T3, R, U> ((T1, T2, T3) -> R).andThen(g: (R) -> U): (T1, T2, T3) -> U {
    return { t1, t2, t3 -> g(this(t1, t2, t3)) }
}

infix fun <T1, T2, T3, T4, R, U> ((T1, T2, T3, T4) -> R).andThen(g: (R) -> U): (T1, T2, T3, T4) -> U {
    return { t1, t2, t3, t4 -> g(this(t1, t2, t3, t4)) }
}
//TODO