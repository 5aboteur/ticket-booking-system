package com.github.saboteur.ticketbookingsystem.ticketbookingservice.dto

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty

@ApiModel(value = "RescheduleSession")
data class RescheduleSessionDto(

    @ApiModelProperty(value = "beginDate")
    val beginDate: String,

    @ApiModelProperty(value = "endDate")
    val endDate: String

) {
    companion object {
        val empty = RescheduleSessionDto(
            beginDate = "",
            endDate = ""
        )
    }
}