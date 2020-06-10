package com.github.saboteur.ticketbookingsystem.ticketbookingservice.dto

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty

@ApiModel(value = "User")
data class UserOutDto(

    @ApiModelProperty(value = "login")
    val login: String,

    @ApiModelProperty(value = "email")
    val email: String,

    @ApiModelProperty(value = "isAdmin")
    val isAdmin: Boolean,

    @ApiModelProperty(value = "category")
    val category: String,

    @ApiModelProperty(value = "tickets")
    val tickets: List<TicketDto>?

) {
    companion object {
        val empty = UserOutDto(
            login = "",
            email = "",
            isAdmin = false,
            category = "",
            tickets = null
        )
    }
}