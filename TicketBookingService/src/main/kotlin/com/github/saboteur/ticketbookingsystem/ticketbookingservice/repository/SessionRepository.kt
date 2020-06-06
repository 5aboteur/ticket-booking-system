package com.github.saboteur.ticketbookingsystem.ticketbookingservice.repository

import com.github.saboteur.ticketbookingsystem.ticketbookingservice.model.Session
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface SessionRepository : JpaRepository<Session, Long> {

    @Query(
        value = "SELECT * FROM sessions s WHERE s.begin_date > NOW()",
        nativeQuery = true
    )
    fun findAllActive(pageable: Pageable): Page<Session>

}