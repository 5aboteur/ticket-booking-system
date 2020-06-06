package com.github.saboteur.ticketbookingsystem.ticketbookingservice.domain

enum class Role(
    val roleName: String
) {
    ADMIN("Administrator"),
    CLIENT("Client")
}