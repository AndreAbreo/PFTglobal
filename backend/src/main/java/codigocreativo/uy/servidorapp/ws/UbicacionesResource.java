package codigocreativo.uy.servidorapp.ws;

import codigocreativo.uy.servidorapp.dtos.UbicacionDto;
import codigocreativo.uy.servidorapp.excepciones.ServiciosException;
import codigocreativo.uy.servidorapp.servicios.UbicacionRemote;
import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.Parameter;

import java.util.List;

@Path("/ubicaciones")
@Tag(name = "Ubicaciones", description = "Gestión de ubicaciones")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@SecurityRequirement(name = "BearerAuth")
public class UbicacionesResource {
    private static final String MESSAGE = "message";
    private static final String ERROR = "error";
    
    @EJB
    private UbicacionRemote er;

    @GET
    @Path("/listar")
    @Operation(summary = "Listar todas las ubicaciones", description = "Obtiene una lista de todas las ubicaciones disponibles", tags = { "Ubicaciones" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de ubicaciones obtenida correctamente", content = @Content(schema = @Schema(implementation = UbicacionDto.class))),
            @ApiResponse(responseCode = "500", description = "Error al listar ubicaciones", 
                content = @Content(schema = @Schema(example = "{\"error\": \"Error al listar ubicaciones\"}")))
    })
    public Response listarUbicaciones() {
        try {
            List<UbicacionDto> ubicaciones = this.er.listarUbicaciones();
            return Response.ok(ubicaciones).build();
        } catch (ServiciosException e) {
            return Response.status(500)
                .entity(java.util.Map.of(ERROR, e.getMessage()))
                .build();
        }
    }

    @GET
    @Path("/seleccionar")
    @Operation(summary = "Buscar una ubicación por ID", description = "Obtiene la información de una ubicación específica por su ID", tags = { "Ubicaciones" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ubicación encontrada", content = @Content(schema = @Schema(implementation = UbicacionDto.class))),
            @ApiResponse(responseCode = "400", description = "Error al buscar la ubicación", 
                content = @Content(schema = @Schema(example = "{\"error\": \"Error al buscar la ubicación\"}")))
    })
    public Response buscarPorId(@Parameter(description = "ID de la ubicación a buscar", required = true) @QueryParam("id") Long id) {
        try {
            UbicacionDto ubicacion = this.er.obtenerUbicacionPorId(id);
            return Response.ok(ubicacion).build();
        } catch (ServiciosException e) {
            return Response.status(400)
                .entity(java.util.Map.of(ERROR, e.getMessage()))
                .build();
        }
    }
}
