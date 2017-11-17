package org.glavo.kala.collection

interface ImmutableList<out E> : List<E> {
    override fun subList(fromIndex: Int, toIndex: Int): ImmutableList<E>
}