package com.github.saboteur.ticketbookingsystem.ticketbookingservice.mapper.model

import com.github.saboteur.ticketbookingsystem.ticketbookingservice.dto.SessionDto
import com.github.saboteur.ticketbookingsystem.ticketbookingservice.mapper.Mapper
import com.github.saboteur.ticketbookingsystem.ticketbookingservice.mapper.common.StringToLocalDateTimeMapper
import com.github.saboteur.ticketbookingsystem.ticketbookingservice.model.Session

object SessionDtoToSessionMapper : Mapper<SessionDto, Session> {

    override fun get(from: SessionDto): Session =
        Session(
            movie = MovieDtoToMovieMapper[from.movie],
            beginDate = StringToLocalDateTimeMapper[from.beginDate],
            endDate = StringToLocalDateTimeMapper[from.endDate],
            tickets = from.tickets.map(TicketDtoToTicketMapper::get)
        )

}