package kala.collection

interface IntIterable : Iterable<Int> {
    /**
     * Returns an iterator over the elements of this object.
     */
    override fun iterator(): IntIterator
}

interface MutableIntIterable : MutableIterable<Int>, IntIterator {
    /**
     * Returns an iterator over the elements of this sequence that supports removing elements during iteration.
     */
    override fun iterator(): MutableIntIterator
}

inline fun foreach(i: IntIterable, action: (Int) -> Unit) {
    val it = i.iterator()
    while (it.hasNext()) {
        action(it.nextValue())
    }
}

inline fun IntIterable.forEach(action: (Int) -> Unit) {
    val it = this.iterator()
    while (it.hasNext()) {
        action(it.nextValue())
    }
}

inline fun IntIterable.forEachIndexed(action: (index: Int, Int) -> Unit) {
    val it = this.iterator()
    var index: Int = 0
    while (it.hasNext()) {
        action(index++, it.nextValue())
    }
}
