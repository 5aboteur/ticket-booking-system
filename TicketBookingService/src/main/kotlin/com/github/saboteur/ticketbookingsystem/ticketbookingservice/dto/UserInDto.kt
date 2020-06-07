package com.github.saboteur.ticketbookingsystem.ticketbookingservice.dto

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty

@ApiModel(value = "User")
data class UserInDto(

    @ApiModelProperty(value = "login")
    val login: String,

    @ApiModelProperty(value = "email")
    val email: String,

    @ApiModelProperty(value = "category")
    val category: String

) {
    companion object {
        val empty = UserInDto(
            login = "",
            email = "",
            category = ""
        )
    }
}