package kala.collection

interface IntList : IntCollection, List<Int> {
    /**
     * Returns the element at the specified index in the list.
     */
    override fun get(index: Int): Int

    /**
     * Returns the index of the first occurrence of the specified element in the list, or -1 if the specified
     * element is not contained in the list.
     */
    override fun indexOf(element: Int): Int

    /**
     * Returns the index of the last occurrence of the specified element in the list, or -1 if the specified
     * element is not contained in the list.
     */
    override fun lastIndexOf(element: Int): Int

    /**
     * Returns a list iterator over the elements in this list (in proper sequence).
     */
    override fun listIterator(): IntListIterator

    /**
     * Returns a list iterator over the elements in this list (in proper sequence), starting at the specified [index].
     */
    override fun listIterator(index: Int): IntListIterator

    /**
     * Returns a view of the portion of this list between the specified [fromIndex] (inclusive) and [toIndex] (exclusive).
     * The returned list is backed by this list, so non-structural changes in the returned list are reflected in this list, and vice-versa.
     *
     * Structural changes in the base list make the behavior of the view undefined.
     */
    override fun subList(fromIndex: Int, toIndex: Int): IntList
}

interface MutableIntList : IntList, MutableIntCollection, MutableList<Int> {
    /**
     * Returns the element at the specified index in the list.
     */
    override fun get(index: Int): Int

    /**
     * Returns the index of the first occurrence of the specified element in the list, or -1 if the specified
     * element is not contained in the list.
     */
    override fun indexOf(element: Int): Int

    /**
     * Returns the index of the last occurrence of the specified element in the list, or -1 if the specified
     * element is not contained in the list.
     */
    override fun lastIndexOf(element: Int): Int

    /**
     * Returns a list iterator over the elements in this list (in proper sequence).
     */
    override fun listIterator(): MutableIntListIterator

    /**
     * Returns a list iterator over the elements in this list (in proper sequence), starting at the specified [index].
     */
    override fun listIterator(index: Int): MutableIntListIterator

    /**
     * Returns a view of the portion of this list between the specified [fromIndex] (inclusive) and [toIndex] (exclusive).
     * The returned list is backed by this list, so non-structural changes in the returned list are reflected in this list, and vice-versa.
     *
     * Structural changes in the base list make the behavior of the view undefined.
     */
    override fun subList(fromIndex: Int, toIndex: Int): MutableIntList

    /**
     * Replaces the element at the specified position in this list with the specified element.
     *
     * @return the element previously at the specified position.
     */
    override operator fun set(index: Int, element: Int): Int


    @Deprecated(
            message = DEPECATED_MESSAGE,
            replaceWith = ReplaceWith("this.removeValue(element)")
    )
    override fun remove(element: Int): Boolean = removeValue(element)
}
