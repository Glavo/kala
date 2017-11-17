package org.glavo.kala.collection

object Iterators {
    val emptyIterator: MutableIterator<Nothing> = object : MutableIterator<Nothing> {
        override fun hasNext(): Boolean = false

        override fun next(): Nothing {
            throw NoSuchElementException("EmptyIterator.next")
        }

        override fun remove() {
            throw IllegalStateException()
        }
    }

    val emptyListIterator: ListIterator<Nothing> = object : ListIterator<Nothing> {
        override fun hasNext(): Boolean = false

        override fun hasPrevious(): Boolean = false

        override fun next(): Nothing {
            throw NoSuchElementException("EmptyListIterator.next")
        }

        override fun nextIndex(): Int = 0

        override fun previous(): Nothing {
            throw NoSuchElementException("EmptyListIterator.previous")
        }

        override fun previousIndex(): Int = -1

    }

}