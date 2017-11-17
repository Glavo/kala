package org.glavo.kala.collection

abstract class AbstractImmutableList<out E> : AbstractList<E>(), ImmutableList<E> {
    abstract override fun subList(fromIndex: Int, toIndex: Int): ImmutableList<E>
}