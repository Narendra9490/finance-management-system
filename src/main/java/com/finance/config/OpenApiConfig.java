package com.finance.config;

import io.swagger.v3.oas.models.*;
import io.swagger.v3.oas.models.info.*;
import io.swagger.v3.oas.models.security.*;
import org.springframework.context.annotation.*;

@Configuration
public class OpenApiConfig {
	@Bean
	OpenAPI financeOpenAPI() {
		return new OpenAPI()
				.info(new Info().title("Finance Management API").version("1.0")
						.description("Finance Management System REST API"))
				.components(new Components().addSecuritySchemes("basicAuth",
						new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("basic")));
	}
}
