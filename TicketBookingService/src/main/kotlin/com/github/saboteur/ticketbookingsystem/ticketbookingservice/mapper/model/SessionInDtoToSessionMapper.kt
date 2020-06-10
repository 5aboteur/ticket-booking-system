package com.github.saboteur.ticketbookingsystem.ticketbookingservice.mapper.model

import com.github.saboteur.ticketbookingsystem.ticketbookingservice.dto.SessionInDto
import com.github.saboteur.ticketbookingsystem.ticketbookingservice.mapper.Mapper
import com.github.saboteur.ticketbookingsystem.ticketbookingservice.mapper.common.StringToLocalDateTimeMapper
import com.github.saboteur.ticketbookingsystem.ticketbookingservice.model.Session

object SessionInDtoToSessionMapper : Mapper<SessionInDto, Session> {

    override fun get(from: SessionInDto): Session =
        Session(
            movie = MovieDtoToMovieMapper[from.movie],
            createdDate = StringToLocalDateTimeMapper[from.createdDate],
            beginDate = StringToLocalDateTimeMapper[from.beginDate],
            endDate = StringToLocalDateTimeMapper[from.endDate],
            tickets = from.tickets.map(TicketDtoToTicketMapper::get)
        )

}