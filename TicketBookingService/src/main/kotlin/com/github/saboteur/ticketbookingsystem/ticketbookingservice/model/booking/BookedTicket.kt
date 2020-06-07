package com.github.saboteur.ticketbookingsystem.ticketbookingservice.model.booking

import java.time.LocalDateTime

class BookedTicket {
    var price: Double? = null
    var discountPrice: Double? = null
    var movie: String? = null
    var date: LocalDateTime? = null
    var seat: String? = null
}