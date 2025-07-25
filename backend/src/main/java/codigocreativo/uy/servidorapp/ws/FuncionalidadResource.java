package codigocreativo.uy.servidorapp.ws;

import codigocreativo.uy.servidorapp.dtos.FuncionalidadDto;
import codigocreativo.uy.servidorapp.servicios.FuncionalidadRemote;
import codigocreativo.uy.servidorapp.excepciones.ServiciosException;
import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Path("/funcionalidades")
@Tag(name = "Funcionalidades", description = "Gestión de funcionalidades")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class FuncionalidadResource {
    @EJB
    private FuncionalidadRemote funcionalidadRemote;

    @POST
    @Path("/crear")
    @Operation(summary = "Crear una funcionalidad", description = "Crea una nueva funcionalidad en la base de datos", tags = { "Funcionalidades" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Funcionalidad creada correctamente", content = @Content(schema = @Schema(implementation = FuncionalidadDto.class))),
            @ApiResponse(responseCode = "400", description = "Solicitud inválida", content = @Content(schema = @Schema(implementation = String.class)))
    })
    public Response crear(FuncionalidadDto funcionalidad) {
        FuncionalidadDto funcionalidadCreada = this.funcionalidadRemote.crear(funcionalidad);
        return Response.status(Response.Status.CREATED)
                .entity(funcionalidadCreada)
                .build();
    }

    @PUT
    @Path("/modificar")
    @Operation(summary = "Modificar una funcionalidad", description = "Modifica una funcionalidad existente en la base de datos", tags = { "Funcionalidades" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Funcionalidad modificada correctamente", content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "404", description = "Funcionalidad no encontrada", content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "400", description = "Solicitud inválida", content = @Content(schema = @Schema(implementation = String.class)))
    })
    public Response modificar(FuncionalidadDto funcionalidad) {
        try {
            FuncionalidadDto funcionalidadActualizada = this.funcionalidadRemote.actualizar(funcionalidad);
            if (funcionalidadActualizada == null) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("{\"message\": \"Funcionalidad no encontrada\"}")
                        .build();
            }
            return Response.status(Response.Status.OK)
                    .entity("{\"message\": \"Funcionalidad modificada correctamente\"}")
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"message\": \"Error al modificar la funcionalidad: " + e.getMessage() + "\"}")
                    .build();
        }
    }

    @PUT
    @Path("/eliminar")
    @Operation(summary = "Eliminar una funcionalidad", description = "Elimina una funcionalidad existente por su ID", tags = { "Funcionalidades" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Funcionalidad eliminada correctamente", content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "404", description = "Funcionalidad no encontrada", content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "400", description = "Error al eliminar", content = @Content(schema = @Schema(implementation = String.class)))
    })
    public Response eliminar(@Parameter(description = "Datos de la funcionalidad a eliminar", required = true) FuncionalidadDto funcionalidad) {
        try {
            if (funcionalidad == null || funcionalidad.getId() == null) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("{\"message\": \"El ID de la funcionalidad es obligatorio para la eliminación\"}")
                        .build();
            }
            
            this.funcionalidadRemote.eliminar(funcionalidad.getId());
            return Response.status(Response.Status.OK)
                    .entity("{\"message\": \"Funcionalidad eliminada correctamente\"}")
                    .build();
        } catch (ServiciosException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"message\": \"" + e.getMessage() + "\"}")
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"message\": \"Error interno del servidor: " + e.getMessage() + "\"}")
                    .build();
        }
    }

    @GET
    @Path("/listar")
    @Operation(summary = "Listar funcionalidades", description = "Obtiene una lista de todas las funcionalidades", tags = { "Funcionalidades" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de funcionalidades obtenida correctamente", content = @Content(schema = @Schema(implementation = FuncionalidadDto.class)))
    })
    public List<FuncionalidadDto> listar() {
        return this.funcionalidadRemote.obtenerTodas();
    }

    @GET
    @Path("/filtrar")
    @Operation(summary = "Filtrar funcionalidades", description = "Filtra funcionalidades por nombre y/o estado", tags = { "Funcionalidades" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista filtrada correctamente", content = @Content(schema = @Schema(implementation = FuncionalidadDto.class)))
    })
    public Response filtrar(@QueryParam("nombre") String nombre, @QueryParam("estado") String estado) {
        try {
            return Response.ok(this.funcionalidadRemote.filtrarFuncionalidades(nombre, estado)).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(java.util.Map.of("error", e.getMessage()))
                    .build();
        }
    }

    @GET
    @Path("/seleccionar/{id}")
    @Operation(summary = "Buscar una funcionalidad por ID", description = "Obtiene la información de una funcionalidad específica por su ID", tags = { "Funcionalidades" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Funcionalidad encontrada", content = @Content(schema = @Schema(implementation = FuncionalidadDto.class))),
            @ApiResponse(responseCode = "404", description = "Funcionalidad no encontrada", content = @Content(schema = @Schema(implementation = String.class)))
    })
    public Response buscarPorId(@Parameter(description = "ID de la funcionalidad a buscar", required = true) @PathParam("id") Long id) {
        FuncionalidadDto funcionalidad = this.funcionalidadRemote.buscarPorId(id);
        if (funcionalidad == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"message\": \"Funcionalidad no encontrada\"}")
                    .build();
        }
        return Response.status(Response.Status.OK)
                .entity(funcionalidad)
                .build();
    }
}
