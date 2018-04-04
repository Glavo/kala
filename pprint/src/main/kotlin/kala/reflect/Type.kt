package kala.reflect

import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type
import kotlin.reflect.KClass
import kotlin.reflect.KType
import kotlin.reflect.full.createType
import kotlin.reflect.jvm.jvmErasure

fun kclassOfTypeToken(kclass: KClass<*>): KType =
        (kclass.supertypes.find { it.jvmErasure == TypeToken::class }
                ?: throw RuntimeException("$kclass is not subtype of TypeToken")).arguments[0].type!!


abstract class TypeToken<T> protected constructor()

inline fun <reified T> typeOf(): KType {
    return kclassOfTypeToken((object : TypeToken<T>() {})::class)
}

fun main(args: Array<String>) {
    println(typeOf<String>())
}