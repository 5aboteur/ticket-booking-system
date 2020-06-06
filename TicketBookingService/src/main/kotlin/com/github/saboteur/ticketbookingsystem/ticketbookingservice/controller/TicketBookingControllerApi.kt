package com.github.saboteur.ticketbookingsystem.ticketbookingservice.controller

import com.github.saboteur.ticketbookingsystem.ticketbookingservice.dto.SessionDto
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

    companion object {
        const val API_VERSION = "v1"
    }

}