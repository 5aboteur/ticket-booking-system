package com.github.saboteur.ticketbookingsystem.ticketbookingservice.mapper.dto

import com.github.saboteur.ticketbookingsystem.ticketbookingservice.dto.BookingResultDto
import com.github.saboteur.ticketbookingsystem.ticketbookingservice.dto.TicketDto
import com.github.saboteur.ticketbookingsystem.ticketbookingservice.mapper.Mapper
import com.github.saboteur.ticketbookingsystem.ticketbookingservice.model.booking.BookingResult

object BookingResultToBookingResultDtoMapper : Mapper<BookingResult, BookingResultDto> {

    override fun get(from: BookingResult): BookingResultDto =
        BookingResultDto(
            clientId = from.clientId,
            sessionId = from.sessionId,
            ticket = from.bookedTicket
                ?.let { BookedTicketToTicketDtoMapper[it] }
                ?: TicketDto.empty,
            operation = from.operation.operationName,
            resultMsg = from.resultMsg
        )

}