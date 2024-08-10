package com.example.employee_management_system;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.annotations.ExternalDocumentation;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@SecurityScheme(
		name = "BasicAuth",
		scheme = "basic",
		type = SecuritySchemeType.HTTP,
		in = SecuritySchemeIn.HEADER
)
@OpenAPIDefinition(
		info = @Info(
				title = "Employee Management System API",
				version = "1.0",
				description = "API for managing employee information, projects, and other related data.",
				termsOfService = "http://swagger.io/terms/",

				license = @License(
						name = "Apache 2.0",
						url = "http://www.apache.org/licenses/LICENSE-2.0.html"
				)
		),
		servers = {
				@Server(url = "http://localhost:8080", description = "Local Development Server"),
				@Server(url = "https://api.yourdomain.com", description = "Production Server")
		},
		externalDocs = @ExternalDocumentation(
				description = "Find out more about Swagger",
				url = "http://swagger.io"
		)
)
public class EmployeeManagementSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(EmployeeManagementSystemApplication.class, args);
	}
}
