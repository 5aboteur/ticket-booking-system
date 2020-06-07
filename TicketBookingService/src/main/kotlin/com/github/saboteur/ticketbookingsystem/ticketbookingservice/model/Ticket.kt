package com.github.saboteur.ticketbookingsystem.ticketbookingservice.model

import java.time.LocalDateTime
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Table

@Entity
@Table(name = "tickets")
data class Ticket(

    @Column(name = "price")
    var price: Double = 0.0,

    @Column(name = "discount_price")
    var discountPrice: Double = 0.0,

    @Column(name = "movie")
    val movie: String = "",

    @Column(name = "date")
    val date: LocalDateTime = LocalDateTime.MIN, // TODO: fix - doesn't change if a session was rescheduled

    @Column(name = "seat")
    val seat: String = "",

    @Column(name = "is_booked")
    var isBooked: Boolean = false

) : BaseModel() {
    companion object {
        val empty = Ticket(
            price = 0.0,
            discountPrice = 0.0,
            movie = "",
            date = LocalDateTime.MIN,
            seat = "",
            isBooked = false
        )
    }
}