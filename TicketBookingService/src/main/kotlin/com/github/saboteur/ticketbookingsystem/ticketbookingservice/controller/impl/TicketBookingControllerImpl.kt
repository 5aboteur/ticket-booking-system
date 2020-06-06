package com.github.saboteur.ticketbookingsystem.ticketbookingservice.controller.impl

import com.github.saboteur.ticketbookingsystem.ticketbookingservice.controller.TicketBookingControllerApi
import com.github.saboteur.ticketbookingsystem.ticketbookingservice.dto.SessionDto
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

}