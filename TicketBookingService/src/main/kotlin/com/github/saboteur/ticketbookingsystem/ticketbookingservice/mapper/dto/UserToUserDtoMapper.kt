package com.github.saboteur.ticketbookingsystem.ticketbookingservice.mapper.dto

import com.github.saboteur.ticketbookingsystem.ticketbookingservice.dto.UserOutDto
import com.github.saboteur.ticketbookingsystem.ticketbookingservice.mapper.Mapper
import com.github.saboteur.ticketbookingsystem.ticketbookingservice.model.User

object UserToUserDtoMapper : Mapper<User, UserOutDto> {

    override fun get(from: User): UserOutDto =
        UserOutDto(
            login = from.login,
            email = from.email,
            isAdmin = from.isAdmin,
            clientProfile = from.client?.let { ClientToClientProfileDtoMapper[it] }
        )

}