package com.example.api.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.annotations.tags.Tag;

@OpenAPIDefinition(
        info = @Info(title = "Web-shop API", version = "1.0.0"),
        servers = {@Server(url = "http://localhost:8080")},
        tags = {
                @Tag(name = "Buy Order", description = "Here all the orders are found"),
                @Tag(name = "Customer", description = "Here all the customers are found"),
                @Tag(name = "Sign up", description = "Here you can sign up a new user"),
                @Tag(name = "Items", description = "Here all the items are found"),
                @Tag(name = "Get", description = "Here all the get methods are found"),
                @Tag(name = "Post", description = "Here all the post methods are found")
        }
)
@SecurityScheme(name = "BearerJWT", type = SecuritySchemeType.HTTP, scheme = "bearer", bearerFormat = "JWT",
        description = "Bearer token for the project.")
public class OpenApiDefinition {
}
