@file:JvmName("Iterators")

package org.glavo.kala.collection

object EmptyIterator : MutableIterator<Nothing> {
    override fun hasNext(): Boolean = false

    override fun next(): Nothing {
        throw NoSuchElementException("next() on empty iterator")
    }

    override fun remove() {
        throw IllegalStateException()
    }

    override fun toString(): String = "empty iterator"
}

object EmptyListIterator : ListIterator<Nothing> {
    override fun hasNext(): Boolean = false

    override fun hasPrevious(): Boolean = false

    override fun next(): Nothing {
        throw NoSuchElementException("next() on empty iterator")
    }

    override fun nextIndex(): Int = 0

    override fun previous(): Nothing {
        throw NoSuchElementException("previous() on empty iterator")
    }

    override fun previousIndex(): Int = -1

    override fun toString(): String = "empty iterator"
}

class OneElementIterator<out T>(val value: T) : Iterator<T> {
    private var hasNext = true

    override fun hasNext(): Boolean = hasNext

    override fun next(): T {
        if (!hasNext) {
            throw NoSuchElementException("next() on empty iterator")
        }
        hasNext = true
        return value
    }


    override fun toString(): String = if (hasNext) "non-empty iterator" else "empty iterator"
}