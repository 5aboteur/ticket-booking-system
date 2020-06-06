package com.github.saboteur.ticketbookingsystem.ticketbookingservice.model

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.ManyToOne
import javax.persistence.Table

@Entity
@Table(name = "seats")
data class Seat(

    @ManyToOne(fetch = FetchType.LAZY)
    val session: Session = Session.empty,

    @Column(name = "number")
    val number: String = "",

    @Column(name = "is_booked")
    val isBooked: Boolean = false

) : BaseModel() {
    companion object {
        val empty = Seat(
            session = Session.empty,
            number = "",
            isBooked = false
        )
    }
}