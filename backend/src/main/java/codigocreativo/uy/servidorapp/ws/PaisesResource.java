package codigocreativo.uy.servidorapp.ws;

import codigocreativo.uy.servidorapp.dtos.PaisDto;
import codigocreativo.uy.servidorapp.servicios.PaisRemote;
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

import java.util.List;

@Path("/paises")
@Tag(name = "Paises", description = "Gestión de países")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@SecurityRequirement(name = "BearerAuth")
public class PaisesResource {
    private static final String ERROR = "error";
    @EJB
    private PaisRemote er;

    @POST
    @Path("/crear")
    @Operation(summary = "Crear un país", description = "Crea un nuevo país en la base de datos", tags = { "Paises" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "País creado correctamente", content = @Content(schema = @Schema(example = "{\"message\": \"País creado correctamente\"}"))),
            @ApiResponse(responseCode = "400", description = "Solicitud inválida", content = @Content(schema = @Schema(example = "{\"error\": \"Ya existe un país con ese nombre\"}")))
    })
    public Response crearPais(PaisDto p) {
        try {
            this.er.crearPais(p);
            return Response.status(201).entity(java.util.Map.of("message", "País creado correctamente")).build();
        } catch (Exception e) {
            return Response.status(400).entity(java.util.Map.of(ERROR, e.getMessage())).build();
        }
    }

    @PUT
    @Path("/modificar")
    @Operation(summary = "Modificar un país", description = "Modifica la información de un país existente en la base de datos", tags = { "Paises" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "País modificado correctamente"),
            @ApiResponse(responseCode = "404", description = "País no encontrado", content = @Content(schema = @Schema(implementation = String.class)))
    })
    public Response modificarPais(PaisDto p) {
        this.er.modificarPais(p);
        return Response.status(200).build();
    }

    @GET
    @Path("/listar")
    @Operation(summary = "Listar todos los países", description = "Obtiene una lista de todos los países", tags = { "Paises" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de países obtenida correctamente", content = @Content(schema = @Schema(implementation = PaisDto.class)))
    })
    public List<PaisDto> listarPaises() {
        return this.er.obtenerpais();
    }

    @GET
    @Path("/seleccionar")
    @Operation(summary = "Buscar un país por ID", description = "Obtiene la información de un país específico por su ID", tags = { "Paises" })
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "País encontrado", content = @Content(schema = @Schema(implementation = PaisDto.class))),
        @ApiResponse(responseCode = "404", description = "País no encontrado", content = @Content(schema = @Schema(example = "{\"error\": \"País no encontrado\"}")))
    })
    public Response seleccionarPais(@QueryParam("id") Long id) {
        PaisDto pais = this.er.obtenerPaisPorId(id);
        if (pais == null) {
            return Response.status(404).entity(java.util.Map.of(ERROR, "País no encontrado")).build();
        }
        return Response.ok(pais).build();
    }

    @GET
    @Path("/filtrar")
    @Operation(summary = "Filtrar países por estado y/o nombre", description = "Obtiene una lista de países filtrados por estado y/o nombre y ordenados alfabéticamente. Si no se proporcionan parámetros, devuelve todos los países.", tags = { "Paises" })
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de países filtrada correctamente", content = @Content(schema = @Schema(implementation = PaisDto.class))),
        @ApiResponse(responseCode = "400", description = "Estado inválido", content = @Content(schema = @Schema(example = "{\"error\": \"Estado inválido\"}")))
    })
    public Response filtrarPorEstado(@QueryParam("estado") String estado, @QueryParam("nombre") String nombre) {
        try {
            return Response.ok(this.er.filtrarPaises(estado, nombre)).build();
        } catch (Exception e) {
            return Response.status(400).entity(java.util.Map.of(ERROR, "Estado inválido: " + e.getMessage())).build();
        }
    }

    @PUT
    @Path("/inactivar")
    @Operation(summary = "Inactivar un país", description = "Inactiva un país en la base de datos", tags = { "Paises" })
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "País desactivado correctamente", content = @Content(schema = @Schema(example = "{\"message\": \"País desactivado correctamente\"}"))),
        @ApiResponse(responseCode = "404", description = "País no encontrado", content = @Content(schema = @Schema(example = "{\"error\": \"País no encontrado\"}")))
    })
    public Response inactivarPais(@QueryParam("id") Long id) {
        try {
            this.er.inactivarPais(id);
            return Response.ok(java.util.Map.of("message", "País desactivado correctamente")).build();
        } catch (Exception e) {
            return Response.status(404).entity(java.util.Map.of(ERROR, "País no encontrado: " + e.getMessage())).build();
        }
    }
}
