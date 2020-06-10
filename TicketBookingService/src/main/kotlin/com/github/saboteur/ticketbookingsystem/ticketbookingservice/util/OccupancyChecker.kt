package com.github.saboteur.ticketbookingsystem.ticketbookingservice.util

import com.github.saboteur.ticketbookingsystem.ticketbookingservice.config.PostgresConfig
import com.github.saboteur.ticketbookingsystem.ticketbookingservice.config.properties.AppProperties
import com.github.saboteur.ticketbookingsystem.ticketbookingservice.model.SessionState
import mu.KotlinLogging
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import java.sql.ResultSet
import java.sql.SQLException
import java.time.LocalDateTime
import java.util.concurrent.ConcurrentMap

@Component
class OccupancyChecker(
    private val appProperties: AppProperties,
    private val sessionStateStorage: ConcurrentMap<Long, SessionState>,
    private val postgresConfig: PostgresConfig
) : Runnable {

    @Scheduled(cron = "#{@getOccupancyCheckerCron}")
    override fun run() {
        logger.info { "OccupancyChecker - Sessions occupancy check process has begun" }

        for ((sessionId, sessionState) in sessionStateStorage.entries) {
            val now = LocalDateTime.now()
            if (!sessionState.isOpenForEveryone) {
                val timeWhenStandardCategoryCanBook =
                    sessionState
                        .beginDate
                        .minusMinutes(appProperties.standardCategoryBookingTimeout.toLong())
                val timeWhenStandardCategoryCanBookEarly =
                    sessionState
                        .createdDate
                        .plusMinutes(appProperties.occupancyTimeout.toLong())

                when {
                    // If the 'standardCategoryBookingTimeout' has been reached - just open booking for everyone
                    now >= timeWhenStandardCategoryCanBook -> {
                        sessionStateStorage[sessionId]?.isOpenForEveryone = true
                        logger.info { "Session with ID = $sessionId now open for everyone" }
                    }
                    /*
                     * If the 'occupancyTimeout' has been reached - check how many tickets were booked and compare
                     * that value with min & max rates
                     */
                    now >= timeWhenStandardCategoryCanBookEarly -> {
                        checkIfStandardCategoryCanBookEarly(sessionId)
                    }
                    else -> {
                        logger.info {
                            "Session with ID = $sessionId is still closed for users with standard category"
                        }
                    }
                }
            }

            // If a session already started we need to remove it from the storage to prevent booking
            if (now >= sessionState.beginDate) {
                sessionStateStorage.remove(sessionId)
                logger.info { "Session with ID = $sessionId removed from the session state storage" }
            }
        }

        logger.info { "The system has ${sessionStateStorage.size} active sessions" }

        logger.info { "OccupancyChecker - Sessions occupancy check process has ended" }
    }

    private fun checkIfStandardCategoryCanBookEarly(sessionId: Long) {
        var rs: ResultSet? = null
        try {
            rs = postgresConfig
                .getPostgresDataSource()
                .connection
                .prepareStatement("""
                    SELECT COUNT(t.id)
                    FROM tickets t, sessions s, sessions_tickets st
                    WHERE s.id = st.session_id 
                    AND t.id = st.tickets_id
                    AND s.id = $sessionId
                    AND is_booked = true
                    GROUP BY s.id
                """)
                .executeQuery()

            if (rs.next()) {
                val numberOfBookedTickets = rs.getInt(1)

                if (numberOfBookedTickets <= appProperties.occupancyMinRate
                    || numberOfBookedTickets >= appProperties.occupancyMaxRate) {
                    sessionStateStorage[sessionId]?.isOpenForEveryone = true
                    logger.info { "Session with ID = $sessionId now open for everyone" }
                }
            }
        } catch (e: SQLException) {
            logger.error { "Error fetching data from the database: ${e.localizedMessage}" }
        } finally {
            rs?.close()
        }
    }

    companion object {
        private val logger = KotlinLogging.logger {}
    }

}