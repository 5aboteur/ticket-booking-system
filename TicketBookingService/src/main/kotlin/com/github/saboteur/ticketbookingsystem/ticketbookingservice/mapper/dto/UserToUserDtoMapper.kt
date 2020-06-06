package com.github.saboteur.ticketbookingsystem.ticketbookingservice.mapper.dto

import com.github.saboteur.ticketbookingsystem.ticketbookingservice.dto.UserDto
import com.github.saboteur.ticketbookingsystem.ticketbookingservice.mapper.Mapper
import com.github.saboteur.ticketbookingsystem.ticketbookingservice.model.User

object UserToUserDtoMapper : Mapper<User, UserDto> {

    override fun get(from: User): UserDto =
        UserDto(
            login = from.login,
            email = from.email,
            isAdmin = from.admin != null,
            clientProfile = from.client?.let { ClientToClientProfileDtoMapper[it] }
        )

}