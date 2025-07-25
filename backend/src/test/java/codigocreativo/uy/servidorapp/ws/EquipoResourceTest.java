package codigocreativo.uy.servidorapp.ws;

import codigocreativo.uy.servidorapp.dtos.BajaEquipoDto;
import codigocreativo.uy.servidorapp.dtos.EquipoDto;
import codigocreativo.uy.servidorapp.servicios.BajaEquipoRemote;
import codigocreativo.uy.servidorapp.servicios.EquipoRemote;
import codigocreativo.uy.servidorapp.excepciones.ServiciosException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.core.HttpHeaders;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class EquipoResourceTest {

    @InjectMocks
    private EquipoResource equipoResource;

    @Mock
    private EquipoRemote equipoRemote;

    @Mock
    private BajaEquipoRemote bajaEquipoRemote;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCrearEquipo_Success() throws ServiciosException {
        EquipoDto equipo = new EquipoDto();

        doNothing().when(equipoRemote).crearEquipo(any(EquipoDto.class));

        Response response = equipoResource.crearEquipo(equipo);

        assertEquals(Response.Status.CREATED.getStatusCode(), response.getStatus());
        String responseBody = (String) response.getEntity();
        assertTrue(responseBody.contains("Equipo creado correctamente"));
        verify(equipoRemote, times(1)).crearEquipo(any(EquipoDto.class));
    }

    @Test
    void testCrearEquipo_WithNullEquipo() throws ServiciosException {
        Response response = equipoResource.crearEquipo(null);

        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
        String responseBody = (String) response.getEntity();
        assertTrue(responseBody.contains("El equipo no puede ser null"));
        verify(equipoRemote, never()).crearEquipo(any());
    }

    @ParameterizedTest
    @ValueSource(strings = {
        "El nombre del equipo es obligatorio",
        "Ya existe un equipo con la identificación interna: INT-001",
        "Ya existe un equipo con el número de serie: 123456789"
    })
    void testCrearEquipo_WithValidationErrors(String errorMessage) throws ServiciosException {
        EquipoDto equipo = new EquipoDto();
        
        doThrow(new ServiciosException(errorMessage)).when(equipoRemote).crearEquipo(any(EquipoDto.class));

        Response response = equipoResource.crearEquipo(equipo);

        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
        String responseBody = (String) response.getEntity();
        assertTrue(responseBody.contains(errorMessage));
        verify(equipoRemote, times(1)).crearEquipo(any(EquipoDto.class));
    }

    @Test
    void testCrearEquipo_WithInternalError() throws ServiciosException {
        EquipoDto equipo = new EquipoDto();
        String errorMessage = "Error de base de datos";
        
        doThrow(new RuntimeException(errorMessage)).when(equipoRemote).crearEquipo(any(EquipoDto.class));

        Response response = equipoResource.crearEquipo(equipo);

        assertEquals(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), response.getStatus());
        String responseBody = (String) response.getEntity();
        assertTrue(responseBody.contains("Error al crear el equipo"));
        verify(equipoRemote, times(1)).crearEquipo(any(EquipoDto.class));
    }

    @Test
    void testModificarEquipo() {
        EquipoDto equipo = new EquipoDto();

        doNothing().when(equipoRemote).modificarEquipo(any(EquipoDto.class));

        Response response = equipoResource.modificarProducto(equipo);

        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        verify(equipoRemote, times(1)).modificarEquipo(any(EquipoDto.class));
    }

    @Test
    void testEliminarEquipo_Success() throws ServiciosException {
        BajaEquipoDto bajaEquipo = new BajaEquipoDto();
        ContainerRequestContext requestContext = mock(ContainerRequestContext.class);
        HttpHeaders headers = mock(HttpHeaders.class);
        
        when(requestContext.getProperty("email")).thenReturn("test@test.com");
        doNothing().when(bajaEquipoRemote).crearBajaEquipo(any(BajaEquipoDto.class), any(String.class));

        Response response = equipoResource.eliminarEquipo(bajaEquipo, requestContext, headers);

        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        String responseBody = (String) response.getEntity();
        assertTrue(responseBody.contains("Equipo dado de baja correctamente"));
        verify(bajaEquipoRemote, times(1)).crearBajaEquipo(bajaEquipo, "test@test.com");
    }

    @Test
    void testEliminarEquipo_WithNullBajaEquipo() throws ServiciosException {
        ContainerRequestContext requestContext = mock(ContainerRequestContext.class);
        HttpHeaders headers = mock(HttpHeaders.class);
        
        Response response = equipoResource.eliminarEquipo(null, requestContext, headers);

        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
        String responseBody = (String) response.getEntity();
        assertTrue(responseBody.contains("Los datos de la baja no pueden ser null"));
        verify(bajaEquipoRemote, never()).crearBajaEquipo(any(), any());
    }

    @Test
    void testEliminarEquipo_WithNullEmailUsuario() throws ServiciosException {
        BajaEquipoDto bajaEquipo = new BajaEquipoDto();
        ContainerRequestContext requestContext = mock(ContainerRequestContext.class);
        HttpHeaders headers = mock(HttpHeaders.class);
        
        when(requestContext.getProperty("email")).thenReturn(null);

        Response response = equipoResource.eliminarEquipo(bajaEquipo, requestContext, headers);

        assertEquals(Response.Status.UNAUTHORIZED.getStatusCode(), response.getStatus());
        String responseBody = (String) response.getEntity();
        assertTrue(responseBody.contains("No se pudo obtener el usuario de la sesión"));
        verify(bajaEquipoRemote, never()).crearBajaEquipo(any(), any());
    }

    @Test
    void testEliminarEquipo_WithEmptyEmailUsuario() throws ServiciosException {
        BajaEquipoDto bajaEquipo = new BajaEquipoDto();
        ContainerRequestContext requestContext = mock(ContainerRequestContext.class);
        HttpHeaders headers = mock(HttpHeaders.class);
        
        when(requestContext.getProperty("email")).thenReturn("   ");

        Response response = equipoResource.eliminarEquipo(bajaEquipo, requestContext, headers);

        assertEquals(Response.Status.UNAUTHORIZED.getStatusCode(), response.getStatus());
        String responseBody = (String) response.getEntity();
        assertTrue(responseBody.contains("No se pudo obtener el usuario de la sesión"));
        verify(bajaEquipoRemote, never()).crearBajaEquipo(any(), any());
    }

    @ParameterizedTest
    @ValueSource(strings = {
        "La razón de la baja es obligatoria",
        "El equipo es obligatorio",
        "Usuario no encontrado con el email: test@test.com"
    })
    void testEliminarEquipo_WithValidationErrors(String errorMessage) throws ServiciosException {
        BajaEquipoDto bajaEquipo = new BajaEquipoDto();
        ContainerRequestContext requestContext = mock(ContainerRequestContext.class);
        HttpHeaders headers = mock(HttpHeaders.class);
        
        when(requestContext.getProperty("email")).thenReturn("test@test.com");
        doThrow(new ServiciosException(errorMessage)).when(bajaEquipoRemote).crearBajaEquipo(any(BajaEquipoDto.class), any(String.class));

        Response response = equipoResource.eliminarEquipo(bajaEquipo, requestContext, headers);

        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
        String responseBody = (String) response.getEntity();
        assertTrue(responseBody.contains(errorMessage));
        verify(bajaEquipoRemote, times(1)).crearBajaEquipo(bajaEquipo, "test@test.com");
    }

    @Test
    void testEliminarEquipo_WithInternalError() throws ServiciosException {
        BajaEquipoDto bajaEquipo = new BajaEquipoDto();
        ContainerRequestContext requestContext = mock(ContainerRequestContext.class);
        HttpHeaders headers = mock(HttpHeaders.class);
        String errorMessage = "Error de base de datos";
        
        when(requestContext.getProperty("email")).thenReturn("test@test.com");
        doThrow(new RuntimeException(errorMessage)).when(bajaEquipoRemote).crearBajaEquipo(any(BajaEquipoDto.class), any(String.class));

        Response response = equipoResource.eliminarEquipo(bajaEquipo, requestContext, headers);

        assertEquals(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), response.getStatus());
        String responseBody = (String) response.getEntity();
        assertTrue(responseBody.contains("Error al dar de baja el equipo"));
        verify(bajaEquipoRemote, times(1)).crearBajaEquipo(bajaEquipo, "test@test.com");
    }

    @Test
    void testObtenerTodosLosEquipos() {
        List<EquipoDto> equipos = List.of(new EquipoDto(), new EquipoDto());
        when(equipoRemote.listarEquipos()).thenReturn(equipos);

        List<EquipoDto> result = equipoResource.obtenerTodosLosEquipos();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(equipoRemote, times(1)).listarEquipos();
    }

    @Test
    void testBuscarEquipo() {
        EquipoDto equipo = new EquipoDto();
        equipo.setId(1L);
        when(equipoRemote.obtenerEquipo(1L)).thenReturn(equipo);

        Response response = equipoResource.buscarEquipo(1L);

        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        EquipoDto result = (EquipoDto) response.getEntity();
        assertNotNull(result);
        assertEquals(1L, result.getId());
    }

    @Test
    @SuppressWarnings("unchecked")
    void testFiltrarEquipos() {
        List<EquipoDto> equipos = List.of(new EquipoDto(), new EquipoDto());
        when(equipoRemote.obtenerEquiposFiltrado(any(Map.class))).thenReturn(equipos);

        List<EquipoDto> result = equipoResource.filtrar("nombre", "tipo", "marca", "modelo", "12345", null, "Uruguay", "ProveedorX", "2022-01-01", "001", "sala");

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(equipoRemote, times(1)).obtenerEquiposFiltrado(any(Map.class));
    }

    @Test
    void testObtenerBajasEquipos() {
        List<BajaEquipoDto> bajas = List.of(new BajaEquipoDto(), new BajaEquipoDto());
        when(bajaEquipoRemote.obtenerBajasEquipos()).thenReturn(bajas);

        List<BajaEquipoDto> result = equipoResource.obtenerBajasEquipos();

        assertNotNull(result);
        assertEquals(2, result.size());
    }

    @Test
    void testObtenerBaja() {
        BajaEquipoDto baja = new BajaEquipoDto();
        baja.setId(1L);
        when(bajaEquipoRemote.obtenerBajaEquipo(1L)).thenReturn(baja);

        BajaEquipoDto result = equipoResource.obtenerBaja(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
    }
}
