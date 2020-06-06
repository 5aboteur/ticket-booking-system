package com.github.saboteur.ticketbookingsystem.ticketbookingservice.controller.impl

import com.github.saboteur.ticketbookingsystem.ticketbookingservice.controller.TicketBookingControllerApi
import com.github.saboteur.ticketbookingsystem.ticketbookingservice.dto.BookingResultDto
import com.github.saboteur.ticketbookingsystem.ticketbookingservice.dto.SeatDto
import com.github.saboteur.ticketbookingsystem.ticketbookingservice.dto.SessionDto
import com.github.saboteur.ticketbookingsystem.ticketbookingservice.dto.TicketDto
import com.github.saboteur.ticketbookingsystem.ticketbookingservice.dto.UserDto
import com.github.saboteur.ticketbookingsystem.ticketbookingservice.service.TicketBookingService
import io.swagger.annotations.Api
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController

@Api
@RestController
class TicketBookingControllerImpl(
    private val ticketBookingService: TicketBookingService
) : TicketBookingControllerApi {

    override fun getAllSessions(pageIndex: Int, pageSize: Int): ResponseEntity<List<SessionDto>> {
        val result = ticketBookingService.getAllSessions(pageIndex, pageSize)
        return ResponseEntity
            .status(
                if (result.isEmpty())
                    HttpStatus.NOT_FOUND
                else
                    HttpStatus.OK
            )
            .body(result)
    }

    override fun getActiveSessions(pageIndex: Int, pageSize: Int): ResponseEntity<List<SessionDto>> {
        val result = ticketBookingService.getActiveSessions(pageIndex, pageSize)
        return ResponseEntity
            .status(
                if (result.isEmpty())
                    HttpStatus.NOT_FOUND
                else
                    HttpStatus.OK
            )
            .body(result)
    }

    override fun getSeats(sessionId: Long): ResponseEntity<List<SeatDto>> {
        val result = ticketBookingService.getSeats(sessionId)
        return ResponseEntity
            .status(
                if (result.isEmpty())
                    HttpStatus.NOT_FOUND
                else
                    HttpStatus.OK
            )
            .body(result)
    }

    override fun bookTicket(clientId: Long, sessionId: Long, seatNumber: String): ResponseEntity<BookingResultDto> {
        val result = ticketBookingService.bookTicket(clientId, sessionId, seatNumber)
        return ResponseEntity.ok(result)
    }

    override fun cancelBooking(clientId: Long, sessionId: Long, seatNumber: String): ResponseEntity<BookingResultDto> {
        val result = ticketBookingService.cancelBooking(clientId, sessionId, seatNumber)
        return ResponseEntity.ok(result)
    }

    override fun getTickets(clientId: Long): ResponseEntity<List<TicketDto>> {
        val result = ticketBookingService.getTickets(clientId)
        return ResponseEntity
            .status(
                if (result.isEmpty())
                    HttpStatus.NOT_FOUND
                else
                    HttpStatus.OK
            )
            .body(result)
    }

    override fun getAllUsers(pageIndex: Int, pageSize: Int): ResponseEntity<List<UserDto>> {
        val result = ticketBookingService.getAllUsers(pageIndex, pageSize)
        return ResponseEntity
            .status(
                if (result.isEmpty())
                    HttpStatus.NOT_FOUND
                else
                    HttpStatus.OK
            )
            .body(result)
    }

    override fun createUser(userDto: UserDto): ResponseEntity<Long> {
        val result = ticketBookingService.createUser(userDto)
        return ResponseEntity
            .status(
                if (result == -1L || result == 0L)
                    HttpStatus.BAD_REQUEST
                else
                    HttpStatus.CREATED
            )
            .body(result)
    }

    override fun getUser(userId: Long): ResponseEntity<UserDto?> {
        val result = ticketBookingService.getUser(userId)
        return ResponseEntity
            .status(
                if (result == null)
                    HttpStatus.NOT_FOUND
                else
                    HttpStatus.OK
            )
            .body(result)
    }

    override fun updateUser(userId: Long, userDto: UserDto): ResponseEntity<Boolean> {
        val result = ticketBookingService.updateUser(userId, userDto)
        return ResponseEntity
            .status(
                when (result) {
                    true -> HttpStatus.OK
                    false -> HttpStatus.BAD_REQUEST
                    null -> HttpStatus.NOT_FOUND
                }
            )
            .body(result)
    }

    override fun deleteUser(userId: Long): ResponseEntity<Boolean> {
        val result = ticketBookingService.deleteUser(userId)
        return ResponseEntity
            .status(
                if (result == null)
                    HttpStatus.NOT_FOUND
                else
                    HttpStatus.OK
            )
            .body(result)
    }

    override fun createSession(sessionDto: SessionDto): ResponseEntity<Long> {
        val result = ticketBookingService.createSession(sessionDto)
        return ResponseEntity
            .status(
                if (result == -1L || result == 0L)
                    HttpStatus.BAD_REQUEST
                else
                    HttpStatus.CREATED
            )
            .body(result)
    }

    override fun getSession(sessionId: Long): ResponseEntity<SessionDto?> {
        val result = ticketBookingService.getSession(sessionId)
        return ResponseEntity
            .status(
                if (result == null)
                    HttpStatus.NOT_FOUND
                else
                    HttpStatus.OK
            )
            .body(result)
    }

    override fun updateSession(sessionId: Long, sessionDto: SessionDto): ResponseEntity<Boolean> {
        val result = ticketBookingService.updateSession(sessionId, sessionDto)
        return ResponseEntity
            .status(
                when (result) {
                    true -> HttpStatus.OK
                    false -> HttpStatus.BAD_REQUEST
                    null -> HttpStatus.NOT_FOUND
                }
            )
            .body(result)
    }

    override fun deleteSession(sessionId: Long): ResponseEntity<Boolean> {
        val result = ticketBookingService.deleteSession(sessionId)
        return ResponseEntity
            .status(
                if (result == null)
                    HttpStatus.NOT_FOUND
                else
                    HttpStatus.OK
            )
            .body(result)
    }

}