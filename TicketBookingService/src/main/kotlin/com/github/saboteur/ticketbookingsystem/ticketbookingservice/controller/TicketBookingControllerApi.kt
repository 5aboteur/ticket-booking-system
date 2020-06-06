package com.github.saboteur.ticketbookingsystem.ticketbookingservice.controller

import com.github.saboteur.ticketbookingsystem.ticketbookingservice.dto.SessionDto
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiParam
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@Api(value = "Ticket Booking Controller API", description = "Ticket booking service controller")
@RestController
@RequestMapping("api")
interface TicketBookingControllerApi {

    /* * * Client APIs * * */

    // Sessions API

    @ApiOperation(value = "Get all sessions in the system")
    @GetMapping("/$API_VERSION/client/sessions")
    fun getAllSessions(
        @ApiParam(value = "Page index", defaultValue = "0", example = "0")
        @RequestParam(value = "pageIndex")
        pageIndex: Int,

        @ApiParam(value = "Page size", defaultValue = "10", example = "10")
        @RequestParam(value = "pageSize")
        pageSize: Int
    ): ResponseEntity<List<SessionDto>>

    @ApiOperation(value = "Get active sessions in the system")
    @GetMapping("/$API_VERSION/client/sessions/active")
    fun getActiveSessions(
        @ApiParam(value = "Page index", defaultValue = "0", example = "0")
        @RequestParam(value = "pageIndex")
        pageIndex: Int,

        @ApiParam(value = "Page size", defaultValue = "10", example = "10")
        @RequestParam(value = "pageSize")
        pageSize: Int
    ): ResponseEntity<List<SessionDto>>

    /* * * Administrator APIs * * */

    // TODO: Get all users, concrete user info, crud for session

    companion object {
        const val API_VERSION = "v1"
    }

}