package codigocreativo.uy.servidorapp.ws;

import codigocreativo.uy.servidorapp.dtos.IntervencionDto;
import codigocreativo.uy.servidorapp.excepciones.ServiciosException;
import codigocreativo.uy.servidorapp.servicios.IntervencionRemote;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/intervenciones")
@Tag(name = "Intervenciones", description = "Gestión de intervenciones")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class IntervencionesResource {
    @EJB
    private IntervencionRemote er;

    @POST
    @Path("/crear")
    @Operation(summary = "Crear una intervención", description = "Crea una nueva intervención en la base de datos", tags = {
            "Intervenciones" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Intervención creada correctamente"),
            @ApiResponse(responseCode = "400", description = "Solicitud inválida", content = @Content(schema = @Schema(implementation = String.class)))
    })
    public Response crear(IntervencionDto p) throws ServiciosException {
        this.er.crear(p);
        return Response.status(201).build();
    }

    @PUT
    @Path("/modificar")
    @Operation(summary = "Modificar una intervención", description = "Modifica una intervención existente en la base de datos", tags = {
            "Intervenciones" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Intervención modificada correctamente"),
            @ApiResponse(responseCode = "404", description = "Intervención no encontrada", content = @Content(schema = @Schema(implementation = String.class)))
    })
    public Response modificar(
            @RequestBody(description = "Datos de la intervención a modificar", required = true, content = @Content(schema = @Schema(implementation = IntervencionDto.class))) IntervencionDto p)
            throws ServiciosException {
        this.er.actualizar(p);
        return Response.status(200).build();
    }

    @GET
    @Path("/listar")
    @Operation(summary = "Listar intervenciones", description = "Obtiene una lista de todas las intervenciones", tags = {
            "Intervenciones" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de intervenciones obtenida correctamente", content = @Content(schema = @Schema(implementation = IntervencionDto.class)))
    })
    public List<IntervencionDto> listar() throws ServiciosException {
        return this.er.obtenerTodas();
    }

    @GET
    @Path("/buscar/{id}")
    @Operation(summary = "Buscar una intervención por ID", description = "Obtiene la información de una intervención específica por su ID", tags = {
            "Intervenciones" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Intervención encontrada", content = @Content(schema = @Schema(implementation = IntervencionDto.class))),
            @ApiResponse(responseCode = "404", description = "Intervención no encontrada", content = @Content(schema = @Schema(implementation = String.class)))
    })
    public Response buscarPorId(
            @Parameter(description = "ID de la intervención a buscar", required = true) @PathParam("id") Long id)
            throws ServiciosException {
        IntervencionDto intervencion = this.er.buscarId(id);
        if (intervencion == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"error\":\"Intervención no encontrada\"}")
                    .build();
        }
        return Response.ok(intervencion).build();
    }

    @GET
    @Path("/reportePorFechas")
    @Operation(summary = "Obtener intervenciones por rango de fechas", description = "Obtiene una lista de intervenciones en un rango de fechas especificado", tags = {
            "Intervenciones" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de intervenciones obtenida correctamente", content = @Content(schema = @Schema(implementation = IntervencionDto.class)))
    })
    public List<IntervencionDto> obtenerPorRangoDeFecha(
            @Parameter(description = "Fecha desde en formato ISO8601", required = true) @QueryParam("fechaDesde") String fechaDesde,
            @Parameter(description = "Fecha hasta en formato ISO8601", required = true) @QueryParam("fechaHasta") String fechaHasta,
            @Parameter(description = "ID del equipo") @QueryParam("idEquipo") Long idEquipo) throws ServiciosException {
        LocalDateTime desde = LocalDateTime.parse(fechaDesde);
        LocalDateTime hasta = LocalDateTime.parse(fechaHasta);
        return this.er.obtenerPorRangoDeFecha(desde, hasta, idEquipo);
    }

    @GET
    @Path("/reportePorTipo")
    @Operation(summary = "Obtener cantidad de intervenciones por tipo", description = "Obtiene un reporte de la cantidad de intervenciones por tipo en un rango de fechas especificado", tags = {
            "Intervenciones" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reporte obtenido correctamente", content = @Content(schema = @Schema(implementation = Map.class)))
    })
    public Map<String, Long> obtenerCantidadPorTipo(
            @Parameter(description = "Fecha desde en formato ISO8601") @QueryParam("fechaDesde") String fechaDesde,
            @Parameter(description = "Fecha hasta en formato ISO8601") @QueryParam("fechaHasta") String fechaHasta,
            @Parameter(description = "ID del tipo de intervención") @QueryParam("idTipo") Long idTipo)
            throws ServiciosException {
        LocalDateTime desde = fechaDesde != null ? LocalDateTime.parse(fechaDesde) : null;
        LocalDateTime hasta = fechaHasta != null ? LocalDateTime.parse(fechaHasta) : null;
        return this.er.obtenerCantidadPorTipo(desde, hasta, idTipo);
    }

    @GET
    @Path("/filtrar")
    @Operation(summary = "Filtrar intervenciones", description = "Filtra las intervenciones según técnico, tipo y estado", tags = { "Intervenciones" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista filtrada correctamente", content = @Content(schema = @Schema(implementation = IntervencionDto.class)))
    })
    public List<IntervencionDto> filtrar(
            @Parameter(description = "Técnico que realizó la intervención") @QueryParam("technician") String technician,
            @Parameter(description = "Tipo de intervención") @QueryParam("type") String type,
            @Parameter(description = "Estado del técnico") @QueryParam("estado") String estado) throws ServiciosException {
        return this.er.filtrarIntervenciones(technician, type, estado);
    }
}
