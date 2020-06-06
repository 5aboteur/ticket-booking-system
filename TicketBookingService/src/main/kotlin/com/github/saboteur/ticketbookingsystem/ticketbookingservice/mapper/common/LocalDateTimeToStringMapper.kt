package com.github.saboteur.ticketbookingsystem.ticketbookingservice.mapper.common

import com.github.saboteur.ticketbookingsystem.ticketbookingservice.mapper.Mapper
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

object LocalDateTimeToStringMapper : Mapper<LocalDateTime, String> {

    private val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy'T'HH:mm")

    override fun get(from: LocalDateTime): String = from.format(formatter)

}