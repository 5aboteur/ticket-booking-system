package com.github.saboteur.ticketbookingsystem.ticketbookingservice.mapper.dto

import com.github.saboteur.ticketbookingsystem.ticketbookingservice.dto.TicketDto
import com.github.saboteur.ticketbookingsystem.ticketbookingservice.mapper.Mapper
import com.github.saboteur.ticketbookingsystem.ticketbookingservice.mapper.common.LocalDateTimeToStringMapper
import com.github.saboteur.ticketbookingsystem.ticketbookingservice.model.Ticket

object TicketToTicketDtoMapper : Mapper<Ticket, TicketDto> {

    override fun get(from: Ticket): TicketDto =
        TicketDto(
            movie = from.movie,
            date = LocalDateTimeToStringMapper[from.date],
            seat = from.seat
        )

}