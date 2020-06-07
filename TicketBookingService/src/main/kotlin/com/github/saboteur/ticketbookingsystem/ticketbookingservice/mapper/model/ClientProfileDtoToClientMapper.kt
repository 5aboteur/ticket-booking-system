package com.github.saboteur.ticketbookingsystem.ticketbookingservice.mapper.model

import com.github.saboteur.ticketbookingsystem.ticketbookingservice.domain.Category
import com.github.saboteur.ticketbookingsystem.ticketbookingservice.dto.ClientProfileDto
import com.github.saboteur.ticketbookingsystem.ticketbookingservice.exception.UnknownCategoryException
import com.github.saboteur.ticketbookingsystem.ticketbookingservice.mapper.Mapper
import com.github.saboteur.ticketbookingsystem.ticketbookingservice.model.Client

object ClientProfileDtoToClientMapper : Mapper<ClientProfileDto, Client> {

    override fun get(from: ClientProfileDto): Client =
        Client(
            category = Category
                .checkAndGet(from.category)
                .takeIf { it != Category.UNKNOWN }
                ?.ordinal
                ?: throw UnknownCategoryException("unknown category - ${from.category}"),
            tickets = from.tickets
                ?.map(TicketDtoToTicketMapper::get)
                ?.toMutableList()
                ?: mutableListOf()
        )

}