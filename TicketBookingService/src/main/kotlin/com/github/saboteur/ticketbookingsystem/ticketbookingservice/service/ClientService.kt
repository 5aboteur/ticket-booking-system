package com.github.saboteur.ticketbookingsystem.ticketbookingservice.service

import com.github.saboteur.ticketbookingsystem.ticketbookingservice.dto.BookingResultDto
import com.github.saboteur.ticketbookingsystem.ticketbookingservice.dto.SeatDto
import com.github.saboteur.ticketbookingsystem.ticketbookingservice.dto.SessionOutDto
import com.github.saboteur.ticketbookingsystem.ticketbookingservice.dto.TicketDto

interface ClientService {

    fun getAllSessions(pageIndex: Int, pageSize: Int): List<SessionOutDto>
    fun getActiveSessions(pageIndex: Int, pageSize: Int): List<SessionOutDto>

    fun getSeats(sessionId: Long): List<SeatDto>
    fun bookTicket(userId: Long, sessionId: Long, seatNumber: String): BookingResultDto
    fun cancelBooking(userId: Long, sessionId: Long, seatNumber: String): BookingResultDto

    fun getTickets(userId: Long): List<TicketDto>

}