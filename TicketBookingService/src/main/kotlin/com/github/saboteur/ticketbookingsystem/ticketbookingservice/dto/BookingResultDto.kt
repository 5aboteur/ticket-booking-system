package com.github.saboteur.ticketbookingsystem.ticketbookingservice.dto

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty

@ApiModel(value = "BookingResult")
data class BookingResultDto(

    @ApiModelProperty(value = "clientId")
    val clientId: Long,

    @ApiModelProperty(value = "sessionId")
    val sessionId: Long,

    @ApiModelProperty(value = "ticket")
    val ticket: TicketDto?,

    @ApiModelProperty(value = "operation")
    val operation: String

) {
    companion object {
        val empty = BookingResultDto(
            clientId = -1L,
            sessionId = -1L,
            ticket = null,
            operation = ""
        )
    }
}