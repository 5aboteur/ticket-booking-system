package com.github.saboteur.ticketbookingsystem.ticketbookingservice.service.impl

import com.github.saboteur.ticketbookingsystem.ticketbookingservice.dto.SessionDto
import com.github.saboteur.ticketbookingsystem.ticketbookingservice.dto.UserDto
import com.github.saboteur.ticketbookingsystem.ticketbookingservice.mapper.dto.SessionToSessionDtoMapper
import com.github.saboteur.ticketbookingsystem.ticketbookingservice.mapper.dto.UserToUserDtoMapper
import com.github.saboteur.ticketbookingsystem.ticketbookingservice.mapper.model.SessionDtoToSessionMapper
import com.github.saboteur.ticketbookingsystem.ticketbookingservice.mapper.model.UserDtoToUserMapper
import com.github.saboteur.ticketbookingsystem.ticketbookingservice.model.User
import com.github.saboteur.ticketbookingsystem.ticketbookingservice.repository.SessionRepository
import com.github.saboteur.ticketbookingsystem.ticketbookingservice.repository.UserRepository
import com.github.saboteur.ticketbookingsystem.ticketbookingservice.service.TicketBookingAdministratorService
import mu.KotlinLogging
import org.springframework.data.domain.PageRequest
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.time.format.DateTimeParseException

@Service
class TicketBookingAdministratorServiceImpl(
    private val sessionRepository: SessionRepository,
    private val userRepository: UserRepository
) : TicketBookingAdministratorService {

    override fun getAllUsers(pageIndex: Int, pageSize: Int): List<UserDto> =
        userRepository
            .findAll(PageRequest.of(pageIndex, pageSize))
            .map(UserToUserDtoMapper::get)
            .toList()

    override fun createUser(userDto: UserDto): Long {
        val user = userRepository.findByLoginOrNull(userDto.login)
        var result = -1L

        if (user != null) {
            return (result).also {
                logger.error {
                    "Error creating user: a user with login = ${userDto.login} already exists in the database"
                }
            }
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
            ?: (null).also {
                logger.error {
                    "Error getting user: a user with ID = $userId doesn't exist in the database"
                }
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
            ?: (null).also {
                logger.error {
                    "Error getting session: a session with ID = $sessionId doesn't exist in the database"
                }
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

    private fun UserRepository.findByLoginOrNull(login: String): User? = findByLogin(login).orElse(null)

    companion object {
        private val logger = KotlinLogging.logger {}
    }

}