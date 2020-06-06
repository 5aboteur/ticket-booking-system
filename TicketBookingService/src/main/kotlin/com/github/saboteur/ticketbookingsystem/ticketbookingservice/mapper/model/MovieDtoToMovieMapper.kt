package com.github.saboteur.ticketbookingsystem.ticketbookingservice.mapper.model

import com.github.saboteur.ticketbookingsystem.ticketbookingservice.dto.MovieDto
import com.github.saboteur.ticketbookingsystem.ticketbookingservice.mapper.Mapper
import com.github.saboteur.ticketbookingsystem.ticketbookingservice.model.Movie

object MovieDtoToMovieMapper : Mapper<MovieDto, Movie> {

    override fun get(from: MovieDto): Movie =
        Movie(
            name = from.name,
            genre = from.genre,
            director = from.director,
            year = from.year
        )

}