package com.github.saboteur.ticketbookingsystem.ticketbookingservice.dto

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty

@ApiModel(value = "Seat")
data class SeatDto(

    @ApiModelProperty(value = "number")
    val number: String,

    @ApiModelProperty(value = "isBooked")
    val isBooked: Boolean

) {
    companion object {
        val empty = SeatDto(
            number = "",
            isBooked = false
        )
    }
}