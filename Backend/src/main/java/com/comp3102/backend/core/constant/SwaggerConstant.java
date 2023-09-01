package com.comp3102.backend.core.constant;

import io.swagger.v3.oas.models.examples.Example;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.responses.ApiResponse;
import org.springframework.http.MediaType;

public class SwaggerConstant {

    public static final String CONTACT_EMAIL = "aziz@eskicamolug.com";
    public static final String CONTACT_URL = "http://frontend-camolug-deployment.s3-website-us-east-1.amazonaws.com/contact";
    public static final String CONTACT_NAME = "Eski Camolug Support";
    public static final String API_TITLE = "Eski Camolug Otomotiv Open API";
    public static final String API_DESCRIPTION = "Welcome to the Car Rental API. This API provides endpoints for managing car rentals, reservations, users, and payments. "
            + "With this API, you can create new reservations, retrieve existing reservations, manage user accounts, handle payments, and more. "
            + "It is designed to facilitate the smooth and efficient management of car rental operations. "
            + "Please refer to the API documentation for detailed information on how to interact with each endpoint and make the most out of the Car Rental system.";
    public static final String TERM_OF_SERVICE = "Terms Of Service";
    public static final String API_VERSION = "1.0";
    public static final String LICENSE = "Apache License 2.1.0";
    public static final String LICENSE_URL = "https://www.apache.org/licenses/LICENSE-2.0";


    public static final ApiResponse badRequest = new ApiResponse()
            .content(new Content().addMediaType(MediaType.APPLICATION_JSON_VALUE,
                    new io.swagger.v3.oas.models.media.MediaType().addExamples("default", new Example().value("{\n" +
                            "  \"timestamp\": \"2023-03-20T10:27:03.330+00:00\",\n" +
                            "  \"status\": 400,\n" +
                            "  \"errors\": {\n" +
                            "    \"year\": \"Year Cannot be null or blank\",\n" +
                            "    \"enginePower\": \"Engine Power Cannot be null or blank\"\n" +
                            "  }\n" +
                            "}")))
            );


}
