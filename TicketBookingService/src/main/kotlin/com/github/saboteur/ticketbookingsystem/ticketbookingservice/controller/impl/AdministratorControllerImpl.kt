package com.github.saboteur.ticketbookingsystem.ticketbookingservice.controller.impl

import com.github.saboteur.ticketbookingsystem.ticketbookingservice.config.properties.AppProperties
import com.github.saboteur.ticketbookingsystem.ticketbookingservice.controller.AdministratorControllerApi
import com.github.saboteur.ticketbookingsystem.ticketbookingservice.dto.RescheduleSessionDto
import com.github.saboteur.ticketbookingsystem.ticketbookingservice.dto.SessionInDto
import com.github.saboteur.ticketbookingsystem.ticketbookingservice.dto.SessionOutDto
import com.github.saboteur.ticketbookingsystem.ticketbookingservice.dto.UserInDto
import com.github.saboteur.ticketbookingsystem.ticketbookingservice.dto.UserOutDto
import com.github.saboteur.ticketbookingsystem.ticketbookingservice.service.AdministratorService
import io.swagger.annotations.Api
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController

@Api
@RestController
class AdministratorControllerImpl(
    private val appProperties: AppProperties,
    private val administratorService: AdministratorService
) : AdministratorControllerApi {

    override fun getAllUsers(pageIndex: Int, pageSize: Int): ResponseEntity<List<UserOutDto>> {
        val result = administratorService.getAllUsers(pageIndex, pageSize)
        return ResponseEntity
            .status(
                if (result.isEmpty())
                    HttpStatus.NOT_FOUND
                else
                    HttpStatus.OK
            )
            .body(result)
    }

    override fun createUser(userInDto: UserInDto): ResponseEntity<Long> {
        val result = administratorService.createUser(userInDto)
        return ResponseEntity
            .status(
                if (result == -1L || result == 0L)
                    HttpStatus.BAD_REQUEST
                else
                    HttpStatus.CREATED
            )
            .body(result)
    }

    override fun getUser(userId: Long): ResponseEntity<UserOutDto?> {
        val result = administratorService.getUser(userId)
        return ResponseEntity
            .status(
                if (result == null)
                    HttpStatus.NOT_FOUND
                else
                    HttpStatus.OK
            )
            .body(result)
    }

    override fun updateUser(userId: Long, userInDto: UserInDto): ResponseEntity<Boolean> {
        val result = administratorService.updateUser(userId, userInDto)
        return ResponseEntity
            .status(
                when (result) {
                    true -> HttpStatus.OK
                    false -> HttpStatus.BAD_REQUEST
                    null -> HttpStatus.NOT_FOUND
                }
            )
            .body(result)
    }

    override fun deleteUser(userId: Long): ResponseEntity<Boolean> {
        val result = administratorService.deleteUser(userId)
        return ResponseEntity
            .status(
                if (result == null)
                    HttpStatus.NOT_FOUND
                else
                    HttpStatus.OK
            )
            .body(result)
    }

    override fun giveAdminRights(userId: Long): ResponseEntity<Boolean> {
        val result = administratorService.giveAdminRights(userId)
        return ResponseEntity
            .status(
                when (result) {
                    true -> HttpStatus.OK
                    false -> HttpStatus.BAD_REQUEST
                    null -> HttpStatus.NOT_FOUND
                }
            )
            .body(result)
    }

    override fun removeAdminRights(userId: Long): ResponseEntity<Boolean> {
        val result = administratorService.removeAdminRights(userId)
        return ResponseEntity
            .status(
                if (result == null)
                    HttpStatus.NOT_FOUND
                else
                    HttpStatus.OK
            )
            .body(result)
    }

    override fun createSession(sessionInDto: SessionInDto): ResponseEntity<Long> {
        val result = administratorService.createSession(sessionInDto)
        return ResponseEntity
            .status(
                if (result == -1L || result == 0L)
                    HttpStatus.BAD_REQUEST
                else
                    HttpStatus.CREATED
            )
            .body(result)
    }

    override fun getSession(sessionId: Long): ResponseEntity<SessionOutDto?> {
        val result = administratorService.getSession(sessionId)
        return ResponseEntity
            .status(
                if (result == null)
                    HttpStatus.NOT_FOUND
                else
                    HttpStatus.OK
            )
            .body(result)
    }

    override fun rescheduleSession(
        sessionId: Long,
        rescheduleSessionDto: RescheduleSessionDto
    ): ResponseEntity<Boolean> {
        val result = administratorService.rescheduleSession(sessionId, rescheduleSessionDto)
        return ResponseEntity
            .status(
                when (result) {
                    true -> HttpStatus.OK
                    false -> HttpStatus.BAD_REQUEST
                    null -> HttpStatus.NOT_FOUND
                }
            )
            .body(result)
    }

    override fun deleteSession(sessionId: Long): ResponseEntity<Boolean> {
        val result = administratorService.deleteSession(sessionId)
        return ResponseEntity
            .status(
                if (result == null)
                    HttpStatus.NOT_FOUND
                else
                    HttpStatus.OK
            )
            .body(result)
    }

    override fun getSocialBenefitsRuleStatus(): ResponseEntity<String> =
        ResponseEntity.ok(
            if (appProperties.socialBenefits)
                "ENABLED"
            else
                "DISABLED"
        )

    override fun changeSocialBenefitsRuleStatus(status: Boolean): ResponseEntity<String> {
        appProperties.socialBenefits = status
        return ResponseEntity.ok(
            if (appProperties.socialBenefits)
                "ENABLED"
            else
                "DISABLED"
        )
    }

}