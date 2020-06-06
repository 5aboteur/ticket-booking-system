package com.github.saboteur.ticketbookingsystem.ticketbookingservice.dto

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty

@ApiModel(value = "Ticket")
data class TicketDto(

    @ApiModelProperty(value = "movie")
    val movie: String,

    @ApiModelProperty(value = "date")
    val date: String,

    @ApiModelProperty(value = "seat")
    val seat: String

) {
    companion object {
        val empty = TicketDto(
            movie = "",
            date = "",
            seat = ""
        )
    }
}