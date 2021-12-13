package com.bol.assignment.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

/**
 * @author Pooya Mirzapour (pooyamirzapour@gmail.com)
 */
@Configuration
@EnableSwagger2
public class Swagger2Config {

    public static final String PACKAGE = "com.bol.assignment";

    @Bean
    public Docket apiDocket() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage(PACKAGE))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(getApiInfo());
    }

    private ApiInfo getApiInfo() {
        return new ApiInfo(
                "6-stone Kalah",
                "This application is a Java RESTful Web Service that runs a Kalah game",
                "1.0.0",
                "",
                new Contact("Pooya Mirzapour", "https://www.linkedin.com/in/pooya-mirzapour", "pooyamirzapour@gmail.com"),
                "",
                "",
                Collections.emptyList()
        );
    }


}
