package com.example.restfulwebservices.configuration;


import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;





import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;



@Configuration


public class OpenApiConfig implements WebMvcConfigurer {
    @Bean
    public OpenAPI customOpenAPI(@Value("${application-description}") String appDesciption,
                                 @Value("${application-version}") String appVersion) {

        return new OpenAPI().info(new Info()
                        .title("Mina Services API")
                        .version(appVersion)
                        .description(appDesciption)
                        .termsOfService("http://swagger.io/terms/")
                         .license(new License().name("Apache 2.0").url("http://springdoc.org")))
                     // for adding Authorization in the Swagger Filee
                .addSecurityItem(new SecurityRequirement()
                        .addList("Bearer Authentication"))
                .components(new Components().addSecuritySchemes("Bearer Authentication", createAPIKeyScheme()))
                .info(new Info().title("Mina API")
                        .description("Some custom description of API.")
                        .version("1.0").contact(getContact())
                        .license(getLicense()));

    }
    private SecurityScheme createAPIKeyScheme() {
        return new SecurityScheme().type(SecurityScheme.Type.HTTP)
                .bearerFormat("JWT")
                .scheme("bearer");
    }

    private Contact getContact(){
        return new Contact()
                .name("Allianz Egypt")
                .email("")
                .url("");
    }

    private License getLicense(){
        return new License()
                .name("License of API")
                .url("API license URL");


    }
}