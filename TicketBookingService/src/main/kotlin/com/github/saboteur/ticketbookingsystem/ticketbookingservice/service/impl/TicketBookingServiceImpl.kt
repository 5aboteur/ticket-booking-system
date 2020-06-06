package com.github.saboteur.ticketbookingsystem.ticketbookingservice.service.impl

import com.github.saboteur.ticketbookingsystem.ticketbookingservice.dto.SessionDto
import com.github.saboteur.ticketbookingsystem.ticketbookingservice.mapper.dto.SessionToSessionDtoMapper
import com.github.saboteur.ticketbookingsystem.ticketbookingservice.repository.SessionRepository
import com.github.saboteur.ticketbookingsystem.ticketbookingservice.service.TicketBookingService
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service

@Service
class TicketBookingServiceImpl(
    private val sessionRepository: SessionRepository
) : TicketBookingService {

    override fun getAllSessions(pageIndex: Int, pageSize: Int): List<SessionDto> =
        sessionRepository
            .findAll(PageRequest.of(pageIndex, pageSize))
            .map(SessionToSessionDtoMapper::get)
            .toList()

    override fun getActiveSessions(pageIndex: Int, pageSize: Int): List<SessionDto> =
        sessionRepository
            .findAll(PageRequest.of(pageIndex, pageSize))
            .map(SessionToSessionDtoMapper::get)
            .toList()

}