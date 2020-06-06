package com.github.saboteur.ticketbookingsystem.ticketbookingservice.service

import com.github.saboteur.ticketbookingsystem.ticketbookingservice.dto.SessionDto

interface TicketBookingService {

    fun getAllSessions(pageIndex: Int, pageSize: Int): List<SessionDto>
    fun getActiveSessions(pageIndex: Int, pageSize: Int): List<SessionDto>

}