package com.github.saboteur.ticketbookingsystem.ticketbookingservice.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import springfox.documentation.builders.PathSelectors
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.service.ApiInfo
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spring.web.plugins.Docket
import springfox.documentation.swagger2.annotations.EnableSwagger2WebFlux

@Configuration
@EnableSwagger2WebFlux
class SwaggerConfig {

    @Bean
    fun api(): Docket {
        val info = ApiInfo(
            "Ticket Booking Service API",
            "A core for a booking system",
            API_VERSION,
            ApiInfo.DEFAULT.termsOfServiceUrl,
            ApiInfo.DEFAULT.contact,
            ApiInfo.DEFAULT.license,
            ApiInfo.DEFAULT.licenseUrl,
            ApiInfo.DEFAULT.vendorExtensions
        )

        return Docket(DocumentationType.SWAGGER_2)
            .apiInfo(info)
            .select()
            .apis(RequestHandlerSelectors.any())
            .paths(PathSelectors.any())
            .build()
    }

    companion object {
        const val API_VERSION = "v1"
    }

}