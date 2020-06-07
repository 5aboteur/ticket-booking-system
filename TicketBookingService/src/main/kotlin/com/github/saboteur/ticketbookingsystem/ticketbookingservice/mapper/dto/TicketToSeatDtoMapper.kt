package com.github.saboteur.ticketbookingsystem.ticketbookingservice.mapper.dto

import com.github.saboteur.ticketbookingsystem.ticketbookingservice.dto.SeatDto
import com.github.saboteur.ticketbookingsystem.ticketbookingservice.mapper.Mapper
import com.github.saboteur.ticketbookingsystem.ticketbookingservice.model.Ticket

object TicketToSeatDtoMapper : Mapper<Ticket, SeatDto> {

    override fun get(from: Ticket): SeatDto =
        SeatDto(
            number = from.seat,
            isBooked = from.isBooked
        )

}