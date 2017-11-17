package org.glavo.kala.collection

interface Iterator<out T> : kotlin.collections.Iterator<T>, java.util.Enumeration<@kotlin.UnsafeVariance T> {

    override fun hasMoreElements(): Boolean = hasNext()

    override fun nextElement(): T = nextElement()
}