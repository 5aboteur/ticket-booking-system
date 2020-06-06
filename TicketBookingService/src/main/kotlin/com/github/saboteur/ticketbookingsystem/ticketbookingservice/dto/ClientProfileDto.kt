package com.github.saboteur.ticketbookingsystem.ticketbookingservice.dto

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty

@ApiModel(value = "ClientProfile")
data class ClientProfileDto(

    @ApiModelProperty(value = "category")
    val category: String,

    @ApiModelProperty(value = "sessions")
    val sessions: List<SessionDto>

) {
    companion object {
        val empty = ClientProfileDto(
            category = "",
            sessions = emptyList()
        )
    }
}