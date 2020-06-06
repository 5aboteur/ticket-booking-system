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
    val category: Int = 0,

    @OneToMany(cascade = [CascadeType.ALL])
    val sessions: List<Session> = emptyList()

) : BaseModel() {
    companion object {
        val empty = Client(
            category = 0,
            sessions = emptyList()
        )
    }
}