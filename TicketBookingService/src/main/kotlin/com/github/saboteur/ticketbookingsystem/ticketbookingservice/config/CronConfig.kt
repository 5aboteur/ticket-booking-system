package com.github.saboteur.ticketbookingsystem.ticketbookingservice.config

import com.github.saboteur.ticketbookingsystem.ticketbookingservice.config.properties.AppProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class CronConfig(
    private val appProperties: AppProperties
) {

    @Bean
    fun getOccupancyCheckerCron(): String = appProperties.occupancyCheckerCron

}