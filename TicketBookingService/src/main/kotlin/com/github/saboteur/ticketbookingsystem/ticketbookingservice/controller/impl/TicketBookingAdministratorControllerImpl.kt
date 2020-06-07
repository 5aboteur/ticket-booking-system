package com.github.saboteur.ticketbookingsystem.ticketbookingservice.controller.impl

import com.github.saboteur.ticketbookingsystem.ticketbookingservice.controller.TicketBookingAdministratorControllerApi
import com.github.saboteur.ticketbookingsystem.ticketbookingservice.dto.SessionDto
import com.github.saboteur.ticketbookingsystem.ticketbookingservice.dto.UserDto
import com.github.saboteur.ticketbookingsystem.ticketbookingservice.service.TicketBookingAdministratorService
import io.swagger.annotations.Api
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController

@Api
@RestController
class TicketBookingAdministratorControllerImpl(
    private val ticketBookingAdministratorService: TicketBookingAdministratorService
) : TicketBookingAdministratorControllerApi {

    override fun getAllUsers(pageIndex: Int, pageSize: Int): ResponseEntity<List<UserDto>> {
        val result = ticketBookingAdministratorService.getAllUsers(pageIndex, pageSize)
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
        val result = ticketBookingAdministratorService.createUser(userDto)
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
        val result = ticketBookingAdministratorService.getUser(userId)
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
        val result = ticketBookingAdministratorService.updateUser(userId, userDto)
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
        val result = ticketBookingAdministratorService.deleteUser(userId)
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
        val result = ticketBookingAdministratorService.createSession(sessionDto)
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
        val result = ticketBookingAdministratorService.getSession(sessionId)
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
        val result = ticketBookingAdministratorService.updateSession(sessionId, sessionDto)
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
        val result = ticketBookingAdministratorService.deleteSession(sessionId)
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