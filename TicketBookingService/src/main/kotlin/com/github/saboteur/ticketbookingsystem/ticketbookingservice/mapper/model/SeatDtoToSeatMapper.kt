package com.github.saboteur.ticketbookingsystem.ticketbookingservice.mapper.model

import com.github.saboteur.ticketbookingsystem.ticketbookingservice.dto.SeatDto
import com.github.saboteur.ticketbookingsystem.ticketbookingservice.mapper.Mapper
import com.github.saboteur.ticketbookingsystem.ticketbookingservice.model.Seat
import com.github.saboteur.ticketbookingsystem.ticketbookingservice.model.Session

object SeatDtoToSeatMapper : Mapper<SeatDto, Seat> {

    override fun get(from: SeatDto): Seat =
        Seat(
            session = Session.empty,
            number = from.number,
            isBooked = from.isBooked
        )

}