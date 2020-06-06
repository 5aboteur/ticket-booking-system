package com.github.saboteur.ticketbookingsystem.ticketbookingservice.service.impl

import com.github.saboteur.ticketbookingsystem.ticketbookingservice.domain.BookingOperation
import com.github.saboteur.ticketbookingsystem.ticketbookingservice.dto.BookingResultDto
import com.github.saboteur.ticketbookingsystem.ticketbookingservice.dto.SeatDto
import com.github.saboteur.ticketbookingsystem.ticketbookingservice.dto.SessionDto
import com.github.saboteur.ticketbookingsystem.ticketbookingservice.dto.TicketDto
import com.github.saboteur.ticketbookingsystem.ticketbookingservice.dto.UserDto
import com.github.saboteur.ticketbookingsystem.ticketbookingservice.mapper.dto.BookingResultToBookingResultDtoMapper
import com.github.saboteur.ticketbookingsystem.ticketbookingservice.mapper.dto.SeatToSeatDtoMapper
import com.github.saboteur.ticketbookingsystem.ticketbookingservice.mapper.dto.SessionToSessionDtoMapper
import com.github.saboteur.ticketbookingsystem.ticketbookingservice.mapper.dto.TicketToTicketDtoMapper
import com.github.saboteur.ticketbookingsystem.ticketbookingservice.mapper.dto.UserToUserDtoMapper
import com.github.saboteur.ticketbookingsystem.ticketbookingservice.mapper.model.SessionDtoToSessionMapper
import com.github.saboteur.ticketbookingsystem.ticketbookingservice.mapper.model.UserDtoToUserMapper
import com.github.saboteur.ticketbookingsystem.ticketbookingservice.model.booking.BookedTicket
import com.github.saboteur.ticketbookingsystem.ticketbookingservice.model.booking.BookingResult
import com.github.saboteur.ticketbookingsystem.ticketbookingservice.repository.ClientRepository
import com.github.saboteur.ticketbookingsystem.ticketbookingservice.repository.SessionRepository
import com.github.saboteur.ticketbookingsystem.ticketbookingservice.repository.UserRepository
import com.github.saboteur.ticketbookingsystem.ticketbookingservice.service.TicketBookingService
import mu.KotlinLogging
import org.springframework.data.domain.PageRequest
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.time.format.DateTimeParseException

