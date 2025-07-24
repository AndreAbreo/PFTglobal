package codigocreativo.uy.servidorapp.ws;

import codigocreativo.uy.servidorapp.dtos.BajaEquipoDto;
import codigocreativo.uy.servidorapp.dtos.EquipoDto;
import codigocreativo.uy.servidorapp.servicios.BajaEquipoRemote;
import codigocreativo.uy.servidorapp.servicios.EquipoRemote;
import codigocreativo.uy.servidorapp.excepciones.ServiciosException;
import codigocreativo.uy.servidorapp.jwt.JwtService;
import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.core.HttpHeaders;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.jsonwebtoken.Claims;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Path("/equipos")
@Tag(name = "Equipos", description = "Gestión de equipos")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class EquipoResource {
    @EJB
    private EquipoRemote er;

    @EJB
    private BajaEquipoRemote ber;

    @EJB
    private JwtService jwtService;

    @POST
    @Path("/crear")
    @Operation(summary = "Crear un equipo", description = "Crea un equipo en la base de datos", tags = { "Equipos" })
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201", description = "Equipo creado correctamente", content = @Content(schema = @Schema(implementation = String.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Solicitud inválida - Campos obligatorios faltantes", content = @Content(schema = @Schema(implementation = String.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content(schema = @Schema(implementation = String.class)))
    })
    public Response crearEquipo(@Parameter(description = "Datos del equipo a crear", required = true) EquipoDto equipo) {
        if (equipo == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"error\": \"El equipo no puede ser null\"}")
                    .build();
        }
        
        try {
            this.er.crearEquipo(equipo);
            return Response.status(Response.Status.CREATED)
                    .entity("{\"message\": \"Equipo creado correctamente\"}")
                    .build();
        } catch (ServiciosException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"error\": \"" + e.getMessage() + "\"}")
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"error\": \"Error al crear el equipo: " + e.getMessage() + "\"}")
                    .build();
        }
    }

    @PUT
    @Path("/modificar")
    @Operation(summary = "Modificar un equipo", description = "Modifica la información de un equipo existente en la base de datos", tags = { "Equipos" })
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Equipo modificado correctamente"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Equipo no encontrado", content = @Content(schema = @Schema(implementation = String.class)))
    })
    public Response modificarProducto(EquipoDto equipo) {
        this.er.modificarEquipo(equipo);
        return Response.status(200).build();
    }

    @PUT
    @Path("/inactivar")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Inactivar un equipo", description = "Inactiva un equipo en la base de datos", tags = { "Equipos" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Equipo inactivado correctamente"),
            @ApiResponse(responseCode = "400", description = "Solicitud inválida - Campos obligatorios faltantes", content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "404", description = "Equipo no encontrado", content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content(schema = @Schema(implementation = String.class)))
    })
    public Response eliminarEquipo(
            @Parameter(description = "Datos de la baja del equipo", required = true) BajaEquipoDto bajaEquipo,
            @Context ContainerRequestContext requestContext,
            @Context HttpHeaders headers) {
        
        System.out.println("=== INICIO ENDPOINT INACTIVAR ===");
        System.out.println("BajaEquipo recibido: " + (bajaEquipo != null ? "NO NULL" : "NULL"));
        if (bajaEquipo != null && bajaEquipo.getIdEquipo() != null) {
            System.out.println("ID Equipo: " + bajaEquipo.getIdEquipo().getId());
            System.out.println("Razón: " + bajaEquipo.getRazon());
        }
        
        if (bajaEquipo == null) {
            System.out.println("ERROR: BajaEquipo es null");
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"error\": \"Los datos de la baja no pueden ser null\"}")
                    .build();
        }
        
        // Obtener el email del usuario desde el token JWT
        String emailUsuario = null;
        
        // Intentar obtener del contexto primero
        if (requestContext != null) {
            try {
                emailUsuario = (String) requestContext.getProperty("email");
                System.out.println("Email obtenido del contexto: " + emailUsuario);
            } catch (Exception e) {
                System.out.println("Error obteniendo email del contexto: " + e.getMessage());
                // Continuar con el método alternativo
            }
        }
        
        // Si no se pudo obtener del contexto, intentar extraer del token JWT directamente
        if (emailUsuario == null || emailUsuario.trim().isEmpty()) {
            try {
                String authorizationHeader = headers.getHeaderString(HttpHeaders.AUTHORIZATION);
                System.out.println("Authorization header: " + (authorizationHeader != null ? "PRESENTE" : "AUSENTE"));
                if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
                    String token = authorizationHeader.substring("Bearer ".length()).trim();
                    Claims claims = jwtService.parseToken(token);
                    emailUsuario = claims.get("email", String.class);
                    System.out.println("Email obtenido del token: " + emailUsuario);
                }
            } catch (Exception e) {
                System.out.println("Error parseando token: " + e.getMessage());
                return Response.status(Response.Status.UNAUTHORIZED)
                        .entity("{\"error\": \"Token JWT inválido o no proporcionado\"}")
                        .build();
            }
        }
        
        if (emailUsuario == null || emailUsuario.trim().isEmpty()) {
            System.out.println("ERROR: No se pudo obtener email del usuario");
            return Response.status(Response.Status.UNAUTHORIZED)
                    .entity("{\"error\": \"No se pudo obtener el usuario de la sesión. Verifique que el token JWT sea válido.\"}")
                    .build();
        }
        
        System.out.println("Llamando a crearBajaEquipo con email: " + emailUsuario);
        try {
            this.ber.crearBajaEquipo(bajaEquipo, emailUsuario);
            System.out.println("Baja creada exitosamente");
            return Response.status(Response.Status.OK)
                    .entity("{\"message\": \"Equipo dado de baja correctamente\"}")
                    .build();
        } catch (ServiciosException e) {
            System.out.println("ServiciosException: " + e.getMessage());
            // Manejar específicamente el caso de duplicados
            if (e.getMessage().contains("ya ha sido dado de baja")) {
                return Response.status(Response.Status.CONFLICT)
                        .entity("{\"error\": \"" + e.getMessage() + "\"}")
                        .build();
            }
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"error\": \"" + e.getMessage() + "\"}")
                    .build();
        } catch (Exception e) {
            System.out.println("Exception general: " + e.getMessage());
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"error\": \"Error al dar de baja el equipo: " + e.getMessage() + "\"}")
                    .build();
        } finally {
            System.out.println("=== FIN ENDPOINT INACTIVAR ===");
        }
    }

    @GET
    @Path("/listar")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Listar equipos", description = "Obtiene una lista de todos los equipos", tags = { "Equipos" })
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Lista de equipos obtenida correctamente", content = @Content(schema = @Schema(implementation = EquipoDto.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "No autorizado", content = @Content(schema = @Schema(implementation = String.class)))
        })
    public List<EquipoDto> obtenerTodosLosEquipos() {
        return this.er.listarEquipos();
    }

    @GET
    @Path("/seleccionar")
    @Operation(summary = "Buscar un equipo", description = "Obtiene la información de un equipo específico por su ID", tags = { "Equipos" })
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Equipo encontrado", content = @Content(schema = @Schema(implementation = EquipoDto.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Equipo no encontrado", content = @Content(schema = @Schema(implementation = String.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "ID inválido", content = @Content(schema = @Schema(implementation = String.class)))
    })
    public Response buscarEquipo(@Parameter(description = "ID del equipo a buscar", required = true) @QueryParam("id") Long id) {
        if (id == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"error\": \"El ID del equipo es obligatorio\"}")
                    .build();
        }
        
        EquipoDto equipo = this.er.obtenerEquipo(id);
        
        if (equipo == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"error\": \"Equipo no encontrado con ID: " + id + "\"}")
                    .build();
        }
        
        return Response.ok(equipo).build();
    }

    @GET
    @Path("/filtrar")
    @Operation(summary = "Filtrar equipos", description = "Filtra los equipos según los criterios proporcionados", tags = { "Equipos" })
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Lista de equipos filtrada correctamente", content = @Content(schema = @Schema(implementation = EquipoDto.class)))
    })
    public List<EquipoDto> filtrar(
            @Parameter(description = "Nombre del equipo") @QueryParam("nombre") String nombre,
            @Parameter(description = "Tipo del equipo") @QueryParam("tipo") String tipo,
            @Parameter(description = "Marca del equipo") @QueryParam("marca") String marca,
            @Parameter(description = "Modelo del equipo") @QueryParam("modelo") String modelo,
            @Parameter(description = "Número de serie del equipo") @QueryParam("numeroSerie") String numeroSerie,
            @Parameter(description = "País de origen del equipo") @QueryParam("paisOrigen") String paisOrigen,
            @Parameter(description = "Proveedor del equipo") @QueryParam("proveedor") String proveedor,
            @Parameter(description = "Fecha de adquisición del equipo") @QueryParam("fechaAdquisicion") String fechaAdquisicion,
            @Parameter(description = "Identificación interna del equipo") @QueryParam("idInterno") String idInterno,
            @Parameter(description = "Ubicación del equipo") @QueryParam("ubicacion") String ubicacion) {
        Map<String, String> filtros = new HashMap<>();
        if (nombre != null) filtros.put("nombre", nombre);
        if (tipo != null) filtros.put("tipo", tipo);
        if (marca != null) filtros.put("marca", marca);
        if (modelo != null) filtros.put("modelo", modelo);
        if (numeroSerie != null) filtros.put("numeroSerie", numeroSerie);
        if (paisOrigen != null) filtros.put("paisOrigen", paisOrigen);
        if (proveedor != null) filtros.put("proveedor", proveedor);
        if (fechaAdquisicion != null) filtros.put("fechaAdquisicion", fechaAdquisicion);
        if (idInterno != null) filtros.put("idInterno", idInterno);
        if (ubicacion != null) filtros.put("ubicacion", ubicacion);

        return this.er.obtenerEquiposFiltrado(filtros);
    }

    @GET
    @Path("/listarBajas")
    @Operation(summary = "Listar equipos dados de baja", description = "Obtiene una lista de todos los equipos dados de baja", tags = { "Equipos" })
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Lista de equipos dados de baja obtenida correctamente", content = @Content(schema = @Schema(implementation = BajaEquipoDto.class)))
    })
    public List<BajaEquipoDto> obtenerBajasEquipos() {
        return this.ber.obtenerBajasEquipos();
    }

    @GET
    @Path("/VerEquipoInactivo")
    @Operation(summary = "Obtener un equipo inactivo", description = "Obtiene la información de un equipo dado de baja por su ID", tags = { "Equipos" })
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Equipo dado de baja encontrado", content = @Content(schema = @Schema(implementation = BajaEquipoDto.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Equipo no encontrado", content = @Content(schema = @Schema(implementation = String.class)))
    })
    public BajaEquipoDto obtenerBaja(@Parameter(description = "ID del equipo dado de baja a buscar", required = true) @QueryParam("id") Long id) {
        return this.ber.obtenerBajaEquipo(id);
    }
}