package com.github.saboteur.ticketbookingsystem.ticketbookingservice.service.impl

import com.github.saboteur.ticketbookingsystem.ticketbookingservice.dto.SessionDto
import com.github.saboteur.ticketbookingsystem.ticketbookingservice.dto.UserDto
import com.github.saboteur.ticketbookingsystem.ticketbookingservice.mapper.dto.SessionToSessionDtoMapper
import com.github.saboteur.ticketbookingsystem.ticketbookingservice.mapper.dto.UserToUserDtoMapper
import com.github.saboteur.ticketbookingsystem.ticketbookingservice.mapper.model.UserDtoToUserMapper
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

    override fun getAllUsers(pageIndex: Int, pageSize: Int): List<UserDto> =
        userRepository
            .findAll(PageRequest.of(pageIndex, pageSize))
            .map(UserToUserDtoMapper::get)
            .toList()

    override fun createUser(userDto: UserDto): Long {
        val user = userRepository.findByLogin(userDto.login)
        var result = -1L

        if (user.isPresent) {
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

    companion object {
        private val logger = KotlinLogging.logger {}
    }

}