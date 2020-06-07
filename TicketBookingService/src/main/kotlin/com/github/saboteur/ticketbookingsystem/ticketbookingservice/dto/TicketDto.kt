package com.github.saboteur.ticketbookingsystem.ticketbookingservice.dto

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty

@ApiModel(value = "Ticket")
data class TicketDto(

    @ApiModelProperty(value = "price")
    val price: Double,

    @ApiModelProperty(value = "discountPrice")
    val discountPrice: Double,

    @ApiModelProperty(value = "movie")
    val movie: String,

    @ApiModelProperty(value = "date")
    val date: String,

    @ApiModelProperty(value = "seat")
    val seat: String

) {
    companion object {
        val empty = TicketDto(
            price = 0.0,
            discountPrice = 0.0,
            movie = "",
            date = "",
            seat = ""
        )
    }
}