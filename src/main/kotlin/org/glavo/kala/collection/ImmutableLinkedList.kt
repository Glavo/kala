package org.glavo.kala.collection

sealed class ImmutableLinkedList<out E> : ImmutableList<E> {
    abstract val head: E

    abstract val tail: ImmutableLinkedList<E>
}

class Cons<out E>
@JvmOverloads
constructor(
        override val head: E,
        override val tail: ImmutableLinkedList<E> = Nil
) : ImmutableLinkedList<E>() {

    inline fun forEach(f: (E) -> Unit): Unit {
        var l: ImmutableLinkedList<E> = this
        while (l !== Nil) {
            f(l.head)
            l = l.tail
        }
    }

    override val size: Int
        get() = TODO("not implemented")

    override fun contains(element: @UnsafeVariance E): Boolean {
        TODO("not implemented")
    }

    override fun containsAll(elements: Collection<@UnsafeVariance E>): Boolean {
        TODO("not implemented")
    }

    override fun get(index: Int): E {
        TODO("not implemented")
    }

    override fun indexOf(element: @UnsafeVariance E): Int {
        TODO("not implemented")
    }

    override fun isEmpty(): Boolean {
        TODO("not implemented")
    }

    override fun iterator(): Iterator<E> {
        TODO("not implemented")
    }

    override fun lastIndexOf(element: @UnsafeVariance E): Int {
        TODO("not implemented")
    }

    override fun listIterator(): ListIterator<E> {
        TODO("not implemented")
    }

    override fun listIterator(index: Int): ListIterator<E> {
        TODO("not implemented")
    }

    override fun subList(fromIndex: Int, toIndex: Int): List<E> {
        TODO("not implemented")
    }
}

object Nil : ImmutableLinkedList<Nothing>() {
    override val size: Int
        get() = TODO("not implemented")

    override fun contains(element: Nothing): Boolean {
        TODO("not implemented")
    }

    override fun containsAll(elements: Collection<Nothing>): Boolean {
        TODO("not implemented")
    }

    override fun get(index: Int): Nothing {
        TODO("not implemented")
    }

    override fun indexOf(element: Nothing): Int {
        TODO("not implemented")
    }

    override fun isEmpty(): Boolean {
        TODO("not implemented")
    }

    override fun iterator(): Iterator<Nothing> {
        TODO("not implemented")
    }

    override fun lastIndexOf(element: Nothing): Int {
        TODO("not implemented")
    }

    override val head: Nothing
        get() = TODO("not implemented")

    override fun listIterator(): ListIterator<Nothing> {
        TODO("not implemented")
    }

    override fun subList(fromIndex: Int, toIndex: Int): List<Nothing> {
        TODO("not implemented")
    }

    override val tail: ImmutableLinkedList<Nothing>
        get() = TODO("not implemented")

    override fun listIterator(index: Int): ListIterator<Nothing> {
        TODO("not implemented")
    }

    override fun toString(): String = "[]"
}