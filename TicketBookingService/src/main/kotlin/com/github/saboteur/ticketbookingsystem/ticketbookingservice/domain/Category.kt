package com.github.saboteur.ticketbookingsystem.ticketbookingservice.domain

enum class Category(
    val categoryName: String
) {
    STANDARD("standard"),
    SOCIAL_ONE("social-1"),
    SOCIAL_TWO("social-2"),
    SOCIAL_THREE("social-3"),
    UNKNOWN("unknown");

    companion object {
        fun checkAndGet(name: String) =
            when (name.toLowerCase()) {
                "standard" -> STANDARD
                "social-1" -> SOCIAL_ONE
                "social-2" -> SOCIAL_TWO
                "social-3" -> SOCIAL_THREE
                else -> UNKNOWN
            }
    }
}