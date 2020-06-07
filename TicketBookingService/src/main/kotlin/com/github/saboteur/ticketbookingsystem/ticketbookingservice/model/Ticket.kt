package com.github.saboteur.ticketbookingsystem.ticketbookingservice.model

import java.time.LocalDateTime
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Table

@Entity
@Table(name = "tickets")
data class Ticket(

    @Column(name = "movie")
    val movie: String = "",

    @Column(name = "date")
    val date: LocalDateTime = LocalDateTime.MIN,

    @Column(name = "seat")
    val seat: String = "",

    @Column(name = "is_booked")
    var isBooked: Boolean = false

) : BaseModel() {
    companion object {
        val empty = Ticket(
            movie = "",
            date = LocalDateTime.MIN,
            seat = "",
            isBooked = false
        )
    }
}