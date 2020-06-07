package com.github.saboteur.ticketbookingsystem.ticketbookingservice.controller.impl

import com.github.saboteur.ticketbookingsystem.ticketbookingservice.controller.TicketBookingClientControllerApi
import com.github.saboteur.ticketbookingsystem.ticketbookingservice.dto.BookingResultDto
import com.github.saboteur.ticketbookingsystem.ticketbookingservice.dto.SeatDto
import com.github.saboteur.ticketbookingsystem.ticketbookingservice.dto.SessionDto
import com.github.saboteur.ticketbookingsystem.ticketbookingservice.dto.TicketDto
import com.github.saboteur.ticketbookingsystem.ticketbookingservice.service.TicketBookingClientService
import io.swagger.annotations.Api
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController

@Api
@RestController
class TicketBookingClientControllerImpl(
    private val ticketBookingClientService: TicketBookingClientService
) : TicketBookingClientControllerApi {

    override fun getAllSessions(pageIndex: Int, pageSize: Int): ResponseEntity<List<SessionDto>> {
        val result = ticketBookingClientService.getAllSessions(pageIndex, pageSize)
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
        val result = ticketBookingClientService.getActiveSessions(pageIndex, pageSize)
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
        val result = ticketBookingClientService.getSeats(sessionId)
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
        val result = ticketBookingClientService.bookTicket(clientId, sessionId, seatNumber)
        return ResponseEntity.ok(result)
    }

    override fun cancelBooking(clientId: Long, sessionId: Long, seatNumber: String): ResponseEntity<BookingResultDto> {
        val result = ticketBookingClientService.cancelBooking(clientId, sessionId, seatNumber)
        return ResponseEntity.ok(result)
    }

    override fun getTickets(clientId: Long): ResponseEntity<List<TicketDto>> {
        val result = ticketBookingClientService.getTickets(clientId)
        return ResponseEntity
            .status(
                if (result.isEmpty())
                    HttpStatus.NOT_FOUND
                else
                    HttpStatus.OK
            )
            .body(result)
    }

}