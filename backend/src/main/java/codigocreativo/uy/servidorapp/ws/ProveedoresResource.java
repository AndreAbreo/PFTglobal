package codigocreativo.uy.servidorapp.ws;

import codigocreativo.uy.servidorapp.dtos.ProveedoresEquipoDto;
import codigocreativo.uy.servidorapp.enumerados.Estados;
import codigocreativo.uy.servidorapp.servicios.ProveedoresEquipoRemote;
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

@Path("/proveedores")
@Tag(name = "Proveedores", description = "Gestión de proveedores de equipos")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@SecurityRequirement(name = "BearerAuth")
public class ProveedoresResource {
    private static final String MESSAGE = "message";
    private static final String ERROR = "error";
    
    @EJB
    private ProveedoresEquipoRemote er;

    @POST
    @Path("/crear")
    @Operation(summary = "Crear un proveedor", description = "Crea un nuevo proveedor de equipos en la base de datos", tags = { "Proveedores" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Proveedor creado correctamente", content = @Content(schema = @Schema(example = "{\"message\": \"Proveedor creado correctamente\"}"))),
            @ApiResponse(responseCode = "400", description = "Solicitud inválida", content = @Content(schema = @Schema(example = "{\"error\": \"Ya existe un proveedor con ese nombre\"}")))
    })
    public Response crearProveedor(ProveedoresEquipoDto p) {
        try {
            this.er.crearProveedor(p);
            return Response.status(201).entity(java.util.Map.of(MESSAGE, "Proveedor creado correctamente")).build();
        } catch (Exception e) {
            return Response.status(400).entity(java.util.Map.of(ERROR, e.getMessage())).build();
        }
    }

    @PUT
    @Path("/modificar")
    @Operation(summary = "Modificar un proveedor", description = "Modifica la información de un proveedor existente en la base de datos", tags = { "Proveedores" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Proveedor modificado correctamente", content = @Content(schema = @Schema(example = "{\"message\": \"Proveedor modificado correctamente\"}"))),
            @ApiResponse(responseCode = "400", description = "Solicitud inválida", content = @Content(schema = @Schema(example = "{\"error\": \"Ya existe otro proveedor con ese nombre\"}")))
    })
    public Response modificarProveedor(ProveedoresEquipoDto p) {
        try {
            this.er.modificarProveedor(p);
            return Response.ok(java.util.Map.of(MESSAGE, "Proveedor modificado correctamente")).build();
        } catch (Exception e) {
            return Response.status(400).entity(java.util.Map.of(ERROR, e.getMessage())).build();
        }
    }

    @PUT
    @Path("/inactivar")
    @Operation(summary = "Inactivar un proveedor", description = "Inactiva un proveedor en la base de datos", tags = { "Proveedores" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Proveedor inactivado correctamente", content = @Content(schema = @Schema(example = "{\"message\": \"Proveedor inactivado correctamente\"}"))),
            @ApiResponse(responseCode = "404", description = "Proveedor no encontrado", content = @Content(schema = @Schema(example = "{\"error\": \"Proveedor no encontrado\"}")))
    })
    public Response eliminarProveedor(@Parameter(description = "ID del proveedor a inactivar", required = true) @QueryParam("id") Long id) {
        try {
            this.er.eliminarProveedor(id);
            return Response.ok(java.util.Map.of(MESSAGE, "Proveedor inactivado correctamente")).build();
        } catch (Exception e) {
            return Response.status(404).entity(java.util.Map.of(ERROR, e.getMessage())).build();
        }
    }

    @GET
    @Path("/listar")
    @Operation(summary = "Listar todos los proveedores", description = "Obtiene una lista de todos los proveedores de equipos ordenados alfabéticamente", tags = { "Proveedores" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de proveedores obtenida correctamente", content = @Content(schema = @Schema(implementation = ProveedoresEquipoDto.class)))
    })
    public List<ProveedoresEquipoDto> listarProveedores() {
        return this.er.obtenerProveedores();
    }

    @GET
    @Path("/seleccionar")
    @Operation(summary = "Buscar un proveedor por ID", description = "Obtiene la información de un proveedor específico por su ID", tags = { "Proveedores" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Proveedor encontrado", content = @Content(schema = @Schema(implementation = ProveedoresEquipoDto.class))),
            @ApiResponse(responseCode = "404", description = "Proveedor no encontrado", content = @Content(schema = @Schema(example = "{\"error\": \"Proveedor no encontrado\"}")))
    })
    public Response buscarPorId(@Parameter(description = "ID del proveedor a buscar", required = true) @QueryParam("id") Long id) {
        ProveedoresEquipoDto proveedor = this.er.obtenerProveedor(id);
        if (proveedor == null) {
            return Response.status(404).entity(java.util.Map.of(ERROR, "Proveedor no encontrado")).build();
        }
        return Response.ok(proveedor).build();
    }

    @GET
    @Path("/filtrar")
    @Operation(summary = "Filtrar proveedores por nombre y/o estado", description = "Obtiene una lista de proveedores filtrados por nombre y/o estado y ordenados alfabéticamente. Si no se proporcionan parámetros, devuelve todos los proveedores.", tags = { "Proveedores" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de proveedores filtrada correctamente", content = @Content(schema = @Schema(implementation = ProveedoresEquipoDto.class))),
            @ApiResponse(responseCode = "400", description = "Estado inválido", content = @Content(schema = @Schema(example = "{\"error\": \"Estado inválido\"}")))
    })
    public Response filtrarProveedores(@QueryParam("nombre") String nombre,
                                       @QueryParam("estado") String estado,
                                       @QueryParam("pais") String pais) {
        try {
            return Response.ok(this.er.filtrarProveedores(nombre, estado, pais)).build();
        } catch (Exception e) {
            return Response.status(400).entity(java.util.Map.of(ERROR, "Estado inválido: " + e.getMessage())).build();
        }
    }
}
