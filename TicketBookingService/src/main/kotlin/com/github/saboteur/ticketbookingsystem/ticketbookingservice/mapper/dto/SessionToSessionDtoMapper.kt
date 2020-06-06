package com.github.saboteur.ticketbookingsystem.ticketbookingservice.mapper.dto

import com.github.saboteur.ticketbookingsystem.ticketbookingservice.dto.SessionDto
import com.github.saboteur.ticketbookingsystem.ticketbookingservice.mapper.Mapper
import com.github.saboteur.ticketbookingsystem.ticketbookingservice.mapper.common.LocalDateTimeToStringMapper
import com.github.saboteur.ticketbookingsystem.ticketbookingservice.model.Session

object SessionToSessionDtoMapper : Mapper<Session, SessionDto> {

    override fun get(from: Session): SessionDto =
        SessionDto(
            movieDto = MovieToMovieDtoMapper[from.movie],
            numberOfTickets = from.numberOfTickets,
            remainingTickets = from.remainingTickets,
            beginDate = LocalDateTimeToStringMapper[from.beginDate],
            endDate = LocalDateTimeToStringMapper[from.endDate],
            seats = from.seats.map(SeatToSeatDtoMapper::get)
        )

}