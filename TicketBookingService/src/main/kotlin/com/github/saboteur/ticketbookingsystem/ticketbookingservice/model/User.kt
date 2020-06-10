package com.github.saboteur.ticketbookingsystem.ticketbookingservice.model

import javax.persistence.CascadeType
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.OneToOne
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

    @OneToOne(cascade = [CascadeType.ALL])
    var client: Client? = null

) : BaseModel() {
    companion object {
        val empty = User(
            login = "",
            email = "",
            isAdmin = false,
            client = null
        )
    }
}