package com.github.saboteur.ticketbookingsystem.ticketbookingservice.mapper.model

import com.github.saboteur.ticketbookingsystem.ticketbookingservice.dto.TicketDto
import com.github.saboteur.ticketbookingsystem.ticketbookingservice.mapper.Mapper
import com.github.saboteur.ticketbookingsystem.ticketbookingservice.mapper.common.StringToLocalDateTimeMapper
import com.github.saboteur.ticketbookingsystem.ticketbookingservice.model.Ticket

object TicketDtoToTicketMapper : Mapper<TicketDto, Ticket> {

    override fun get(from: TicketDto): Ticket =
        Ticket(
            price = from.price,
            discountPrice = from.discountPrice,
            movie = from.movie,
            date = StringToLocalDateTimeMapper[from.date],
            seat = from.seat
        )

}