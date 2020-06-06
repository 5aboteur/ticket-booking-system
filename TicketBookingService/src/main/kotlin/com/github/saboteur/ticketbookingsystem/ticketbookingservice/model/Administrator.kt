package com.github.saboteur.ticketbookingsystem.ticketbookingservice.model

import javax.persistence.Entity
import javax.persistence.Table

@Entity
@Table(name = "administrators")
class Administrator : BaseModel() {
    companion object {
        val empty = Administrator()
    }
}