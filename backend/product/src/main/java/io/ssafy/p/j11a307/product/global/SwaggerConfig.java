package io.ssafy.p.j11a307.product.global;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.servers.Server;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.List;

@OpenAPIDefinition
@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {
        String jwt = "JWT";
        SecurityRequirement securityRequirement = new SecurityRequirement().addList(jwt);
        Components components = new Components().addSecuritySchemes(jwt, new SecurityScheme()
                .name(jwt)
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JWT")
        );
        return new OpenAPI()
                .info(apiInfo())
                .servers(List.of(new Server().url("/api/products")))
                .addSecurityItem(securityRequirement)
                .components(components);
    }

    private Info apiInfo() {
        return new Info()
                .title("API test") // API의 제목
                .description("streat project") // API에 대한 설명
                .version("1.0.0"); // API의 버전
    }
}
