package com.github.saboteur.ticketbookingsystem.ticketbookingservice.config

import com.github.saboteur.ticketbookingsystem.ticketbookingservice.model.SessionState
import mu.KotlinLogging
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.DependsOn
import java.sql.ResultSet
import java.sql.SQLException
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.ConcurrentMap

@Configuration
class SessionStateStorageConfig(
    private val postgresConfig: PostgresConfig
) {

    @Bean
    @DependsOn(value = ["postgresDataSource"])
    fun sessionStateStorage(): ConcurrentMap<Long, SessionState> {
        val storage = ConcurrentHashMap<Long, SessionState>()
        var rs: ResultSet? = null

        try {
            rs = postgresConfig
                .getPostgresDataSource()
                .connection
                .prepareStatement("SELECT * FROM sessions s WHERE s.begin_date > NOW()")
                .executeQuery()

            // TODO: this may not be the best way to get data from DB, should be redone l8r (lazy?)
            while (rs.next()) {
                val sessionId = rs.getLong(1)
                val sessionState = SessionState(
                    isOpenForEveryone = false,
                    createdDate = rs.getTimestamp(2).toLocalDateTime(),
                    beginDate = rs.getTimestamp(3).toLocalDateTime()
                )

                storage[sessionId] = sessionState

                logger.info { "Session with ID = $sessionId added to the session state storage" }
            }
        } catch (e: SQLException) {
            logger.error { "Error initializing session state storage: ${e.localizedMessage}" }
        } finally {
            rs?.close()
        }

        return (storage).also {
            logger.info { "The system has ${storage.size} active sessions" }
        }
    }

    companion object {
        private val logger = KotlinLogging.logger {}
    }

}