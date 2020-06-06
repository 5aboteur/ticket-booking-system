package com.github.saboteur.ticketbookingsystem.ticketbookingservice.dto

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty

@ApiModel(value = "Movie")
data class MovieDto(

    @ApiModelProperty(value = "name")
    val name: String,

    @ApiModelProperty(value = "genre")
    val genre: String,

    @ApiModelProperty(value = "director")
    val director: String,

    @ApiModelProperty(value = "year")
    val year: Int

) {
    companion object {
        val empty = MovieDto(
            name = "",
            genre = "",
            director = "",
            year = 0
        )
    }
}