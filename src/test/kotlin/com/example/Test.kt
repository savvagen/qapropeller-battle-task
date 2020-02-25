package com.example

import java.lang.IllegalArgumentException
import java.math.BigDecimal


fun sendPayment(money: Money, message: String = "Good Luck!") {
    println("Sending Money: ${money.ammount} ${money.currency}, Message: $message")
}

fun sum(x: Int, y: Int) = x + y


fun convertToDollars(money: Money): Money {
    when(money.currency){
        "$" -> return money
        "EUR" -> return Money(money.ammount * BigDecimal(1.10), "$")
        else -> throw IllegalArgumentException("Invalid Currency.")
    }
}


fun BigDecimal.percent(percentage: Int) = this.multiply(BigDecimal(percentage)).divide(BigDecimal(100))


fun main() {
    val tickets  = Money(BigDecimal(100), "$")
    val popcorn = tickets.copy(ammount = BigDecimal(500), currency = "EUR")
    if (tickets != popcorn){
        println("They Are different")
    }

    val bd1 = BigDecimal(500)
    println(bd1.percent(200))

    sendPayment(tickets, message = "Hello World!")




}