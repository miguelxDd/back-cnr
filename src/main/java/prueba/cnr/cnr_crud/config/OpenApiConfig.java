package prueba.cnr.cnr_crud.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        final String securitySchemeName = "basicAuth";
        
        return new OpenAPI()
                .info(new Info()
                        .title("API de Productos - CNR")
                        .version("1.0.0")
                        .description("API REST para la gestión de productos. " +
                                "Sistema CRUD completo con autenticación básica.")
                        .contact(new Contact()
                                .name("CNR - Centro Nacional de Registros")
                                .email("soporte@cnr.gob.sv")
                                .url("https://www.cnr.gob.sv"))
                        .license(new License()
                                .name("Licencia CNR")
                                .url("https://www.cnr.gob.sv/licencia")))
                .servers(List.of(
                        new Server()
                                .url("http://localhost:8080")
                                .description("Servidor de Desarrollo")))
                .addSecurityItem(new SecurityRequirement().addList(securitySchemeName))
                .components(new Components()
                        .addSecuritySchemes(securitySchemeName,
                                new SecurityScheme()
                                        .name(securitySchemeName)
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("basic")
                                        .description("Autenticación básica HTTP. " +
                                                "Usuarios de prueba: admin/admin123 o user/user123")));
}
}
