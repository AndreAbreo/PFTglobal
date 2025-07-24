package codigocreativo.uy.servidorapp.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;

@OpenAPIDefinition(
    info = @Info(
        title = "API de Gestión de Mantenimiento de equipos biomedicos",
        version = "1.0",
        description = "API para la gestión del mantenimiento de equipos biomedicos en hospitales.\n\nPara autenticación, usa el endpoint: POST /api/usuarios/login para obtener tu JWT.",
        contact = @Contact(
            name = "Código Creativo",
            email = "contacto@codigocreativo.uy",
            url = "https://codigocreativo.ddns.net"
        ),
        license = @License(
            name = "Apache 2.0",
            url = "http://www.apache.org/licenses/LICENSE-2.0.html"
        )
    ),
    servers = {
        @Server(url = "http://codigocreativo.ddns.net:8080/ServidorApp-1.0-SNAPSHOT/", description = "Servidor de desarrollo")
    },
    security = @SecurityRequirement(name = "bearerAuth")
)
@SecurityScheme(
    name = "bearerAuth",
    type = SecuritySchemeType.HTTP,
    scheme = "bearer",
    bearerFormat = "JWT",
    description = "JWT token de autenticación. Obtén tu token usando el endpoint: POST /api/usuarios/login"
)
public class OpenApiDocConfig {} 