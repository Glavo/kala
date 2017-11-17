package org.glavo.kala.collection

import java.lang.StringBuilder
import java.util.*
import kotlin.collections.AbstractList

sealed class ImmutableLinkedList<out E> : ImmutableList<E> {
    abstract val head: E

    abstract val tail: ImmutableLinkedList<E>

    inline fun forEach(f: (E) -> Unit) {
        var l: ImmutableLinkedList<E> = this
        while (l !== Nil) {
            f(l.head)
            l = l.tail
        }
    }

    inline fun forEachIndexed(f: (Int, E) -> Unit) {
        var index = 0
        forEach {
            f(index, it)
            index++
        }
    }

    fun drop(n: Int): ImmutableLinkedList<E> {
        if (n < 0)
            return this
        var num = n
        var l = this
        while (num > 0) {
            if (l === Nil)
                return Nil
            l = l.tail
            num--
        }
        return l
    }

    inline fun all(p: (E) -> Boolean): Boolean {
        forEach { if (!p(it)) return false }
        return true
    }

    inline fun any(p: (E) -> Boolean): Boolean {
        forEach { if (p(it)) return true }
        return false
    }

    override final var size: Int = if (this === Nil) 0 else -1
        get() {
            return if (field == -1) {
                var count = 0
                forEach { count++ }
                count.apply { field = count }
            } else
                field
        }
        private set(value) {
            field = value
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
        var index = -1
        forEachIndexed { i, e ->
            if (e == element)
                index = i
        }
        return index
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

     inner class SubList(override val size: Int) : AbstractImmutableList<E>() {
        override fun subList(fromIndex: Int, toIndex: Int): ImmutableList<E> {
            if (fromIndex < 0)
                throw IndexOutOfBoundsException("fromIndex = $fromIndex")
            if (toIndex < 0)
                throw IndexOutOfBoundsException("toIndex = $toIndex")
            if (fromIndex > toIndex)
                throw IllegalArgumentException("fromIndex($fromIndex) < toIndex($toIndex)")
            if (fromIndex >= size) {
                throw IndexOutOfBoundsException("fromIndex = $fromIndex")
            }
            if (toIndex >= size) {
                throw IndexOutOfBoundsException("toIndex = $toIndex")
            }
            if(toIndex - fromIndex > size) {
                throw IndexOutOfBoundsException()
            }
            return (this@Cons.drop(fromIndex) as? Cons<E>)?.SubList(toIndex - fromIndex) ?: Nil
        }

        override fun get(index: Int): E {
            return this@Cons[index]
        }

    }

    private val str: String by lazy {
        val sb = StringBuilder(24)
        sb.append("[").append(head)
        tail.forEach {
            sb.append(", ").append(it)
        }
        sb.append("]")
        sb.toString()
    }

    private val hash: Int by lazy {
        var tem = 1
        forEach {
            tem = tem * 31 + (it?.hashCode() ?: 0)
        }
        tem
    }

    override fun isEmpty(): Boolean = false

    override fun iterator(): Iterator<E> = ConsIterator(0)

    override fun listIterator(): ListIterator<E> = ConsIterator(0)

    override fun listIterator(index: Int): ListIterator<E> = ConsIterator(index)

    override fun subList(fromIndex: Int, toIndex: Int): ImmutableList<E> {
        if (fromIndex < 0)
            throw IndexOutOfBoundsException("fromIndex = $fromIndex")
        if (toIndex < 0)
            throw IndexOutOfBoundsException("toIndex = $toIndex")
        if (fromIndex > toIndex)
            throw IllegalArgumentException("fromIndex($fromIndex) < toIndex($toIndex)")
        if (fromIndex >= size) {
            throw IndexOutOfBoundsException("fromIndex = $fromIndex")
        }
        if (toIndex >= size) {
            throw IndexOutOfBoundsException("toIndex = $toIndex")
        }
        if(toIndex - fromIndex > size) {
            throw IndexOutOfBoundsException()
        }
        return (drop(fromIndex) as? Cons<E>)?.SubList(toIndex - fromIndex) ?: Nil
    }

    override fun toString(): String {
        return str
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

    override fun subList(fromIndex: Int, toIndex: Int): ImmutableList<Nothing> {
        return if (fromIndex == 0 && toIndex == 0) Nil
        else throw IndexOutOfBoundsException("Nil.subList(fromIndex=$fromIndex, toIndex=$toIndex)")
    }

    override val tail: ImmutableLinkedList<Nothing>
        get() = throw NoSuchElementException("Nil.tail")

    override fun toString(): String = "[]"
}