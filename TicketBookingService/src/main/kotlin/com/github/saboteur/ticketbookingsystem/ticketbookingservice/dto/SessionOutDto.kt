package com.github.saboteur.ticketbookingsystem.ticketbookingservice.dto

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty

@ApiModel(value = "Session")
data class SessionOutDto(

    @ApiModelProperty(value = "movie")
    val movie: MovieDto,

    @ApiModelProperty(value = "numberOfTickets")
    val numberOfTickets: Int,

    @ApiModelProperty(value = "remainingTickets")
    val remainingTickets: Int,

    @ApiModelProperty(value = "beginDate")
    val beginDate: String,

    @ApiModelProperty(value = "endDate")
    val endDate: String,

    @ApiModelProperty(value = "seats")
    val tickets: List<TicketDto>

) {
    companion object {
        val empty = SessionOutDto(
            movie = MovieDto.empty,
            numberOfTickets = 0,
            remainingTickets = 0,
            beginDate = "",
            endDate = "",
            tickets = emptyList()
        )
    }
}