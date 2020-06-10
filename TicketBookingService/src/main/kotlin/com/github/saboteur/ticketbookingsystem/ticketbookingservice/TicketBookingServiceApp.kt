package com.github.saboteur.ticketbookingsystem.ticketbookingservice

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling

@SpringBootApplication
@EnableScheduling
class TicketBookingServiceApp
fun main(args: Array<String>) {
    runApplication<TicketBookingServiceApp>(*args)
}