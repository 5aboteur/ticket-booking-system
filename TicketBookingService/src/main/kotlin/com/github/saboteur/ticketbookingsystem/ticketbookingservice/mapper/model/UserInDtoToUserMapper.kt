package com.github.saboteur.ticketbookingsystem.ticketbookingservice.mapper.model

import com.github.saboteur.ticketbookingsystem.ticketbookingservice.domain.Category
import com.github.saboteur.ticketbookingsystem.ticketbookingservice.dto.UserInDto
import com.github.saboteur.ticketbookingsystem.ticketbookingservice.exception.UnknownCategoryException
import com.github.saboteur.ticketbookingsystem.ticketbookingservice.mapper.Mapper
import com.github.saboteur.ticketbookingsystem.ticketbookingservice.model.Client
import com.github.saboteur.ticketbookingsystem.ticketbookingservice.model.User

object UserInDtoToUserMapper : Mapper<UserInDto, User> {

    override fun get(from: UserInDto): User =
        User(
            login = from.login,
            email = from.email,
            admin = null,
            client = Client.empty
                .apply {
                    category = Category
                        .checkAndGet(from.category)
                        .takeIf { it != Category.UNKNOWN }
                        ?.ordinal
                        ?: throw UnknownCategoryException(from.category)
                }
        )

}