package com.avast.kotlinbasics

class ParameterizedClass<A>(private val value: A) {

    fun getValue(): A {
        return value
    }
}


class ParameterizedProducer<out T>(private val value: T) {
    fun get(): T {
        return value
    }
}

class ParameterizedConsumer<in T> {
    fun toString(value: T): String {
        return value.toString()
    }
}


fun main() {
    val pc = ParameterizedClass("Hello World")
    println(pc.getValue() is String)

    val parameterizedProducer = ParameterizedProducer("string")

    val ref: ParameterizedProducer<Any> = parameterizedProducer

    println(ref is ParameterizedProducer<Any>)
}
