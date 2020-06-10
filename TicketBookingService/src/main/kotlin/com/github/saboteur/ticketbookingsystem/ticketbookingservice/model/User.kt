package com.github.saboteur.ticketbookingsystem.ticketbookingservice.model

import javax.persistence.CascadeType
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.OneToMany
import javax.persistence.Table

@Entity
@Table(name = "users")
data class User(

    @Column(name = "login")
    val login: String = "",

    @Column(name = "email")
    val email: String = "",

    @Column(name = "is_admin")
    var isAdmin: Boolean = false,

    @Column(name = "category")
    var category: Int = 0,

    @OneToMany(cascade = [CascadeType.ALL])
    val tickets: MutableList<Ticket> = mutableListOf()

) : BaseModel() {
    companion object {
        val empty = User(
            login = "",
            email = "",
            isAdmin = false,
            category = 0,
            tickets = mutableListOf()
        )
    }
}