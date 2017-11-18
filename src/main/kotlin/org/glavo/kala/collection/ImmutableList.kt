package org.glavo.kala.collection

interface ImmutableList<out E> : List<E> {
    companion object {
        @JvmName("of")
        operator fun <E> invoke(vararg args: E): ImmutableList<E> {
            var l: ImmutableLinkedList<E> = Nil
            args.indices.reversed().forEach {
                l = Cons(args[it], l)
            }
            return l
        }
    }

    override fun subList(fromIndex: Int, toIndex: Int): ImmutableList<E>
}