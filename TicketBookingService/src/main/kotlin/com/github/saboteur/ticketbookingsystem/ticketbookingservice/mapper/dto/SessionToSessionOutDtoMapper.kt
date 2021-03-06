package com.github.saboteur.ticketbookingsystem.ticketbookingservice.mapper.dto

import com.github.saboteur.ticketbookingsystem.ticketbookingservice.dto.SessionOutDto
import com.github.saboteur.ticketbookingsystem.ticketbookingservice.mapper.Mapper
import com.github.saboteur.ticketbookingsystem.ticketbookingservice.mapper.common.LocalDateTimeToStringMapper
import com.github.saboteur.ticketbookingsystem.ticketbookingservice.model.Session

object SessionToSessionOutDtoMapper : Mapper<Session, SessionOutDto> {

    override fun get(from: Session): SessionOutDto =
        SessionOutDto(
            movie = MovieToMovieDtoMapper[from.movie],
            numberOfTickets = from.tickets.size,
            remainingTickets = from.tickets
                .filter { !it.isBooked }
                .count(),
            beginDate = LocalDateTimeToStringMapper[from.beginDate],
            endDate = LocalDateTimeToStringMapper[from.endDate],
            tickets = from.tickets.map(TicketToTicketDtoMapper::get)
        )

}