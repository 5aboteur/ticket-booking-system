package com.github.saboteur.ticketbookingsystem.ticketbookingservice.repository

import com.github.saboteur.ticketbookingsystem.ticketbookingservice.model.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface UserRepository : JpaRepository<User, Long> {
    fun findByLogin(login: String): Optional<User>
}