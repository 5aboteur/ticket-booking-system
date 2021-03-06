package com.github.saboteur.ticketbookingsystem.ticketbookingservice.controller

import com.github.saboteur.ticketbookingsystem.ticketbookingservice.dto.RescheduleSessionDto
import com.github.saboteur.ticketbookingsystem.ticketbookingservice.dto.SessionInDto
import com.github.saboteur.ticketbookingsystem.ticketbookingservice.dto.SessionOutDto
import com.github.saboteur.ticketbookingsystem.ticketbookingservice.dto.UserInDto
import com.github.saboteur.ticketbookingsystem.ticketbookingservice.dto.UserOutDto
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

@Api(value = "Ticket Booking Administrator Controller API", description = "Service controller for administrators")
@RestController
@RequestMapping(value = ["api"])
interface AdministratorControllerApi {

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
    ): ResponseEntity<List<UserOutDto>>

    // User API

    @ApiOperation(value = "Create a new user")
    @PostMapping(value = ["/$API_VERSION/administrator/user"])
    fun createUser(
        @ApiParam(value = "User data")
        @RequestBody(required = true)
        userInDto: UserInDto
    ): ResponseEntity<Long>

    @ApiOperation(value = "Get a user by ID")
    @GetMapping(value = ["/$API_VERSION/administrator/user/{userId}"])
    fun getUser(
        @ApiParam(value = "User ID", required = true, defaultValue = "666", example = "666")
        @PathVariable(value = "userId")
        userId: Long
    ): ResponseEntity<UserOutDto?>

    @ApiOperation(value = "Update an existing user")
    @PutMapping(value = ["/$API_VERSION/administrator/user/{userId}"])
    fun updateUser(
        @ApiParam(value = "User ID", required = true, defaultValue = "666", example = "666")
        @PathVariable(value = "userId")
        userId: Long,

        @ApiParam(value = "User data")
        @RequestBody(required = true)
        userInDto: UserInDto
    ): ResponseEntity<Boolean>

    @ApiOperation(value = "Delete an existing user")
    @DeleteMapping(value = ["/$API_VERSION/administrator/user/{userId}"])
    fun deleteUser(
        @ApiParam(value = "User ID", required = true, defaultValue = "666", example = "666")
        @PathVariable(value = "userId")
        userId: Long
    ): ResponseEntity<Boolean>

    @ApiOperation(value = "Give user administrator rights")
    @PutMapping(value = ["/$API_VERSION/administrator/user/{userId}/admin-rights"])
    fun giveAdminRights(
        @ApiParam(value = "User ID", required = true, defaultValue = "666", example = "666")
        @PathVariable(value = "userId")
        userId: Long
    ): ResponseEntity<Boolean>

    @ApiOperation(value = "Remove administrator rights from user")
    @DeleteMapping(value = ["/$API_VERSION/administrator/user/{userId}/admin-rights"])
    fun removeAdminRights(
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
        sessionInDto: SessionInDto
    ): ResponseEntity<Long>

    @ApiOperation(value = "Get a session by ID")
    @GetMapping(value = ["/$API_VERSION/administrator/session/{sessionId}"])
    fun getSession(
        @ApiParam(value = "Session ID", required = true, defaultValue = "666", example = "666")
        @PathVariable(value = "sessionId")
        sessionId: Long
    ): ResponseEntity<SessionOutDto?>

    @ApiOperation(value = "Reschedule an existing session")
    @PutMapping(value = ["/$API_VERSION/administrator/session/{sessionId}"])
    fun rescheduleSession(
        @ApiParam(value = "Session ID", required = true, defaultValue = "666", example = "666")
        @PathVariable(value = "sessionId")
        sessionId: Long,

        @ApiParam(value = "Session schedule data")
        @RequestBody(required = true)
        rescheduleSessionDto: RescheduleSessionDto
    ): ResponseEntity<Boolean>

    @ApiOperation(value = "Delete an existing session")
    @DeleteMapping(value = ["/$API_VERSION/administrator/session/{sessionId}"])
    fun deleteSession(
        @ApiParam(value = "Session ID", required = true, defaultValue = "666", example = "666")
        @PathVariable(value = "sessionId")
        sessionId: Long
    ): ResponseEntity<Boolean>

    // Miscellaneous

    @ApiOperation(value = "Get the social benefits rule status")
    @GetMapping(value = ["/$API_VERSION/administrator/miscellaneous/social-benefits"])
    fun getSocialBenefitsRuleStatus(): ResponseEntity<String>

    @ApiOperation(value = "Change the social benefits rule status")
    @PutMapping(value = ["/$API_VERSION/administrator/miscellaneous/social-benefits"])
    fun changeSocialBenefitsRuleStatus(
        @ApiParam(value = "New status", required = true)
        @RequestParam(value = "status")
        status: Boolean
    ): ResponseEntity<String>

    companion object {
        const val API_VERSION = "v1"
    }

}