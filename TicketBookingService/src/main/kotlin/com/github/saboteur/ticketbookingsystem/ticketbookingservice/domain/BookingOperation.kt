package com.github.saboteur.ticketbookingsystem.ticketbookingservice.domain

enum class BookingOperation(
    val operationName: String
) {
    CREATE("CREATE"),
    CANCEL("CANCEL"),
    UNKNOWN("UNKNOWN")
}