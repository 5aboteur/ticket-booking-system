package com.github.saboteur.ticketbookingsystem.ticketbookingservice.config.properties

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Component

@Primary
@Component
@ConfigurationProperties(prefix = "app")
class AppProperties(
    var socialBenefits: Boolean = true,
    var standardCategoryBookingTimeout: Int = 0,
    var occupancyTimeout: Int = 0,
    var occupancyMinRate: Int = 0,
    var occupancyMaxRate: Int = 0,
    var occupancyCheckerCron: String = "-",
    var socialOneDiscountPercentage: Int = 0,
    var socialTwoDiscountPercentage: Int = 0,
    var socialThreeDiscountPercentage: Int = 0
)