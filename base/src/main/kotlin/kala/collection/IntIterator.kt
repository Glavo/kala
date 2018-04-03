package kala.collection

interface IntIterator : Iterator<Int> {
    /**
     * Returns the next element in the iteration.
     */
    fun nextValue(): Int

    @Deprecated(
            message = DEPECATED_MESSAGE,
            replaceWith = ReplaceWith("this.nextValue()")
    )
    override fun next(): Int = nextValue()

    /**
     * Returns `true` if the iteration has more elements.
     */
    override fun hasNext(): Boolean
}

interface MutableIntIterator : MutableIterator<Int>, IntIterator

interface IntListIterator : ListIterator<Int>, IntIterator {
    /**
     * Returns the next element in the iteration.
     */
    override fun nextValue(): Int

    @Deprecated(
            message = DEPECATED_MESSAGE,
            replaceWith = ReplaceWith("this.nextValue()")
    )
    override fun next(): Int = nextValue()

    /**
     * Returns `true` if the iteration has more elements.
     */
    override fun hasNext(): Boolean

    /**
     * Returns the index of the element that would be returned by a subsequent call to [next].
     */
    override fun nextIndex(): Int

    /**
     * Returns the previous element in the iteration and moves the cursor position backwards.
     */
    fun previousValue(): Int

    @Deprecated(
            message = DEPECATED_MESSAGE,
            replaceWith = ReplaceWith("this.previousValue()")
    )
    override fun previous(): Int = previousValue()

    /**
     * Returns `true` if there are elements in the iteration before the current element.
     */
    override fun hasPrevious(): Boolean


    /**
     * Returns the index of the element that would be returned by a subsequent call to [previous].
     */
    override fun previousIndex(): Int
}

interface MutableIntListIterator : MutableListIterator<Int>, MutableIntIterator, IntListIterator {
    /**
     * Returns the next element in the iteration.
     */
    override fun nextValue(): Int

    /**
     * Returns the next element in the iteration.
     */
    @Deprecated(
            message = DEPECATED_MESSAGE,
            replaceWith = ReplaceWith("this.nextValue()")
    )
    override fun next(): Int = nextValue()

    /**
     * Returns `true` if the iteration has more elements.
     */
    override fun hasNext(): Boolean

    /**
     * Returns the index of the element that would be returned by a subsequent call to [next].
     */
    override fun nextIndex(): Int

    /**
     * Returns the previous element in the iteration and moves the cursor position backwards.
     */
    override fun previousValue(): Int

    /**
     * Returns the previous element in the iteration and moves the cursor position backwards.
     */
    /**
     * Returns the next element in the iteration.
     */
    @Deprecated(
            message = DEPECATED_MESSAGE,
            replaceWith = ReplaceWith("this.previousValue()")
    )
    override fun previous(): Int = previousValue()

    /**
     * Returns `true` if there are elements in the iteration before the current element.
     */
    override fun hasPrevious(): Boolean


    /**
     * Returns the index of the element that would be returned by a subsequent call to [previous].
     */
    override fun previousIndex(): Int

    /**
     * Removes from the underlying collection the last element returned by this iterator.
     */
    override fun remove()

    /**
     * Replaces the last element returned by [next] or [previous] with the specified element [element].
     */
    override fun set(element: Int)

    /**
     * Adds the specified element [element] into the underlying collection immediately before the element that would be
     * returned by [next], if any, and after the element that would be returned by [previous], if any.
     * (If the collection contains no elements, the new element becomes the sole element in the collection.)
     * The new element is inserted before the implicit cursor: a subsequent call to [next] would be unaffected,
     * and a subsequent call to [previous] would return the new element. (This call increases by one the value \
     * that would be returned by a call to [nextIndex] or [previousIndex].)
     */
    override fun add(element: Int)
}

fun Iterator<Int>.toIntIterator(): IntIterator = object : IntIterator {
    /**
     * Returns the next element in the iteration.
     */
    override fun nextValue(): Int = this@toIntIterator.next()

    /**
     * Returns `true` if the iteration has more elements.
     */
    override fun hasNext(): Boolean = this@toIntIterator.hasNext()

}

/**
 * Performs the given [operation] on each element of this [IntIterator].
 */
inline fun IntIterator.forEach(operation: (Int) -> Unit) {
    while (this.hasNext()) {
        operation(this.nextValue())
    }
}
