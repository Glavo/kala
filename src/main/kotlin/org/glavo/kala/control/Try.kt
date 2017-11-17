package org.glavo.kala.control

sealed class Try<out T> {

}

data class Success<out T>(val value: T) : Try<T>() {

}

data class Failure(val exception: Throwable) : Try<Nothing>()