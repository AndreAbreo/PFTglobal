package codigocreativo.uy.servidorapp.ws;

import codigocreativo.uy.servidorapp.dtos.PerfilDto;
import codigocreativo.uy.servidorapp.enumerados.Estados;
import codigocreativo.uy.servidorapp.excepciones.ServiciosException;
import codigocreativo.uy.servidorapp.servicios.PerfilRemote;
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


@Path("/perfiles")
@Tag(name = "Perfiles", description = "Gestión de perfiles de usuario")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PerfilResource {
    private static final String ERROR_JSON_FORMAT = "{\"error\":\"%s\"}";
    private static final String MSG_JSON_FORMAT = "{\"message\":\"%s\"}";

    @EJB
    private PerfilRemote perfilRemote;

    @POST
    @Path("/crear")
    @Operation(summary = "Crear un perfil", description = "Crea un nuevo perfil de usuario", tags = { "Perfiles" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Perfil creado correctamente", content = @Content(schema = @Schema(implementation = PerfilDto.class))),
            @ApiResponse(responseCode = "400", description = "Solicitud inválida", content = @Content(schema = @Schema(implementation = String.class)))
    })
    @SecurityRequirement(name = "BearerAuth")
    public Response crearPerfil(PerfilDto perfil) {
        if (perfil == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(String.format(ERROR_JSON_FORMAT, "El perfil no puede ser null"))
                    .build();
        }

        try {
            PerfilDto perfilCreado = perfilRemote.crearPerfil(perfil);
            return Response.status(Response.Status.CREATED)
                    .entity(perfilCreado)
                    .build();
        } catch (ServiciosException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(String.format(ERROR_JSON_FORMAT, e.getMessage()))
                    .build();
        }
    }


    @PUT
    @Path("/modificar")
    @Operation(summary = "Modificar un perfil", description = "Modifica un perfil existente", tags = { "Perfiles" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Perfil modificado correctamente"),
            @ApiResponse(responseCode = "400", description = "Solicitud inválida", content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "404", description = "Perfil no encontrado", content = @Content(schema = @Schema(implementation = String.class)))
    })
    @SecurityRequirement(name = "BearerAuth")
    public Response modificarPerfil(PerfilDto perfil) {
        if (perfil == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"error\":\"El perfil no puede ser null\"}")
                    .build();
        }

        try {
            perfilRemote.modificarPerfil(perfil);
            return Response.ok().build();
        } catch (ServiciosException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"error\":\"" + e.getMessage() + "\"}")
                    .build();
        }
    }

    @PUT
    @Path("/inactivar")
    @Operation(summary = "Inactivar un perfil", description = "Inactiva un perfil existente", tags = { "Perfiles" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Perfil inactivado correctamente"),
            @ApiResponse(responseCode = "400", description = "Solicitud inválida", content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "404", description = "Perfil no encontrado", content = @Content(schema = @Schema(implementation = String.class)))
    })
    @SecurityRequirement(name = "BearerAuth")
    public Response eliminarPerfil(@Parameter(description = "ID del perfil a inactivar", required = true) @QueryParam("id") Long id) {
        // Validar que el ID no sea null
        if (id == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(String.format(ERROR_JSON_FORMAT, "El ID del perfil es obligatorio"))
                    .build();
        }

        try {
            PerfilDto perfil = perfilRemote.obtenerPerfil(id);
            if (perfil != null) {
                perfilRemote.eliminarPerfil(perfil);
                return Response.ok()
                        .entity(String.format(MSG_JSON_FORMAT, "Perfil inactivado correctamente"))
                        .build();
            } else {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity(String.format(ERROR_JSON_FORMAT, "Perfil no encontrado con ID: " + id))
                        .build();
            }
        } catch (ServiciosException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(String.format(ERROR_JSON_FORMAT, e.getMessage()))
                    .build();
        }
    }

    @GET
    @Path("/seleccionar")
    @Operation(summary = "Obtener un perfil por ID", description = "Obtiene la información de un perfil específico por su ID", tags = { "Perfiles" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Perfil encontrado", content = @Content(schema = @Schema(implementation = PerfilDto.class))),
            @ApiResponse(responseCode = "404", description = "Perfil no encontrado", content = @Content(schema = @Schema(implementation = String.class)))
    })
    public Response obtenerPerfil(@Parameter(description = "ID del perfil a buscar", required = true) @QueryParam("id") Long id) {
        PerfilDto perfil = perfilRemote.obtenerPerfil(id);
        if (perfil != null) {
            return Response.ok(perfil).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @GET
    @Path("/listar")
    @Operation(summary = "Listar todos los perfiles", description = "Obtiene una lista de todos los perfiles de usuario", tags = { "Perfiles" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de perfiles obtenida correctamente", content = @Content(schema = @Schema(implementation = PerfilDto.class)))
    })
    public List<PerfilDto> obtenerPerfiles() {
        return perfilRemote.obtenerPerfiles();
    }

    @GET
    @Path("/buscarPorNombre")
    @Operation(summary = "Buscar perfiles por nombre", description = "Obtiene una lista de perfiles que coinciden con el nombre especificado", tags = { "Perfiles" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de perfiles obtenida correctamente", content = @Content(schema = @Schema(implementation = PerfilDto.class)))
    })
    public List<PerfilDto> listarPerfilesPorNombre(@Parameter(description = "Nombre del perfil a buscar") @QueryParam("nombre") String nombre) {
        return perfilRemote.listarPerfilesPorNombre(nombre);
    }

    @GET
    @Path("/buscarPorEstado")
    @Operation(summary = "Buscar perfiles por estado", description = "Obtiene una lista de perfiles que coinciden con el estado especificado", tags = { "Perfiles" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de perfiles obtenida correctamente", content = @Content(schema = @Schema(implementation = PerfilDto.class)))
    })
    public List<PerfilDto> listarPerfilesPorEstado(@Parameter(description = "Estado del perfil a buscar") @QueryParam("estado") Estados estado) {
        return perfilRemote.listarPerfilesPorEstado(estado);
    }
}
