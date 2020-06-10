package com.github.saboteur.ticketbookingsystem.ticketbookingservice.dto

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty

@ApiModel(value = "Session")
data class SessionInDto(

    @ApiModelProperty(value = "movie")
    val movie: MovieDto,

    @ApiModelProperty(value = "createdDate")
    val createdDate: String,

    @ApiModelProperty(value = "beginDate")
    val beginDate: String,

    @ApiModelProperty(value = "endDate")
    val endDate: String,

    @ApiModelProperty(value = "seats")
    val tickets: List<TicketDto>

) {
    companion object {
        val empty = SessionInDto(
            movie = MovieDto.empty,
            createdDate = "",
            beginDate = "",
            endDate = "",
            tickets = emptyList()
        )
    }
}