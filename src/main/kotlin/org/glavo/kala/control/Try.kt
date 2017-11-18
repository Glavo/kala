package org.glavo.kala.control

sealed class Try<out T> {
    companion object {
        @JvmStatic
        @JvmName("of")
        inline operator fun <T> invoke(f: () -> T): Try<T> {
            return try {
                Success(f())
            } catch (t: Throwable) {
                Failure(t)
            }
        }
    }
}

data class Success<out T>(val value: T) : Try<T>() {

}

data class Failure(val exception: Throwable) : Try<Nothing>()