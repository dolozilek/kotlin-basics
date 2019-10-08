package com.avast.kotlinbasics

data class Account(val email: String, val password: String? = null)

fun main() {
    println("Hello world")

    //variables
    var number = 10
    println(number)
    number = 11
    println(number)
    val constant = 11
//    constant = 12 - immutable


    //data class - default value, copy constructor, nullability
    val account = Account(email = "Martin")
    val acc2 = account.copy(email = "Ondra")


    // multiple assignment
    val (email, password) = account
    println(email)
    println(password)

    val array = listOf(account, acc2)
    for ((e, p) in array) {
        println(e)
        println(p)
    }

    //tuple - deprecated => data classes
    val aaa = "a" to "b"
    val map = mapOf(aaa)
    println(map)



    println(account == acc2.copy(email = "Martin"))

    println(account)
    println(acc2)
    println(Account(email = "Martin", password = "secret"))
    println(account.password!!.length)


    //functions
    println("function: ${::sayHello}")
    sayHello("Martin")

    val sayHelloFunction: (String) -> Unit = ::sayHello
    sayHelloFunction("function as a variable")

    sayHelloProvider(martinProvider)
    sayHelloProvider { "cool" }
    sayHelloProvider(providerBuilder())

    //implicit param - can be used multiple times
    println("Hello World".filter { it == 'o' || it == 'H' })

    println(square(5))

    //if as expression
    val name = if (System.currentTimeMillis() % 2 == 0L) "Martin" else "Ondra"
    println(name)

    //pattern matching
    val check = true
    val result = when (check) {
        true -> println("it's true")
        false -> println("it's false")
    }
    println(result)

    // sealed classes
    evaluate(Result.Success)
    evaluate(Result.Failure("Error"))

}


fun sayHello(name: String) {
    println("Hello $name")
}

fun sayHelloProvider(nameProvider: () -> String) {
    println(nameProvider)
    sayHello(nameProvider())
}

val providerBuilder: () -> () -> String = { { "Returns function" } }

val martinProvider = { "Martin" }

val square = { x: Int -> x * x }


sealed class Result {
    object Success : Result()
    class Failure(var message: String) : Result()
}

fun evaluate(r: Result) = when (r) {
    Result.Success -> println("Success")
    is Result.Failure -> println("Failure: ${r.message}")
}