package com.github.saboteur.ticketbookingsystem.ticketbookingservice.mapper.dto

import com.github.saboteur.ticketbookingsystem.ticketbookingservice.dto.SeatDto
import com.github.saboteur.ticketbookingsystem.ticketbookingservice.mapper.Mapper
import com.github.saboteur.ticketbookingsystem.ticketbookingservice.model.Seat

object SeatToSeatDtoMapper : Mapper<Seat, SeatDto> {

    override fun get(from: Seat): SeatDto =
        SeatDto(
            number = from.number,
            isBooked = from.isBooked
        )

}