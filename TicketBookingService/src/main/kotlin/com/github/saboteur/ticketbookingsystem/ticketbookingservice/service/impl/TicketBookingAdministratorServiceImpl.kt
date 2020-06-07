package com.github.saboteur.ticketbookingsystem.ticketbookingservice.service.impl

import com.github.saboteur.ticketbookingsystem.ticketbookingservice.dto.SessionDto
import com.github.saboteur.ticketbookingsystem.ticketbookingservice.dto.UserInDto
import com.github.saboteur.ticketbookingsystem.ticketbookingservice.dto.UserOutDto
import com.github.saboteur.ticketbookingsystem.ticketbookingservice.exception.UnknownCategoryException
import com.github.saboteur.ticketbookingsystem.ticketbookingservice.mapper.dto.SessionToSessionDtoMapper
import com.github.saboteur.ticketbookingsystem.ticketbookingservice.mapper.dto.UserToUserDtoMapper
import com.github.saboteur.ticketbookingsystem.ticketbookingservice.mapper.model.SessionDtoToSessionMapper
import com.github.saboteur.ticketbookingsystem.ticketbookingservice.mapper.model.UserInDtoToUserMapper
import com.github.saboteur.ticketbookingsystem.ticketbookingservice.model.Administrator
import com.github.saboteur.ticketbookingsystem.ticketbookingservice.model.User
import com.github.saboteur.ticketbookingsystem.ticketbookingservice.repository.AdministratorRepository
import com.github.saboteur.ticketbookingsystem.ticketbookingservice.repository.SessionRepository
import com.github.saboteur.ticketbookingsystem.ticketbookingservice.repository.UserRepository
import com.github.saboteur.ticketbookingsystem.ticketbookingservice.service.TicketBookingAdministratorService
import mu.KotlinLogging
import org.springframework.data.domain.PageRequest
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.format.DateTimeParseException

@Service
class TicketBookingAdministratorServiceImpl(
    private val sessionRepository: SessionRepository,
    private val administratorRepository: AdministratorRepository,
    private val userRepository: UserRepository
) : TicketBookingAdministratorService {

    @Transactional
    override fun getAllUsers(pageIndex: Int, pageSize: Int): List<UserOutDto> =
        userRepository
            .findAll(PageRequest.of(pageIndex, pageSize))
            .map(UserToUserDtoMapper::get)
            .toList()

    @Transactional
    override fun createUser(userInDto: UserInDto): Long {
        val user = userRepository.findByLoginOrNull(userInDto.login)
        var result = -1L

        if (user != null) {
            return (result).also {
                logger.error {
                    "Error creating user: a user with login = ${userInDto.login} already exists in the database"
                }
            }
        }

        try {
            result = userRepository
                .save(UserInDtoToUserMapper[userInDto])
                .id
            logger.info { "User with ID = $result created" }
        } catch (e: UnknownCategoryException) {
            logger.error { "Error creating user: invalid category - ${e.localizedMessage}" }
        } catch (e: DateTimeParseException) {
            logger.error { "Error creating user: invalid date - ${e.localizedMessage}" }
        } catch (e: IllegalArgumentException) {
            logger.error { "Error creating user: ${e.localizedMessage}" }
        }

        return result
    }

    @Transactional
    override fun getUser(userId: Long): UserOutDto? =
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

    @Transactional
    override fun updateUser(userId: Long, userInDto: UserInDto): Boolean? =
        userRepository
            .findByIdOrNull(userId)
            ?.let { user ->
                try {
                    val updatedUser = UserInDtoToUserMapper[userInDto]

                    with (updatedUser) {
                        id = user.id
                        admin = user.admin
                        client = user.client
                    }

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
                } catch (e: UnknownCategoryException) {
                    logger.error { "Error creating user: invalid category - ${e.localizedMessage}" }
                    false
                } catch (e: DateTimeParseException) {
                    logger.error { "Error creating user: invalid date - ${e.localizedMessage}" }
                    false
                } catch (e: IllegalArgumentException) {
                    logger.error { "Error creating user: ${e.localizedMessage}" }
                    false
                }
            }
            ?: (null).also {
                logger.error {
                    "Error updating user: a user with ID = $userId doesn't exist in the database"
                }
            }

    @Transactional
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

    @Transactional
    override fun giveAdminRights(userId: Long): Boolean? =
        userRepository
            .findByIdOrNull(userId)
            .takeIf { it?.admin == null }
            ?.let { user ->
                user.admin = Administrator()
                val updatedId = userRepository
                    .save(user)
                    .id

                if (updatedId == userId) {
                    logger.info { "User with ID = $userId now has administrator rights" }
                    true
                } else {
                    logger.error {
                        "Error giving admin rights: updated ID ($updatedId) != provided ID ($userId)"
                    }
                    false
                }
            }
            ?: (null).also {
                logger.error {
                    "Error giving admin rights: a user with ID = $userId doesn't exist in the database, " +
                        "or it is an administrator already"
                }
            }

    // TODO: fix this
    @Transactional
    override fun removeAdminRights(userId: Long): Boolean? =
        userRepository
            .findByIdOrNull(userId)
            ?.let { user ->
                user.admin?.id?.let { administratorRepository.deleteById(it) }
                logger.info { "User with ID = $userId has lost administrator rights" }
                true
            }
            ?: (null).also {
                logger.error {
                    "Error removing admin rights: a user with ID = $userId doesn't exist in the database"
                }
            }

    @Transactional
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

    @Transactional
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

    @Transactional
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

    @Transactional
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

    private fun UserRepository.findByLoginOrNull(login: String): User? =
        findByLogin(login).orElse(null)

    companion object {
        private val logger = KotlinLogging.logger {}
    }

}