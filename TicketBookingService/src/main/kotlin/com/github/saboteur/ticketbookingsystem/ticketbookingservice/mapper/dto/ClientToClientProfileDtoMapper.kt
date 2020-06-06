package com.github.saboteur.ticketbookingsystem.ticketbookingservice.mapper.dto

import com.github.saboteur.ticketbookingsystem.ticketbookingservice.domain.Category
import com.github.saboteur.ticketbookingsystem.ticketbookingservice.dto.ClientProfileDto
import com.github.saboteur.ticketbookingsystem.ticketbookingservice.mapper.Mapper
import com.github.saboteur.ticketbookingsystem.ticketbookingservice.model.Client

object ClientToClientProfileDtoMapper : Mapper<Client, ClientProfileDto> {

    override fun get(from: Client): ClientProfileDto =
        ClientProfileDto(
            category = Category
                .values()[from.category]
                .categoryName,
            tickets = from.tickets.map(TicketToTicketDtoMapper::get)
        )

}