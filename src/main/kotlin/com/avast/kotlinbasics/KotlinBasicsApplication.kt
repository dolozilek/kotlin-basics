package com.avast.kotlinbasics

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class KotlinBasicsApplication

fun main(args: Array<String>) {
	runApplication<KotlinBasicsApplication>(*args)
}
