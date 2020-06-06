package com.github.saboteur.ticketbookingsystem.ticketbookingservice.service

import com.github.saboteur.ticketbookingsystem.ticketbookingservice.dto.BookingResultDto
import com.github.saboteur.ticketbookingsystem.ticketbookingservice.dto.SeatDto
import com.github.saboteur.ticketbookingsystem.ticketbookingservice.dto.SessionDto
import com.github.saboteur.ticketbookingsystem.ticketbookingservice.dto.TicketDto
import com.github.saboteur.ticketbookingsystem.ticketbookingservice.dto.UserDto

interface TicketBookingService {

    fun getAllSessions(pageIndex: Int, pageSize: Int): List<SessionDto>
    fun getActiveSessions(pageIndex: Int, pageSize: Int): List<SessionDto>

    fun getSeats(sessionId: Long): List<SeatDto>
    fun bookTicket(clientId: Long, sessionId: Long, seatNumber: String): BookingResultDto
    fun cancelBooking(clientId: Long, sessionId: Long, seatNumber: String): BookingResultDto

    fun getTickets(clientId: Long): List<TicketDto>

    fun getAllUsers(pageIndex: Int, pageSize: Int): List<UserDto>

    fun createUser(userDto: UserDto): Long
    fun getUser(userId: Long): UserDto?
    fun updateUser(userId: Long, userDto: UserDto): Boolean?
    fun deleteUser(userId: Long): Boolean?

    fun createSession(sessionDto: SessionDto): Long
    fun getSession(sessionId: Long): SessionDto?
    fun updateSession(sessionId: Long, sessionDto: SessionDto): Boolean?
    fun deleteSession(sessionId: Long): Boolean?

}