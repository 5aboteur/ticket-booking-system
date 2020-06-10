package com.github.saboteur.ticketbookingsystem.ticketbookingservice.controller

import com.github.saboteur.ticketbookingsystem.ticketbookingservice.dto.BookingResultDto
import com.github.saboteur.ticketbookingsystem.ticketbookingservice.dto.SeatDto
import com.github.saboteur.ticketbookingsystem.ticketbookingservice.dto.SessionOutDto
import com.github.saboteur.ticketbookingsystem.ticketbookingservice.dto.TicketDto
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiParam
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@Api(value = "Ticket Booking Client Controller API", description = "Service controller for clients")
@RestController
@RequestMapping(value = ["api"])
interface ClientControllerApi {

    /* * * Client APIs * * */

    // Sessions API

    @ApiOperation(value = "Get all sessions in the system")
    @GetMapping(value = ["/$API_VERSION/client/sessions"])
    fun getAllSessions(
        @ApiParam(value = "Page index", defaultValue = "0", example = "0")
        @RequestParam(value = "pageIndex")
        pageIndex: Int,

        @ApiParam(value = "Page size", defaultValue = "10", example = "10")
        @RequestParam(value = "pageSize")
        pageSize: Int
    ): ResponseEntity<List<SessionOutDto>>

    @ApiOperation(value = "Get active sessions in the system")
    @GetMapping(value = ["/$API_VERSION/client/sessions/active"])
    fun getActiveSessions(
        @ApiParam(value = "Page index", defaultValue = "0", example = "0")
        @RequestParam(value = "pageIndex")
        pageIndex: Int,

        @ApiParam(value = "Page size", defaultValue = "10", example = "10")
        @RequestParam(value = "pageSize")
        pageSize: Int
    ): ResponseEntity<List<SessionOutDto>>

    // Session API

    @ApiOperation(value = "Get seats information")
    @GetMapping(value = ["/$API_VERSION/client/session/{sessionId}/seats"])
    fun getSeats(
        @ApiParam(value = "Session ID", required = true, defaultValue = "666", example = "666")
        @PathVariable(value = "sessionId")
        sessionId: Long
    ): ResponseEntity<List<SeatDto>>

    @ApiOperation(value = "Book a ticket")
    @PostMapping(value = ["/$API_VERSION/client/{clientId}/session/{sessionId}/book"])
    fun bookTicket(
        @ApiParam(value = "Client ID", required = true, defaultValue = "666", example = "666")
        @PathVariable(value = "clientId")
        clientId: Long,

        @ApiParam(value = "Session ID", required = true, defaultValue = "666", example = "666")
        @PathVariable(value = "sessionId")
        sessionId: Long,

        @ApiParam(value = "Seat number", required = true, example = "1F")
        @RequestParam(value = "seatNumber")
        seatNumber: String
    ): ResponseEntity<BookingResultDto>

    @ApiOperation(value = "Cancel booking")
    @DeleteMapping(value = ["/$API_VERSION/client/{clientId}/session/{sessionId}/book"])
    fun cancelBooking(
        @ApiParam(value = "Client ID", required = true, defaultValue = "666", example = "666")
        @PathVariable(value = "clientId")
        clientId: Long,

        @ApiParam(value = "Session ID", required = true, defaultValue = "666", example = "666")
        @PathVariable(value = "sessionId")
        sessionId: Long,

        @ApiParam(value = "Seat number", required = true, example = "1F")
        @RequestParam(value = "seatNumber")
        seatNumber: String
    ): ResponseEntity<BookingResultDto>

    // Client API

    @ApiOperation(value = "Get client tickets")
    @GetMapping(value = ["/$API_VERSION/client/{clientId}/tickets"])
    fun getTickets(
        @ApiParam(value = "Client ID", required = true, defaultValue = "666", example = "666")
        @PathVariable(value = "clientId")
        clientId: Long
    ): ResponseEntity<List<TicketDto>>

    companion object {
        const val API_VERSION = "v1"
    }

}