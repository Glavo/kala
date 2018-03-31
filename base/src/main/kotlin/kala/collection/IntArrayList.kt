@file:Suppress("NOTHING_TO_INLINE")

package kala.collection

import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import java.io.Serializable
import java.util.*

private const val DEFAULT_CAPACITY = 16

open class IntArrayList
internal constructor(
        @Transient
        private var _size: Int,
        @Transient
        private var elements: IntArray)
    : AbstractMutableList<Int>(), Cloneable, Serializable, RandomAccess {
    constructor() : this(0, IntArray(DEFAULT_BUFFER_SIZE))

    private inline fun checkIndexRange(index: Int) {
        if (index < 0 || index >= _size)
            throw IndexOutOfBoundsException("Index: $index, Size: $size")
    }

    private fun grow(minCapacity: Int = this._size + 1) {
        val oldLength = this.elements.size
        val newLength = oldLength + (oldLength shr 1)
        if (newLength <= oldLength) {
            assert(false)
        }
        modCount++
        this.elements = Arrays.copyOf(this.elements, Math.max(newLength, minCapacity))
    }

    override val size: Int
        get() = _size

    override fun add(index: Int, element: Int) {
        if (index < 0 || index >= _size) {
            throw IndexOutOfBoundsException("Index: $index, Size: $size")
        }
        if (_size == elements.size) {
            grow()
        } else {
            modCount++
        }
        if (index != _size) {
            System.arraycopy(
                    elements, index,
                    elements, index + 1, _size - index
            )
        }
        elements[index] = element
        _size++
    }

    override fun get(index: Int): Int {
        checkIndexRange(index)
        return elements[index]
    }

    override fun removeAt(index: Int): Int {
        checkIndexRange(index)
        modCount++
        val old = elements[index]
        if (index != _size) {
            System.arraycopy(
                    elements, index,
                    elements, index - 1, _size - index
            )
        }
        _size--
        return old
    }

    override fun set(index: Int, element: Int): Int {
        checkIndexRange(index)
        val old = elements[index]
        elements[index] = element
        return old
    }

    override fun clear() {
        modCount++
        _size = 0
    }

    fun toIntArray(): IntArray = Arrays.copyOf(elements, _size)

    private fun readObject(input: ObjectInputStream) {
        input.defaultReadObject()
        val size = input.readInt()
        assert(size >= 0)
        val arr = IntArray(size)
        for (i in 0 until size) {
            arr[i] = input.readInt()
        }
        this._size = size
        this.elements = arr
    }

    private fun writeObject(output: ObjectOutputStream) {
        output.defaultWriteObject()
        output.writeInt(_size)
        for (i in 0 until _size) {
            output.writeInt(elements[i])
        }
    }

    override fun clone(): IntArrayList {
        try {
            val newList = super.clone() as IntArrayList
            newList.modCount = 0
            newList.elements = Arrays.copyOf(elements, _size)
            return newList
        } catch (e: CloneNotSupportedException) {
            throw Error(e)
        } catch (e: ClassCastException) {
            throw Error(e)
        }
    }

    override fun toString(): String =
            elements.joinToString(separator = ", ", prefix = "[", postfix = "]", limit = _size)

}
