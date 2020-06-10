package com.github.saboteur.ticketbookingsystem.ticketbookingservice.controller.impl

import com.github.saboteur.ticketbookingsystem.ticketbookingservice.controller.ClientControllerApi
import com.github.saboteur.ticketbookingsystem.ticketbookingservice.dto.BookingResultDto
import com.github.saboteur.ticketbookingsystem.ticketbookingservice.dto.SeatDto
import com.github.saboteur.ticketbookingsystem.ticketbookingservice.dto.SessionOutDto
import com.github.saboteur.ticketbookingsystem.ticketbookingservice.dto.TicketDto
import com.github.saboteur.ticketbookingsystem.ticketbookingservice.service.ClientService
import io.swagger.annotations.Api
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController

@Api
@RestController
class ClientControllerImpl(
    private val clientService: ClientService
) : ClientControllerApi {

    override fun getAllSessions(pageIndex: Int, pageSize: Int): ResponseEntity<List<SessionOutDto>> {
        val result = clientService.getAllSessions(pageIndex, pageSize)
        return ResponseEntity
            .status(
                if (result.isEmpty())
                    HttpStatus.NOT_FOUND
                else
                    HttpStatus.OK
            )
            .body(result)
    }

    override fun getActiveSessions(pageIndex: Int, pageSize: Int): ResponseEntity<List<SessionOutDto>> {
        val result = clientService.getActiveSessions(pageIndex, pageSize)
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
        val result = clientService.getSeats(sessionId)
        return ResponseEntity
            .status(
                if (result.isEmpty())
                    HttpStatus.NOT_FOUND
                else
                    HttpStatus.OK
            )
            .body(result)
    }

    override fun bookTicket(userId: Long, sessionId: Long, seatNumber: String): ResponseEntity<BookingResultDto> {
        val result = clientService.bookTicket(userId, sessionId, seatNumber)
        return ResponseEntity.ok(result)
    }

    override fun cancelBooking(userId: Long, sessionId: Long, seatNumber: String): ResponseEntity<BookingResultDto> {
        val result = clientService.cancelBooking(userId, sessionId, seatNumber)
        return ResponseEntity.ok(result)
    }

    override fun getTickets(userId: Long): ResponseEntity<List<TicketDto>> {
        val result = clientService.getTickets(userId)
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