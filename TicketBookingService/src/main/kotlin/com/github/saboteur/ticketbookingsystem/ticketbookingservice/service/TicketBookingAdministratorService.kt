package com.github.saboteur.ticketbookingsystem.ticketbookingservice.service

import com.github.saboteur.ticketbookingsystem.ticketbookingservice.dto.RescheduleSessionDto
import com.github.saboteur.ticketbookingsystem.ticketbookingservice.dto.SessionInDto
import com.github.saboteur.ticketbookingsystem.ticketbookingservice.dto.SessionOutDto
import com.github.saboteur.ticketbookingsystem.ticketbookingservice.dto.UserInDto
import com.github.saboteur.ticketbookingsystem.ticketbookingservice.dto.UserOutDto

interface TicketBookingAdministratorService {

    fun getAllUsers(pageIndex: Int, pageSize: Int): List<UserOutDto>

    fun createUser(userInDto: UserInDto): Long
    fun getUser(userId: Long): UserOutDto?
    fun updateUser(userId: Long, userInDto: UserInDto): Boolean?
    fun deleteUser(userId: Long): Boolean?

    fun giveAdminRights(userId: Long): Boolean?
    fun removeAdminRights(userId: Long): Boolean?

    fun createSession(sessionInDto: SessionInDto): Long
    fun getSession(sessionId: Long): SessionOutDto?
    fun rescheduleSession(sessionId: Long, rescheduleSessionDto: RescheduleSessionDto): Boolean?
    fun deleteSession(sessionId: Long): Boolean?

}