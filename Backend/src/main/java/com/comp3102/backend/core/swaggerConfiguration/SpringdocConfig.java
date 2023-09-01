package com.comp3102.backend.core.swaggerConfiguration;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.comp3102.backend.core.constant.SwaggerConstant.*;

@Configuration
@SecurityScheme(
        name = "bearerAuth",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        scheme = "bearer"
)
public class SpringdocConfig {

    @Bean
    public OpenAPI baseOpenAPI() {
        Components components = new Components();
        components.addResponses("badRequestAPI", badRequest);


        Info myInfo = new Info();
        myInfo.title(API_TITLE)
                .version(API_VERSION)
                .description(API_DESCRIPTION)
                .contact(new Contact().name(CONTACT_NAME).email(CONTACT_EMAIL).url(CONTACT_URL))
                .license(new License().url(LICENSE_URL).name(LICENSE));

        return new OpenAPI().components(components).
                info(myInfo);
    }
}