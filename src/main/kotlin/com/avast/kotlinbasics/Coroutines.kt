package com.avast.kotlinbasics


import kotlinx.coroutines.*
import kotlinx.coroutines.future.asDeferred
import java.util.concurrent.CompletableFuture

fun main() = runBlocking {
    println("App started")
    //    GlobalScope.launch {
//        val result1 = async { wait(5) }
//        val result2 = async { wait(7) }
//        println(result1.await() + result2.await())
//    }.join()


//    GlobalScope.launch {
//        val result1 = waitAsync(5)
//        val result2 = waitAsync(7)
//
//        delay(5000)
//
//        result1.start()
//        result2.start()
//
//        println(result1.await() + result2.await())
//    }.join()

    launch {
        val martin = getAccount("Martin", 5).asDeferred()
        val ondra = getAccount("Ondra", 3).asDeferred()

        println(martin.await())
        println(ondra.await())
    }.join()

    println("App Finished")


}


suspend fun wait(seconds: Long): Long {
    println("Waiting start for $seconds s ${Thread.currentThread().name}")
    delay(seconds * 1000)
    println("Waiting end for $seconds s")
    return seconds
}

fun waitAsync(seconds: Long) = GlobalScope.async(start = CoroutineStart.LAZY) {
    println("Waiting start for $seconds s ${Thread.currentThread().name}")
    delay(seconds * 1000)
    println("Waiting end for $seconds s")
    seconds
}

fun getAccount(name: String, seconds: Long): CompletableFuture<String> = CompletableFuture.supplyAsync {
    println("Futustart $name - ${Thread.currentThread().name}")
    Thread.sleep(seconds * 1000)
    println("Futuend $name")
    check(name != "Ondra") { "Ondra exception" }
    name
}
