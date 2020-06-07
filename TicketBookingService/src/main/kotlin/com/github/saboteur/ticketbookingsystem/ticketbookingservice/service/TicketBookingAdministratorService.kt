package com.github.saboteur.ticketbookingsystem.ticketbookingservice.service

import com.github.saboteur.ticketbookingsystem.ticketbookingservice.dto.SessionDto
import com.github.saboteur.ticketbookingsystem.ticketbookingservice.dto.UserDto

interface TicketBookingAdministratorService {

    fun getAllUsers(pageIndex: Int, pageSize: Int): List<UserDto>

    fun createUser(userDto: UserDto): Long
    fun getUser(userId: Long): UserDto?
    fun updateUser(userId: Long, userDto: UserDto): Boolean?
    fun deleteUser(userId: Long): Boolean?

    fun createSession(sessionDto: SessionDto): Long
    fun getSession(sessionId: Long): SessionDto?
    fun updateSession(sessionId: Long, sessionDto: SessionDto): Boolean?
    fun deleteSession(sessionId: Long): Boolean?

}