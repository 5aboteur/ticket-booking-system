package com.github.saboteur.ticketbookingsystem.ticketbookingservice.mapper

interface Mapper<FROM, TO> {
    operator fun get(from: FROM): TO
}