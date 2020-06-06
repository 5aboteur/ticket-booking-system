package com.github.saboteur.ticketbookingsystem.ticketbookingservice.mapper.dto

import com.github.saboteur.ticketbookingsystem.ticketbookingservice.dto.MovieDto
import com.github.saboteur.ticketbookingsystem.ticketbookingservice.mapper.Mapper
import com.github.saboteur.ticketbookingsystem.ticketbookingservice.model.Movie

object MovieToMovieDtoMapper : Mapper<Movie, MovieDto> {

    override fun get(from: Movie): MovieDto =
        MovieDto(
            name = from.name,
            genre = from.genre,
            director = from.director,
            year = from.year
        )

}