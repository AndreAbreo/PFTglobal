package codigocreativo.uy.servidorapp.ws;

import codigocreativo.uy.servidorapp.dtos.TiposIntervencioneDto;
import codigocreativo.uy.servidorapp.servicios.TipoIntervencioneRemote;
import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Path("/tipoIntervenciones")
@Tag(name = "Tipos de Intervenciones", description = "Gestión de tipos de intervenciones")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@SecurityRequirement(name = "BearerAuth")
public class TipoIntervencionesResource {
    @EJB
    private TipoIntervencioneRemote er;

    @POST
    @Path("/crear")
    @Operation(summary = "Crear un tipo de intervención", description = "Crea un nuevo tipo de intervención en la base de datos", tags = { "Tipos de Intervenciones" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Tipo de intervención creado correctamente"),
            @ApiResponse(responseCode = "400", description = "Solicitud inválida", content = @Content(schema = @Schema(implementation = String.class)))
    })
    public Response crear(@Parameter(description = "Datos del tipo de intervención a crear", required = true) TiposIntervencioneDto p) {
        this.er.crearTipoIntervencion(p);
        return Response.status(201).build();
    }

    @PUT
    @Path("/modificar")
    @Operation(summary = "Modificar un tipo de intervención", description = "Modifica la información de un tipo de intervención existente en la base de datos", tags = { "Tipos de Intervenciones" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tipo de intervención modificado correctamente"),
            @ApiResponse(responseCode = "404", description = "Tipo de intervención no encontrado", content = @Content(schema = @Schema(implementation = String.class)))
    })
    public Response modificar(@Parameter(description = "Datos del tipo de intervención a modificar", required = true) TiposIntervencioneDto p) {
        this.er.modificarTipoIntervencion(p);
        return Response.status(200).build();
    }

    @DELETE
    @Path("/inactivar")
    @Operation(summary = "Inactivar un tipo de intervención", description = "Inactiva un tipo de intervención en la base de datos", tags = { "Tipos de Intervenciones" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tipo de intervención inactivado correctamente"),
            @ApiResponse(responseCode = "404", description = "Tipo de intervención no encontrado", content = @Content(schema = @Schema(implementation = String.class)))
    })
    public Response eliminar(@Parameter(description = "ID del tipo de intervención a inactivar", required = true) @QueryParam("id") Long id) {
        this.er.eliminarTipoIntervencion(id);
        return Response.status(200).build();
    }

    @GET
    @Path("/listar")
    @Operation(summary = "Listar todos los tipos de intervenciones", description = "Obtiene una lista de todos los tipos de intervenciones", tags = { "Tipos de Intervenciones" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de tipos de intervenciones obtenida correctamente", content = @Content(schema = @Schema(implementation = TiposIntervencioneDto.class)))
    })
    public List<TiposIntervencioneDto> listarTodos() {
        return this.er.obtenerTiposIntervenciones();
    }

    @GET
    @Path("/seleccionar")
    @Operation(summary = "Buscar un tipo de intervención por ID", description = "Obtiene la información de un tipo de intervención específico por su ID", tags = { "Tipos de Intervenciones" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tipo de intervención encontrado", content = @Content(schema = @Schema(implementation = TiposIntervencioneDto.class))),
            @ApiResponse(responseCode = "404", description = "Tipo de intervención no encontrado", content = @Content(schema = @Schema(implementation = String.class)))
    })
    public TiposIntervencioneDto buscarPorId(@Parameter(description = "ID del tipo de intervención a buscar", required = true) @QueryParam("id") Long id) {
        return this.er.obtenerTipoIntervencion(id);
    }

    @GET
    @Path("/filtrar")
    @Operation(summary = "Filtrar tipos de intervención por estado y/o nombre", description = "Obtiene una lista filtrada de tipos de intervención", tags = { "Tipos de Intervenciones" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista filtrada correctamente"),
            @ApiResponse(responseCode = "400", description = "Estado inválido", content = @Content(schema = @Schema(example = "{\"error\": \"Estado inválido\"}")))
    })
    public Response filtrarTiposIntervenciones(@QueryParam("estado") String estado, @QueryParam("nombre") String nombre) {
        try {
            return Response.ok(er.filtrarTiposIntervenciones(estado, nombre)).build();
        } catch (Exception e) {
            return Response.status(400).entity(java.util.Map.of("error", "Estado inválido: " + e.getMessage())).build();
        }
    }
}
