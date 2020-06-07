package com.github.saboteur.ticketbookingsystem.ticketbookingservice.service

import com.github.saboteur.ticketbookingsystem.ticketbookingservice.dto.BookingResultDto
import com.github.saboteur.ticketbookingsystem.ticketbookingservice.dto.SeatDto
import com.github.saboteur.ticketbookingsystem.ticketbookingservice.dto.SessionOutDto
import com.github.saboteur.ticketbookingsystem.ticketbookingservice.dto.TicketDto

interface TicketBookingClientService {

    fun getAllSessions(pageIndex: Int, pageSize: Int): List<SessionOutDto>
    fun getActiveSessions(pageIndex: Int, pageSize: Int): List<SessionOutDto>

    fun getSeats(sessionId: Long): List<SeatDto>
    fun bookTicket(clientId: Long, sessionId: Long, seatNumber: String): BookingResultDto
    fun cancelBooking(clientId: Long, sessionId: Long, seatNumber: String): BookingResultDto

    fun getTickets(clientId: Long): List<TicketDto>

}