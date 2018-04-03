@file:Suppress("NOTHING_TO_INLINE")

package kala.nio

import java.nio.*

fun ByteBuffer.asIterator() = object : Iterator<Byte> {
    override fun hasNext(): Boolean = this@asIterator.hasRemaining()

    override fun next(): Byte = this@asIterator.get()
}

inline operator fun ByteBuffer.iterator() = slice().asIterator()

fun ByteBuffer.asList(): List<Byte> = object : AbstractList<Byte>() {
    override val size: Int
        get() = limit() - position()


    override fun get(index: Int) = this@asList.get(index)

    override fun iterator() = this@asList.iterator()
}


fun ShortBuffer.asIterator() = object : Iterator<Short> {
    override fun hasNext(): Boolean = this@asIterator.hasRemaining()

    override fun next(): Short = this@asIterator.get()
}

inline operator fun ShortBuffer.iterator() = slice().asIterator()

fun ShortBuffer.asList(): List<Short> = object : AbstractList<Short>() {
    override val size: Int
        get() = limit() - position()


    override fun get(index: Int) = this@asList.get(index)

    override fun iterator() = this@asList.iterator()
}


fun IntBuffer.asIterator() = object : Iterator<Int> {
    override fun hasNext(): Boolean = this@asIterator.hasRemaining()

    override fun next(): Int = this@asIterator.get()
}

inline operator fun IntBuffer.iterator() = slice().asIterator()

fun IntBuffer.asList(): List<Int> = object : AbstractList<Int>() {
    override val size: Int
        get() = limit() - position()


    override fun get(index: Int) = this@asList.get(index)

    override fun iterator() = this@asList.iterator()
}

fun LongBuffer.asIterator() = object : Iterator<Long> {
    override fun hasNext(): Boolean = this@asIterator.hasRemaining()

    override fun next() = this@asIterator.get()
}

inline operator fun LongBuffer.iterator() = slice().asIterator()

fun LongBuffer.asList(): List<Long> = object : AbstractList<Long>() {
    override val size: Int
        get() = limit() - position()


    override fun get(index: Int) = this@asList.get(index)

    override fun iterator() = this@asList.iterator()
}

fun FloatBuffer.asIterator() = object : Iterator<Float> {
    override fun hasNext(): Boolean = this@asIterator.hasRemaining()

    override fun next() = this@asIterator.get()
}

inline operator fun FloatBuffer.iterator() = slice().asIterator()

fun FloatBuffer.asList(): List<Float> = object : AbstractList<Float>() {
    override val size: Int
        get() = limit() - position()


    override fun get(index: Int) = this@asList.get(index)

    override fun iterator() = this@asList.iterator()
}

fun DoubleBuffer.asIterator() = object : Iterator<Double> {
    override fun hasNext(): Boolean = this@asIterator.hasRemaining()

    override fun next() = this@asIterator.get()
}

inline operator fun DoubleBuffer.iterator() = slice().asIterator()

fun DoubleBuffer.asList(): List<Double> = object : AbstractList<Double>() {
    override val size: Int
        get() = limit() - position()


    override fun get(index: Int) = this@asList.get(index)

    override fun iterator() = this@asList.iterator()
}

fun CharBuffer.asIterator() = object : Iterator<Char> {
    override fun hasNext(): Boolean = this@asIterator.hasRemaining()

    override fun next() = this@asIterator.get()
}

inline operator fun CharBuffer.iterator() = slice().asIterator()

fun CharBuffer.asList(): List<Char> = object : AbstractList<Char>() {
    override val size: Int
        get() = limit() - position()


    override fun get(index: Int) = this@asList.get(index)

    override fun iterator() = this@asList.iterator()
}