package com.github.saboteur.ticketbookingsystem.ticketbookingservice.controller

import com.github.saboteur.ticketbookingsystem.ticketbookingservice.dto.BookingResultDto
import com.github.saboteur.ticketbookingsystem.ticketbookingservice.dto.SeatDto
import com.github.saboteur.ticketbookingsystem.ticketbookingservice.dto.SessionDto
import com.github.saboteur.ticketbookingsystem.ticketbookingservice.dto.TicketDto
import com.github.saboteur.ticketbookingsystem.ticketbookingservice.dto.UserDto
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiParam
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@Api(value = "Ticket Booking Controller API", description = "Ticket booking service controller")
@RestController
@RequestMapping(value = ["api"])
interface TicketBookingControllerApi {

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
    ): ResponseEntity<List<SessionDto>>

    @ApiOperation(value = "Get active sessions in the system")
    @GetMapping(value = ["/$API_VERSION/client/sessions/active"])
    fun getActiveSessions(
        @ApiParam(value = "Page index", defaultValue = "0", example = "0")
        @RequestParam(value = "pageIndex")
        pageIndex: Int,

        @ApiParam(value = "Page size", defaultValue = "10", example = "10")
        @RequestParam(value = "pageSize")
        pageSize: Int
    ): ResponseEntity<List<SessionDto>>

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

        @ApiParam(value = "Seat number", example = "1F")
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

        @ApiParam(value = "Seat number", example = "1F")
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

    /* * * Administrator APIs * * */

    // Users API

    @ApiOperation(value = "Get all users")
    @GetMapping(value = ["/$API_VERSION/administrator/users"])
    fun getAllUsers(
        @ApiParam(value = "Page index", defaultValue = "0", example = "0")
        @RequestParam(value = "pageIndex")
        pageIndex: Int,

        @ApiParam(value = "Page size", defaultValue = "10", example = "10")
        @RequestParam(value = "pageSize")
        pageSize: Int
    ): ResponseEntity<List<UserDto>>

    // User API

    @ApiOperation(value = "Create a new user")
    @PostMapping(value = ["/$API_VERSION/administrator/user"])
    fun createUser(
        @ApiParam(value = "User data")
        @RequestBody(required = true)
        userDto: UserDto
    ): ResponseEntity<Long>

    @ApiOperation(value = "Get a user by ID")
    @GetMapping(value = ["/$API_VERSION/administrator/user/{userId}"])
    fun getUser(
        @ApiParam(value = "User ID", required = true, defaultValue = "666", example = "666")
        @PathVariable(value = "userId")
        userId: Long
    ): ResponseEntity<UserDto?>

    @ApiOperation(value = "Update an existing user")
    @PutMapping(value = ["/$API_VERSION/administrator/user/{userId}"])
    fun updateUser(
        @ApiParam(value = "User ID", required = true, defaultValue = "666", example = "666")
        @PathVariable(value = "userId")
        userId: Long,

        @ApiParam(value = "User data")
        @RequestBody(required = true)
        userDto: UserDto
    ): ResponseEntity<Boolean>

    @ApiOperation(value = "Delete an existing user")
    @DeleteMapping(value = ["/$API_VERSION/administrator/user/{userId}"])
    fun deleteUser(
        @ApiParam(value = "User ID", required = true, defaultValue = "666", example = "666")
        @PathVariable(value = "userId")
        userId: Long
    ): ResponseEntity<Boolean>

    // Session API

    @ApiOperation(value = "Create a new session")
    @PostMapping(value = ["/$API_VERSION/administrator/session"])
    fun createSession(
        @ApiParam(value = "Session data")
        @RequestBody(required = true)
        sessionDto: SessionDto
    ): ResponseEntity<Long>

    @ApiOperation(value = "Get a session by ID")
    @GetMapping(value = ["/$API_VERSION/administrator/session/{sessionId}"])
    fun getSession(
        @ApiParam(value = "Session ID", required = true, defaultValue = "666", example = "666")
        @PathVariable(value = "sessionId")
        sessionId: Long
    ): ResponseEntity<SessionDto?>

    @ApiOperation(value = "Update an existing session")
    @PutMapping(value = ["/$API_VERSION/administrator/session/{sessionId}"])
    fun updateSession(
        @ApiParam(value = "Session ID", required = true, defaultValue = "666", example = "666")
        @PathVariable(value = "sessionId")
        sessionId: Long,

        @ApiParam(value = "Session data")
        @RequestBody(required = true)
        sessionDto: SessionDto
    ): ResponseEntity<Boolean>

    @ApiOperation(value = "Delete an existing session")
    @DeleteMapping(value = ["/$API_VERSION/administrator/session/{sessionId}"])
    fun deleteSession(
        @ApiParam(value = "Session ID", required = true, defaultValue = "666", example = "666")
        @PathVariable(value = "sessionId")
        sessionId: Long
    ): ResponseEntity<Boolean>

    companion object {
        const val API_VERSION = "v1"
    }

}