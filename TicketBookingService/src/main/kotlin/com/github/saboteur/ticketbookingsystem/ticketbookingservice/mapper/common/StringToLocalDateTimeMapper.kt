package com.github.saboteur.ticketbookingsystem.ticketbookingservice.mapper.common

import com.github.saboteur.ticketbookingsystem.ticketbookingservice.mapper.Mapper
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

object StringToLocalDateTimeMapper : Mapper<String, LocalDateTime> {

    private val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy'T'HH:mm")

    override fun get(from: String): LocalDateTime = LocalDateTime.parse(from, formatter)

}