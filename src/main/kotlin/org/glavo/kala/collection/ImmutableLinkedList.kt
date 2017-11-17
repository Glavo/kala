package org.glavo.kala.collection

sealed class ImmutableLinkedList<out E> : ImmutableList<E> {
    abstract val head: E

    abstract val tail: ImmutableLinkedList<E>

    inline fun forEach(f: (E) -> Unit): Unit {
        var l: ImmutableLinkedList<E> = this
        while (l !== Nil) {
            f(l.head)
            l = l.tail
        }
    }

    inline fun forEachIndexed(f: (Int, E) -> Unit): Unit {
        var index = 0
        forEach {
            f(index, it)
            index++
        }
    }

    inline fun all(p: (E) -> Boolean): Boolean {
        forEach { if (!p(it)) return false }
        return true
    }

    inline fun any(p: (E) -> Boolean): Boolean {
        forEach { if (p(it)) return true }
        return false
    }

    override final val size: Int
        get() {
            var count = 0
            forEach { count++ }
            return count
        }

    override final fun contains(element: @UnsafeVariance E): Boolean {
        return any { it == element }
    }

    override final fun containsAll(elements: Collection<@UnsafeVariance E>): Boolean {
        return all { elements.contains(it) }
    }

    override final fun get(index: Int): E {
        if (index < 0)
            throw IndexOutOfBoundsException("index: $index")
        var i = index
        forEach {
            if (i == 0)
                return it
            i--
        }
        throw IndexOutOfBoundsException("index: $index")
    }

    override final fun indexOf(element: @UnsafeVariance E): Int {
        forEachIndexed { index, elem ->
            if (elem == element)
                return index
        }

        return -1
    }

    override fun lastIndexOf(element: @UnsafeVariance E): Int {
        TODO("not implemented")
    }
}

class Cons<out E>
@JvmOverloads
constructor(
        override val head: E,
        override val tail: ImmutableLinkedList<E> = Nil
) : ImmutableLinkedList<E>() {
    private inner class ConsIterator(var index: Int) : ListIterator<E> {
        var l: ImmutableLinkedList<@UnsafeVariance E> = run {
            if (index < 0)
                throw IndexOutOfBoundsException("index: $index")
            if (index == 0)
                this@Cons
            else {
                l = this@Cons
                var c = 0
                while (c < index) {
                    l = l.tail
                    c++
                }
                l
            }
        }

        override fun hasNext(): Boolean {
            return l !== Nil
        }

        override fun next(): E {
            return l.head.apply {
                l = l.tail
                index++
            }
        }

        override fun hasPrevious(): Boolean {
            return l !== this@Cons
        }

        override fun nextIndex(): Int {
            return index + 1
        }

        override fun previous(): E {
            if (!hasPrevious())
                throw NoSuchElementException()

            return l.head.apply {

                index--
            }
        }

        override fun previousIndex(): Int {
            TODO("not implemented")
        }
    }

    override fun isEmpty(): Boolean = false

    override fun iterator(): Iterator<E> = ConsIterator(0)

    override fun listIterator(): ListIterator<E> = ConsIterator(0)

    override fun listIterator(index: Int): ListIterator<E> = ConsIterator(index)

    override fun subList(fromIndex: Int, toIndex: Int): List<E> {
        TODO("not implemented")
    }
}

object Nil : ImmutableLinkedList<Nothing>() {

    override fun isEmpty(): Boolean = true

    override fun iterator(): Iterator<Nothing> = Iterators.emptyIterator

    override fun lastIndexOf(element: Nothing): Int = -1

    override val head: Nothing
        get() = throw NoSuchElementException("Nil.head")

    override fun listIterator(): ListIterator<Nothing> = Iterators.emptyListIterator

    override fun listIterator(index: Int): ListIterator<Nothing> {
        return if (index == 0) Iterators.emptyListIterator
        else throw IndexOutOfBoundsException("index=$index")
    }


    override fun subList(fromIndex: Int, toIndex: Int): List<Nothing> {
        return if (fromIndex == 0 && toIndex == 0) Nil
        else throw IndexOutOfBoundsException("Nil.subList(fromIndex=$fromIndex, toIndex=$toIndex)")
    }

    override val tail: ImmutableLinkedList<Nothing>
        get() = throw NoSuchElementException("Nil.tail")

    override fun toString(): String = "[]"
}