package com.github.saboteur.ticketbookingsystem.ticketbookingservice.mapper.dto

import com.github.saboteur.ticketbookingsystem.ticketbookingservice.dto.TicketDto
import com.github.saboteur.ticketbookingsystem.ticketbookingservice.mapper.Mapper
import com.github.saboteur.ticketbookingsystem.ticketbookingservice.mapper.common.LocalDateTimeToStringMapper
import com.github.saboteur.ticketbookingsystem.ticketbookingservice.model.booking.BookedTicket

object BookedTicketToTicketDtoMapper : Mapper<BookedTicket, TicketDto> {

    override fun get(from: BookedTicket): TicketDto =
        TicketDto(
            price = from.price ?: 0.0,
            discountPrice = from.discountPrice ?: 0.0,
            movie = from.movie ?: "",
            date = from.date
                ?.let { LocalDateTimeToStringMapper[it] }
                ?: "",
            seat = from.seat ?: ""
        )

}