@Service
class TicketBookingServiceImpl(
    private val sessionRepository: SessionRepository,
    private val clientRepository: ClientRepository,
    private val userRepository: UserRepository
) : TicketBookingService {

    override fun getAllSessions(pageIndex: Int, pageSize: Int): List<SessionDto> =
        sessionRepository
            .findAll(PageRequest.of(pageIndex, pageSize))
            .map(SessionToSessionDtoMapper::get)
            .toList()

    override fun getActiveSessions(pageIndex: Int, pageSize: Int): List<SessionDto> =
        sessionRepository
            .findAll(PageRequest.of(pageIndex, pageSize))
            .map(SessionToSessionDtoMapper::get)
            .toList()

    override fun getSeats(sessionId: Long): List<SeatDto> =
        sessionRepository
            .findByIdOrNull(sessionId)
            ?.seats
            ?.map(SeatToSeatDtoMapper::get)
            ?: emptyList()

    override fun bookTicket(clientId: Long, sessionId: Long, seatNumber: String): BookingResultDto {
        // This object will store a detailed result of booking
        val bookingResult = BookingResult().apply {
            this.clientId = clientId
            this.sessionId = sessionId
            this.operation = BookingOperation.CREATE
            this.resultMsg = "failed"
        }

        // Check if a session with the provided ID actually exists
        val session = sessionRepository
            .findByIdOrNull(sessionId)
            ?: return BookingResultToBookingResultDtoMapper[bookingResult].also {
                logger.error {
                    "Error booking ticket: a session with ID = $sessionId doesn't exist in the database"
                }
            }

        // Retrieve some necessary data from it
        bookingResult.bookedTicket = BookedTicket().apply {
            movie = session.movie.name
            date = session.beginDate
            seat = seatNumber
        }

        // Get an index of the seat which number we want to book
        val index = session
            .seats
            .indexOfFirst { it.number == seatNumber }

        if (index == -1) {
            return BookingResultToBookingResultDtoMapper[bookingResult].also {
                logger.error {
                    "Error booking ticket: a ticket with the seat number = $seatNumber doesn't exist"
                }
            }
        }

        // Check if a ticket with the seat number we got already booked
        when (session.seats[index].isBooked) {
            false -> {
                session.seats[index].isBooked = true
                val updatedId = sessionRepository
                    .save(session)
                    .id
                if (updatedId == sessionId) {
                    bookingResult
                        .apply { resultMsg = "succeed" }
                        .also {
                            logger.info { "Client = $clientId successfully booked a ticket" }
                        }
                } else {
                    logger.error {
                        "Error booking ticket: updated ID ($updatedId) != retrieved ID ($sessionId)"
                    }
                }
            }
            true -> {
                logger.error {
                    "Error booking ticket: a ticket with the seat number = $seatNumber already booked"
                }
            }
        }

        return BookingResultToBookingResultDtoMapper[bookingResult]
    }

    override fun cancelBooking(clientId: Long, sessionId: Long, seatNumber: String): BookingResultDto {
        // This object will store a detailed result of booking cancellation
        val bookingResult = BookingResult().apply {
            this.clientId = clientId
            this.sessionId = sessionId
            this.operation = BookingOperation.CANCEL
            this.resultMsg = "failed"
        }

        // Check if a session with the provided ID actually exists
        val session = sessionRepository
            .findByIdOrNull(sessionId)
            ?: return BookingResultToBookingResultDtoMapper[bookingResult].also {
                logger.error {
                    "Error booking cancellation: a session with ID = $sessionId doesn't exist in the database"
                }
            }

        // Retrieve some necessary data from it
        bookingResult.bookedTicket = BookedTicket().apply {
            movie = session.movie.name
            date = session.beginDate
            seat = seatNumber
        }

        // Get an index of the seat which booking we want to cancel
        val index = session
            .seats
            .indexOfFirst { it.number == seatNumber }

        if (index == -1) {
            return BookingResultToBookingResultDtoMapper[bookingResult].also {
                logger.error {
                    "Error booking cancellation: a ticket with the seat number = $seatNumber doesn't exist"
                }
            }
        }

        // Cancel the booking
        session.seats[index].isBooked = false

        val updatedId = sessionRepository
            .save(session)
            .id

        if (updatedId == sessionId) {
            bookingResult
                .apply { resultMsg = "succeed" }
                .also {
                    logger.info { "Client = $clientId successfully cancel the booking" }
                }
        } else {
            logger.error {
                "Error booking cancellation: updated ID ($updatedId) != retrieved ID ($sessionId)"
            }
        }

        return BookingResultToBookingResultDtoMapper[bookingResult]
    }

    override fun getTickets(clientId: Long): List<TicketDto> =
        clientRepository
            .findByIdOrNull(clientId)
            ?.tickets
            ?.map(TicketToTicketDtoMapper::get)
            ?: emptyList()

    override fun getAllUsers(pageIndex: Int, pageSize: Int): List<UserDto> =
        userRepository
            .findAll(PageRequest.of(pageIndex, pageSize))
            .map(UserToUserDtoMapper::get)
            .toList()

    override fun createUser(userDto: UserDto): Long {
        val user = userRepository.findByLoginOrNull(userDto.login)
        var result = -1L

        if (user != null) {
            logger.error {
                "Error creating user: a user with login = ${userDto.login} already exists in the database"
            }
            return result;
        }

        try {
            result = userRepository
                .save(UserDtoToUserMapper[userDto])
                .id
            logger.info { "User with ID = $result created" }
        } catch (e: DateTimeParseException) {
            logger.error { "Error creating user: invalid date - ${e.localizedMessage}" }
        } catch (e: IllegalArgumentException) {
            logger.error { "Error creating user: ${e.localizedMessage}" }
        }

        return result
    }

    override fun getUser(userId: Long): UserDto? =
        userRepository
            .findByIdOrNull(userId)
            ?.let { user ->
                UserToUserDtoMapper[user]
            }

    override fun updateUser(userId: Long, userDto: UserDto): Boolean? =
        userRepository
            .findByIdOrNull(userId)
            ?.let { user ->
                val updatedUser = UserDtoToUserMapper[userDto]
                updatedUser.id = user.id
                val updatedId = userRepository
                    .save(updatedUser)
                    .id
                if (updatedId == userId) {
                    logger.info { "User with ID = $userId updated" }
                    true
                } else {
                    logger.error {
                        "Error updating user: updated ID ($updatedId) != provided ID ($userId)"
                    }
                    false
                }
            }
            ?: (null).also {
                logger.error {
                    "Error updating user: a user with ID = $userId doesn't exist in the database"
                }
            }

    override fun deleteUser(userId: Long): Boolean? =
        userRepository
            .findByIdOrNull(userId)
            ?.let {
                userRepository.deleteById(userId)
                logger.info { "User with ID = $userId deleted" }
                true
            }
            ?: (null).also {
                logger.error {
                    "Error deleting user: a user with ID = $userId doesn't exist in the database"
                }
            }

    override fun createSession(sessionDto: SessionDto): Long {
        var result = -1L

        try {
            result = sessionRepository
                .save(SessionDtoToSessionMapper[sessionDto])
                .id
            logger.info { "Session with ID = $result created" }
        } catch (e: DateTimeParseException) {
            logger.error { "Error creating session: invalid date - ${e.localizedMessage}" }
        } catch (e: IllegalArgumentException) {
            logger.error { "Error creating session: ${e.localizedMessage}" }
        }

        return result
    }

    override fun getSession(sessionId: Long): SessionDto? =
        sessionRepository
            .findByIdOrNull(sessionId)
            ?.let { session ->
                SessionToSessionDtoMapper[session]
            }

    override fun updateSession(sessionId: Long, sessionDto: SessionDto): Boolean? =
        sessionRepository
            .findByIdOrNull(sessionId)
            ?.let { session ->
                val updatedSession = SessionDtoToSessionMapper[sessionDto]
                updatedSession.id = session.id
                val updatedId = sessionRepository
                    .save(updatedSession)
                    .id
                if (updatedId == sessionId) {
                    logger.info { "Session with ID = $sessionId updated" }
                    true
                } else {
                    logger.error {
                        "Error updating session: updated ID ($updatedId) != provided ID ($sessionId)"
                    }
                    false
                }
            }
            ?: (null).also {
                logger.error {
                    "Error updating session: a session with ID = $sessionId doesn't exist in the database"
                }
            }

    override fun deleteSession(sessionId: Long): Boolean? =
        sessionRepository
            .findByIdOrNull(sessionId)
            ?.let {
                sessionRepository.deleteById(sessionId)
                logger.info { "Session with ID = $sessionId deleted" }
                true
            }
            ?: (null).also {
                logger.error {
                    "Error deleting session: a session with ID = $sessionId doesn't exist in the database"
                }
            }

    companion object {
        private val logger = KotlinLogging.logger {}
    }

}