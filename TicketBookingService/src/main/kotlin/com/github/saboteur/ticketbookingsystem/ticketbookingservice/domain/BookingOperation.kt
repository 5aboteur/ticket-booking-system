package com.github.saboteur.ticketbookingsystem.ticketbookingservice.domain

enum class BookingOperation(
    val operationName: String
) {
    CREATED("CREATED"),
    CANCELED("CANCELED"),
    FAILED("FAILED"),
    REJECTED("REJECTED")
}