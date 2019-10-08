package com.avast.kotlinbasics

fun main() {


//   val expression1: Boolean by lazy {
//      println("Evaluating expression 1")
//      false
//   }
//
//   val expression2: Boolean by lazy {
//      println("Evaluating expression 2")
//      false
//   }
//
//   if (expression1 && expression2) {
//      println("Hello world")
//   }


   fun add (a:Int, b:Int): Int {
      return if (a > 10) a else a+b
   }


   val expression2: List<Int> by lazy {
      listOf<Int>()
   }


   fun add (a:Int, b:List<Int>) {
      add(a, b[0])
   }


   println(add(11, expression2))

}