package com.avast.kotlinbasics

import kotlinx.coroutines.*
import org.slf4j.LoggerFactory
import org.springframework.util.StopWatch
import reactor.core.publisher.Flux
import reactor.core.publisher.toFlux
import reactor.core.scheduler.Schedulers
import java.util.concurrent.ConcurrentLinkedQueue
import java.util.regex.Pattern
import java.util.stream.IntStream
import kotlin.streams.toList

object Operation {
    private val logger = LoggerFactory.getLogger(Operation::class.java)
    private val pattern = Pattern.compile("avast-cleanup-preauth-trial-\\d{1,2}\\w?-\\d{1,2}\\w|avast-cleanup-trial-\\d{1,2}\\w?-\\d{1,2}\\w|avast-cleanup-\\d{1,2}\\w?-\\d{1,2}\\w")
    private const val patternExpression = "avast-cleanup-preauth-trial-\\d{1,2}\\w?-\\d{1,2}\\w|avast-cleanup-trial-\\d{1,2}\\w?-\\d{1,2}\\w|avast-cleanup-\\d{1,2}\\w?-\\d{1,2}\\w"

    fun runPrecompiled(value: String): Boolean {
        pattern.matcher(value).matches()
//        logger.info("$value done")
        return true
    }

    fun runWithCompilation(value: String): Boolean {
        patternExpression.matches(Regex(value))
//        logger.info("$value done")
        return true
    }
}

//val operation = Operation::runPrecompiled
val operation = Operation::runWithCompilation

object Sequential {
    fun run(data: Flux<String>): Flux<Boolean> {
        return data.map(operation)
    }
}

object Parallel {
    private const val parallelism = 2

    fun run(data: Flux<String>): Flux<Boolean> {
        return data.parallel(parallelism).runOn(Schedulers.parallel())
                .map(operation).sequential()
    }
}

object Suspend {
    fun run(data: List<String>): List<Boolean> {
        return runBlocking {
            data.map {
                GlobalScope.async {
                    operation(it)
                }
            }.awaitAll()
        }

    }

    fun run2(data: List<String>): Collection<Boolean> {
        val lstOfReturnData = ConcurrentLinkedQueue<Boolean>()
        runBlocking {
            data.forEach {
                launch {
                    lstOfReturnData.add(operation(it))
                }
            }
        }
        return lstOfReturnData
    }
}


fun main() {
    val data = IntStream.range(0, 200000)
            .mapToObj { "Avast Cleanup $it" }
            .toList()

    val sw = StopWatch()
    IntStream.range(0,3).forEach { i->

        println("sequential $i")
        Sequential.run(data.toFlux())
                .doOnSubscribe { sw.start("sequential $i") }
                .doOnComplete { sw.stop() }
                .collectList().block();

        println("parallel $i")
        Parallel.run(data.toFlux())
                .doOnSubscribe { sw.start("parallel $i") }
                .doOnComplete { sw.stop() }
                .collectList().block();

        println("suspend 1 $i")
        sw.start("suspend 1 $i")
        Suspend.run(data)
        sw.stop()

        println("suspend 2 $i")
        sw.start("suspend 2 $i")
        Suspend.run2(data)
        sw.stop()
    }

    println(sw.prettyPrint())
}

