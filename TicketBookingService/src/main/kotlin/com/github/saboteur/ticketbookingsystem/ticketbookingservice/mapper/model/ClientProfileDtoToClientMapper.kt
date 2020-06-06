package com.github.saboteur.ticketbookingsystem.ticketbookingservice.mapper.model

import com.github.saboteur.ticketbookingsystem.ticketbookingservice.domain.Category
import com.github.saboteur.ticketbookingsystem.ticketbookingservice.dto.ClientProfileDto
import com.github.saboteur.ticketbookingsystem.ticketbookingservice.mapper.Mapper
import com.github.saboteur.ticketbookingsystem.ticketbookingservice.model.Client

object ClientProfileDtoToClientMapper : Mapper<ClientProfileDto, Client> {

    override fun get(from: ClientProfileDto): Client =
        Client(
            category = Category.valueOf(from.category).ordinal,
            sessions = from.sessions.map(SessionDtoToSessionMapper::get)
        )

}