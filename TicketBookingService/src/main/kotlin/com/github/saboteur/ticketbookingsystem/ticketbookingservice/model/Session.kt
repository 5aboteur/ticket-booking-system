package com.github.saboteur.ticketbookingsystem.ticketbookingservice.model

import java.time.LocalDateTime
import javax.persistence.CascadeType
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.OneToMany
import javax.persistence.OneToOne
import javax.persistence.Table

@Entity
@Table(name = "sessions")
data class Session(

    @OneToOne(cascade = [CascadeType.ALL])
    val movie: Movie = Movie.empty,

    @Column(name = "begin_date")
    var beginDate: LocalDateTime = LocalDateTime.MIN,

    @Column(name = "end_date")
    var endDate: LocalDateTime = LocalDateTime.MAX,

    @OneToMany(cascade = [CascadeType.ALL])
    val tickets: List<Ticket> = emptyList()

) : BaseModel() {
    companion object {
        val empty = Session(
            movie = Movie.empty,
            beginDate = LocalDateTime.MIN,
            endDate = LocalDateTime.MAX,
            tickets = emptyList()
        )
    }
}