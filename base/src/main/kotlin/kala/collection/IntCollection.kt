package kala.collection

interface IntCollection : IntIterable, Collection<Int> {
    /**
     * Returns the size of the collection.
     */
    override val size: Int

    /**
     * Checks if the specified element is contained in this collection.
     */
    override fun contains(element: Int): Boolean

    /**
     * Checks if all elements in the specified collection are contained in this collection.
     */
    override fun containsAll(elements: Collection<Int>): Boolean

    /**
     * Checks if all elements in the specified collection are contained in this collection.
     */
    fun containsAll(elements: IntCollection): Boolean = containsAll(elements as Collection<Int>)

    /**
     * Returns `true` if the collection is empty (contains no elements), `false` otherwise.
     */
    override fun isEmpty(): Boolean

    /**
     * Returns an iterator over the elements of this object.
     */
    override fun iterator(): IntIterator
}

interface MutableIntCollection : IntCollection, MutableCollection<Int> {
    /**
     * Returns an iterator over the elements of this object.
     */
    override fun iterator(): MutableIntIterator

    /**
     * Adds the specified element to the collection.
     *
     * @return `true` if the element has been added, `false` if the collection does not support duplicates
     * and the element is already contained in the collection.
     */
    override fun add(element: Int): Boolean

    /**
     * Removes a single instance of the specified element from this
     * collection, if it is present.
     *
     * @return `true` if the element has been successfully removed; `false` if it was not present in the collection.
     */
    fun removeValue(element: Int): Boolean

    @Deprecated(
            message = DEPECATED_MESSAGE,
            replaceWith = ReplaceWith("this.removeValue(element)")
    )
    override fun remove(element: Int): Boolean = removeValue(element)

    /**
     * Adds all of the elements in the specified collection to this collection.
     *
     * @return `true` if any of the specified elements was added to the collection, `false` if the collection was not modified.
     */
    override fun addAll(elements: Collection<Int>): Boolean

    /**
     * Adds all of the elements in the specified collection to this collection.
     *
     * @return `true` if any of the specified elements was added to the collection, `false` if the collection was not modified.
     */
    fun addAll(elements: IntCollection): Boolean = addAll(elements as Collection<Int>)

    /**
     * Removes all of this collection's elements that are also contained in the specified collection.
     *
     * @return `true` if any of the specified elements was removed from the collection, `false` if the collection was not modified.
     */
    override fun removeAll(elements: Collection<Int>): Boolean

    /**
     * Removes all of this collection's elements that are also contained in the specified collection.
     *
     * @return `true` if any of the specified elements was removed from the collection, `false` if the collection was not modified.
     */
    fun removeAll(elements: IntCollection): Boolean = removeAll(elements as Collection<Int>)

    /**
     * Retains only the elements in this collection that are contained in the specified collection.
     *
     * @return `true` if any element was removed from the collection, `false` if the collection was not modified.
     */
    override fun retainAll(elements: Collection<Int>): Boolean

    /**
     * Retains only the elements in this collection that are contained in the specified collection.
     *
     * @return `true` if any element was removed from the collection, `false` if the collection was not modified.
     */
    fun retainAll(elements: IntCollection): Boolean = retainAll(elements as Collection<Int>)

    /**
     * Removes all elements from this collection.
     */
    override fun clear(): Unit
}

