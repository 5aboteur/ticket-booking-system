package com.github.saboteur.ticketbookingsystem.ticketbookingservice.dto

data class BookingResultDto(
    val clientId: Long,
    val sessionId: Long,
    val ticket: TicketDto,
    val operation: String,
    val resultMsg: String
) {
    companion object {
        val empty = BookingResultDto(
            clientId = -1L,
            sessionId = -1L,
            ticket = TicketDto.empty,
            operation = "",
            resultMsg = ""
        )
    }
}