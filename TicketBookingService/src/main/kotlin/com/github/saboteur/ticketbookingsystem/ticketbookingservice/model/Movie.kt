package com.github.saboteur.ticketbookingsystem.ticketbookingservice.model

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Table

@Entity
@Table(name = "movies")
data class Movie(

    @Column(name = "name")
    val name: String = "",

    @Column(name = "genre")
    val genre: String = "",

    @Column(name = "director")
    val director: String = "",

    @Column(name = "year")
    val year: Int = 0

) : BaseModel() {
    companion object {
        val empty = Movie(
            name = "",
            genre = "",
            director = "",
            year = 0
        )
    }
}