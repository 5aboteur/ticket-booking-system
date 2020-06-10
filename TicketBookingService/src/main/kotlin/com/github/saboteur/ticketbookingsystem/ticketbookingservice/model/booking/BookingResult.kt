package com.github.saboteur.ticketbookingsystem.ticketbookingservice.model.booking

import com.github.saboteur.ticketbookingsystem.ticketbookingservice.domain.BookingOperation

class BookingResult {
    var userId: Long = -1L
    var sessionId: Long = -1L
    var bookedTicket: BookedTicket? = null
    var operation: BookingOperation = BookingOperation.FAILED
}