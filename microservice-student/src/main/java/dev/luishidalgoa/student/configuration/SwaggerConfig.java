package dev.luishidalgoa.student.configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
        info = @Info(
                title = "Microservice Student",
                description = "provee de la información de los estudiantes",
                termsOfService = "http://swagger.io/terms/",
                contact = @Contact(
                        name = "Luis Hidalgo Aguilar",
                        url = "https://www.linkedin.com/feed/",
                        email = "luishidalgoa@outlook.es"
                ),
                version = "1.0.0"
        ),
        servers = {
                @Server(
                        url = "http://localhost:8080", description = "Local server"
                ),
                @Server(
                        url = "http://localhost:8090", description = "Microservice-student"
                )
        }
)
public class SwaggerConfig {
}
