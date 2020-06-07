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

    @OneToOne(cascade = [CascadeType.ALL])
    var admin: Administrator? = null,

    @OneToOne(cascade = [CascadeType.ALL])
    var client: Client? = null

) : BaseModel() {
    companion object {
        val empty = User(
            login = "",
            email = "",
            admin = null,
            client = null
        )
    }
}