package com.github.saboteur.ticketbookingsystem.ticketbookingservice.service.impl

import com.github.saboteur.ticketbookingsystem.ticketbookingservice.config.properties.AppProperties
import com.github.saboteur.ticketbookingsystem.ticketbookingservice.domain.BookingOperation
import com.github.saboteur.ticketbookingsystem.ticketbookingservice.domain.Category
import com.github.saboteur.ticketbookingsystem.ticketbookingservice.dto.BookingResultDto
import com.github.saboteur.ticketbookingsystem.ticketbookingservice.dto.SeatDto
import com.github.saboteur.ticketbookingsystem.ticketbookingservice.dto.SessionOutDto
import com.github.saboteur.ticketbookingsystem.ticketbookingservice.dto.TicketDto
import com.github.saboteur.ticketbookingsystem.ticketbookingservice.mapper.dto.BookingResultToBookingResultDtoMapper
import com.github.saboteur.ticketbookingsystem.ticketbookingservice.mapper.dto.SessionToSessionOutDtoMapper
import com.github.saboteur.ticketbookingsystem.ticketbookingservice.mapper.dto.TicketToSeatDtoMapper
import com.github.saboteur.ticketbookingsystem.ticketbookingservice.mapper.dto.TicketToTicketDtoMapper
import com.github.saboteur.ticketbookingsystem.ticketbookingservice.model.SessionState
import com.github.saboteur.ticketbookingsystem.ticketbookingservice.model.booking.BookedTicket
import com.github.saboteur.ticketbookingsystem.ticketbookingservice.model.booking.BookingResult
import com.github.saboteur.ticketbookingsystem.ticketbookingservice.repository.ClientRepository
import com.github.saboteur.ticketbookingsystem.ticketbookingservice.repository.SessionRepository
import com.github.saboteur.ticketbookingsystem.ticketbookingservice.service.ClientService
import mu.KotlinLogging
import org.springframework.data.domain.PageRequest
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.concurrent.ConcurrentMap

