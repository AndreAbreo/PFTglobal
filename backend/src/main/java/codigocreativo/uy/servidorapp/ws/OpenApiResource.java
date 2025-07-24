package codigocreativo.uy.servidorapp.ws;

import codigocreativo.uy.servidorapp.config.OpenApiDocConfig;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/openapi.json")
@Tag(name = "OpenAPI", description = "Endpoint que sirve la especificación OpenAPI en formato JSON para Swagger UI y otras herramientas.")
public class OpenApiResource {
    private final OpenApiDocConfig config = new OpenApiDocConfig();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(
        summary = "Obtener especificación OpenAPI",
        description = "Devuelve el JSON de la especificación OpenAPI de la API para ser consumido por Swagger UI u otras herramientas."
    )
    public Response getOpenApi() {
        return Response.ok(config).build();
    }
} 