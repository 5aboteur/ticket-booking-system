package com.github.saboteur.ticketbookingsystem.ticketbookingservice.model

import java.time.LocalDateTime
import javax.persistence.CascadeType
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.ManyToOne
import javax.persistence.OneToMany
import javax.persistence.OneToOne
import javax.persistence.Table

@Entity
@Table(name = "sessions")
data class Session(

    @ManyToOne(fetch = FetchType.LAZY)
    val client: Client = Client.empty,

    @OneToOne(cascade = [CascadeType.ALL])
    val movie: Movie = Movie.empty,

    @Column(name = "number_of_tickets")
    var numberOfTickets: Int = 0,

    @Column(name = "remaining_tickets")
    var remainingTickets: Int = 0,

    @Column(name = "begin_date")
    val beginDate: LocalDateTime = LocalDateTime.MIN,

    @Column(name = "end_date")
    val endDate: LocalDateTime = LocalDateTime.MAX,

    @OneToMany(cascade = [CascadeType.ALL])
    val seats: List<Seat> = emptyList()

) : BaseModel() {
    companion object {
        val empty = Session(
            client = Client.empty,
            movie = Movie.empty,
            numberOfTickets = 0,
            remainingTickets = 0,
            beginDate = LocalDateTime.MIN,
            endDate = LocalDateTime.MAX,
            seats = emptyList()
        )
    }
}