package com.github.saboteur.ticketbookingsystem.ticketbookingservice.repository

import com.github.saboteur.ticketbookingsystem.ticketbookingservice.model.Client
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ClientRepository : JpaRepository<Client, Long>