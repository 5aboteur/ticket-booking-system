package com.github.saboteur.ticketbookingsystem.ticketbookingservice.model

import java.time.LocalDateTime

data class SessionState(
    var isOpenForEveryone: Boolean = false,
    var createdDate: LocalDateTime = LocalDateTime.MIN,
    var beginDate: LocalDateTime = LocalDateTime.MIN
)