@Service
class ClientServiceImpl(
    private val appProperties: AppProperties,
    private val sessionStateStorage: ConcurrentMap<Long, SessionState>,
    private val sessionRepository: SessionRepository,
    private val clientRepository: ClientRepository
) : ClientService {

    @Transactional
    override fun getAllSessions(pageIndex: Int, pageSize: Int): List<SessionOutDto> =
        sessionRepository
            .findAll(PageRequest.of(pageIndex, pageSize))
            .map(SessionToSessionOutDtoMapper::get)
            .toList()

    @Transactional
    override fun getActiveSessions(pageIndex: Int, pageSize: Int): List<SessionOutDto> =
        sessionRepository
            .findAllActive(PageRequest.of(pageIndex, pageSize))
            .map(SessionToSessionOutDtoMapper::get)
            .toList()

    @Transactional
    override fun getSeats(sessionId: Long): List<SeatDto> =
        sessionRepository
            .findByIdOrNull(sessionId)
            ?.tickets
            ?.map(TicketToSeatDtoMapper::get)
            ?: emptyList()

    @Transactional
    override fun bookTicket(clientId: Long, sessionId: Long, seatNumber: String): BookingResultDto {
        // This object will store a detailed result of booking
        val bookingResult = BookingResult().apply {
            this.clientId = clientId
            this.sessionId = sessionId
            this.operation = BookingOperation.CREATE
            this.resultMsg = "failed"
        }

        // Get the user's client profile
        val client = clientRepository
            .findByIdOrNull(clientId)
            ?: return BookingResultToBookingResultDtoMapper[bookingResult].also {
                logger.error {
                    "Error booking ticket: a client with ID = $clientId doesn't exist in the database"
                }
            }

        // If the user has the standard category and the booking doesn't open for everyone yet - reject it
        if (client.category == Category.STANDARD.ordinal) {
            val s = sessionStateStorage[sessionId]
                ?: return BookingResultToBookingResultDtoMapper[bookingResult].also {
                    logger.error {
                        "Error booking ticket: a session with ID = $sessionId doesn't exist in the session state storage"
                    }
                }

            if (!s.isOpenForEveryone) {
                return BookingResultToBookingResultDtoMapper[bookingResult].also {
                    logger.error {
                        "Error booking ticket: a session with ID = $sessionId doesn't open for everyone yet"
                    }
                }
            }
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
            .tickets
            .indexOfFirst { it.seat == seatNumber }

        if (index == -1) {
            return BookingResultToBookingResultDtoMapper[bookingResult].also {
                logger.error {
                    "Error booking ticket: a ticket with the seat number = $seatNumber doesn't exist"
                }
            }
        }

        // Check if a ticket with the seat number we got already booked
        when (session.tickets[index].isBooked) {
            false -> {
                // Mark a ticket as booked
                session.tickets[index].isBooked = true

                // Calculate discount price, if the 'social benefits' rule disabled - return full price
                session.tickets[index].discountPrice =
                    if (appProperties.socialBenefits)
                        calcTicketDiscountPrice(session.tickets[index].price, client.category)
                    else
                        session.tickets[index].price

                // Add a ticket to its client
                client.tickets.add(session.tickets[index]).also {
                    clientRepository.save(client)
                }

                val updatedId = sessionRepository
                    .save(session)
                    .id

                if (updatedId == sessionId) {
                    bookingResult
                        .apply {
                            bookedTicket?.price = session.tickets[index].price
                            bookedTicket?.discountPrice = session.tickets[index].discountPrice
                            resultMsg = "succeed"
                        }
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

    @Transactional
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
            .tickets
            .indexOfFirst { it.seat == seatNumber }

        if (index == -1) {
            return BookingResultToBookingResultDtoMapper[bookingResult].also {
                logger.error {
                    "Error booking cancellation: a ticket with the seat number = $seatNumber doesn't exist"
                }
            }
        }

        // Get the user's client profile
        val client = clientRepository
            .findByIdOrNull(clientId)
            ?: return BookingResultToBookingResultDtoMapper[bookingResult].also {
                logger.error {
                    "Error booking cancellation: a client with ID = $clientId doesn't exist in the database"
                }
            }

        // Check if a ticket with the seat number we got already booked
        when (session.tickets[index].isBooked) {
            true -> {
                // Unmark a ticket as booked
                session.tickets[index].isBooked = false

                // Return default price
                session.tickets[index].discountPrice = session.tickets[index].price

                // Remove a ticket from its client
                client.tickets.remove(session.tickets[index]).also {
                    clientRepository.save(client)
                }

                val updatedId = sessionRepository
                    .save(session)
                    .id

                if (updatedId == sessionId) {
                    bookingResult
                        .apply {
                            bookedTicket?.price = session.tickets[index].price
                            bookedTicket?.discountPrice = session.tickets[index].discountPrice
                            resultMsg = "succeed"
                        }
                        .also {
                            logger.info { "Client = $clientId successfully cancel the booking" }
                        }
                } else {
                    logger.error {
                        "Error booking cancellation: updated ID ($updatedId) != retrieved ID ($sessionId)"
                    }
                }
            }
            false -> {
                logger.error {
                    "Error booking cancellation: a ticket with the seat number = $seatNumber is free"
                }
            }
        }

        return BookingResultToBookingResultDtoMapper[bookingResult]
    }

    @Transactional
    override fun getTickets(clientId: Long): List<TicketDto> =
        clientRepository
            .findByIdOrNull(clientId)
            ?.tickets
            ?.map(TicketToTicketDtoMapper::get)
            ?: emptyList()

    private fun calcTicketDiscountPrice(price: Double, categoryNumber: Int): Double =
        with (appProperties) {
            when (Category.values()[categoryNumber]) {
                Category.SOCIAL_ONE -> price - (price * socialOneDiscountPercentage / 100)
                Category.SOCIAL_TWO -> price - (price * socialTwoDiscountPercentage / 100)
                Category.SOCIAL_THREE -> price - (price * socialThreeDiscountPercentage / 100)
                else -> price
            }
        }

    companion object {
        private val logger = KotlinLogging.logger {}
    }

}