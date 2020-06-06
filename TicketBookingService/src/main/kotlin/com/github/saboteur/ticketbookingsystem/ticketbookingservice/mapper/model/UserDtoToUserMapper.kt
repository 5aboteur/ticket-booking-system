package com.github.saboteur.ticketbookingsystem.ticketbookingservice.mapper.model

import com.github.saboteur.ticketbookingsystem.ticketbookingservice.dto.UserDto
import com.github.saboteur.ticketbookingsystem.ticketbookingservice.mapper.Mapper
import com.github.saboteur.ticketbookingsystem.ticketbookingservice.model.Administrator
import com.github.saboteur.ticketbookingsystem.ticketbookingservice.model.Client
import com.github.saboteur.ticketbookingsystem.ticketbookingservice.model.User

object UserDtoToUserMapper : Mapper<UserDto, User> {

    override fun get(from: UserDto): User =
        User(
            login = from.login,
            email = from.email,
            admin = if (from.isAdmin) Administrator.empty else null,
            client = from.clientProfile?.let { ClientProfileDtoToClientMapper[it] }
        )

}