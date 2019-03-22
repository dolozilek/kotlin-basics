package com.avast.kotlinbasics

import org.springframework.stereotype.Component

@Component
class Service {
    fun sayHello(name: String): String {
        return "Hello $name"
    }
}

@Component
class Handler(private val service: Service) {

    init {
        println(service.sayHello("OOOOOOhhhh"))
    }

    fun handle() {
        service.sayHello("Handler")
    }
}