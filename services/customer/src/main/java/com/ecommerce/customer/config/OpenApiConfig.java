package com.ecommerce.customer.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@Slf4j
public class OpenApiConfig {

    @Value("${openapi.dev.url}")
    private String devUrl;

    @Bean
    public OpenAPI getOpenApiInfo() {
        Server devServer = new Server();
        devServer.setUrl(devUrl);
        devServer.setDescription("Server URL in Development environment");

        Contact contact = new Contact();
        contact.setEmail("amasabcar@outlook.com");
        contact.setName("Amador");
        contact.setUrl("https://amadordev.netlify.app/");

        License mitLicense = new License().name("MIT License").url("https://choosealicense.com/licenses/mit/");

        Info info = new Info()
                .title("Customer Microservice API")
                .version("1.0")
                .contact(contact)
                .description("This API exposes endpoints to manage customer operations.")
                .license(mitLicense);

        log.info("Swagger configuration loaded: {}", devUrl);
        return new OpenAPI().info(info).servers(List.of(devServer));
    }
}
