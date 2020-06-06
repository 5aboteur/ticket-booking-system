package com.github.saboteur.ticketbookingsystem.ticketbookingservice.model.booking

import java.time.LocalDateTime

class BookedTicket {
    var movie: String? = null
    var date: LocalDateTime? = null
    var seat: String? = null
}