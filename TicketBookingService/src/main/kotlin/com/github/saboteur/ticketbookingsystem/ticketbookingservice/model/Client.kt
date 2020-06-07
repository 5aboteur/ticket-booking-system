package com.github.saboteur.ticketbookingsystem.ticketbookingservice.model

import javax.persistence.CascadeType
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.OneToMany
import javax.persistence.Table

@Entity
@Table(name = "clients")
data class Client(

    @Column(name = "category")
    var category: Int = 0,

    @OneToMany(cascade = [CascadeType.ALL])
    val tickets: MutableList<Ticket> = mutableListOf()

) : BaseModel() {
    companion object {
        val empty = Client(
            category = 0,
            tickets = mutableListOf()
        )
    }
}