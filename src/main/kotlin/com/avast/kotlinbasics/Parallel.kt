package com.avast.kotlinbasics

import org.slf4j.LoggerFactory
import reactor.core.publisher.Flux
import reactor.core.publisher.toFlux
import reactor.core.scheduler.Schedulers
import java.util.regex.Pattern

object Operation {
    private val logger = LoggerFactory.getLogger(Operation::class.java)


    fun run(value: String):Boolean {
//        Thread.sleep(1000)
        logger.info("$value done")
        return true
    }
}

object Sequential {

    private val pattern = Pattern.compile(".*")
    private val logger = LoggerFactory.getLogger(Sequential::class.java)

    fun run(data: Flux<String>): Flux<Boolean> {
//        return data.map { pattern.matcher(it).matches() }
        return data.map(Operation::run)
//                .doOnNext { logger.info("Processing $it") }
//                .map { pattern.matcher(it).matches() }
    }
}

object Parallel {

    private val pattern = Pattern.compile(".*")
    private val logger = LoggerFactory.getLogger(Parallel::class.java)

    fun run(data: Flux<String>): Flux<Boolean> {
        return data.parallel(5).runOn(Schedulers.elastic())
                .map(Operation::run).sequential()
//                .doOnNext { logger.info("Processing $it") }
//                .map { pattern.matcher(it).matches() }.sequential()
    }
}


fun main() {
    val data = Flux.range(0, 20).map { "hello $it" }.collectList().block()!!

    val parStart = System.currentTimeMillis()
    Parallel.run(data.toFlux()).collectList().block();
    val parEnd = System.currentTimeMillis()

    val seqStart = System.currentTimeMillis()
    Sequential.run(data.toFlux()).collectList().block();
    val seqEnd = System.currentTimeMillis()

    println("Sequential: ${seqEnd - seqStart} ms")
    println("Parallel: ${parEnd - parStart} ms")

}


