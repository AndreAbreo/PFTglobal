package codigocreativo.uy.servidorapp.ws;

import codigocreativo.uy.servidorapp.dtos.PaisDto;
import codigocreativo.uy.servidorapp.servicios.PaisRemote;
import codigocreativo.uy.servidorapp.enumerados.Estados;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import jakarta.ws.rs.core.Response;
import java.util.List;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class PaisesResourceTest {

    @InjectMocks
    private PaisesResource paisesResource;

    @Mock
    private PaisRemote er;

    private PaisDto paisDto;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        paisDto = new PaisDto();
        paisDto.setNombre("Uruguay");
        paisDto.setEstado(Estados.ACTIVO);
    }

    @Test
    void testCrearPais_success() {
        doNothing().when(er).crearPais(any(PaisDto.class));

        Response response = paisesResource.crearPais(paisDto);

        assertEquals(Response.Status.CREATED.getStatusCode(), response.getStatus());
        Map<String, String> entity = (Map<String, String>) response.getEntity();
        assertEquals("País creado correctamente", entity.get("message"));
        verify(er, times(1)).crearPais(any(PaisDto.class));
    }

    @Test
    void testCrearPais_error() {
        doThrow(new IllegalArgumentException("Ya existe un país con ese nombre")).when(er).crearPais(any(PaisDto.class));

        Response response = paisesResource.crearPais(paisDto);

        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
        Map<String, String> entity = (Map<String, String>) response.getEntity();
        assertEquals("Ya existe un país con ese nombre", entity.get("error"));
    }

    @Test
    void testModificarPais() {
        doNothing().when(er).modificarPais(any(PaisDto.class));

        Response response = paisesResource.modificarPais(paisDto);

        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        verify(er, times(1)).modificarPais(any(PaisDto.class));
    }

    @Test
    void testListarPaises() {
        List<PaisDto> expectedList = List.of(paisDto);
        when(er.obtenerpais()).thenReturn(expectedList);

        List<PaisDto> result = paisesResource.listarPaises();

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(er, times(1)).obtenerpais();
    }

    @Test
    void testSeleccionarPais_success() {
        when(er.obtenerPaisPorId(1L)).thenReturn(paisDto);

        Response response = paisesResource.seleccionarPais(1L);

        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        assertEquals(paisDto, response.getEntity());
        verify(er, times(1)).obtenerPaisPorId(1L);
    }

    @Test
    void testSeleccionarPais_notFound() {
        when(er.obtenerPaisPorId(1L)).thenReturn(null);

        Response response = paisesResource.seleccionarPais(1L);

        assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());
        Map<String, String> entity = (Map<String, String>) response.getEntity();
        assertEquals("País no encontrado", entity.get("error"));
    }

    @Test
    void testFiltrarPorEstado_conEstado() {
        List<PaisDto> expectedList = List.of(paisDto);
        when(er.filtrarPaises("ACTIVO", null)).thenReturn(expectedList);

        Response response = paisesResource.filtrarPorEstado("ACTIVO", null);

        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        assertEquals(expectedList, response.getEntity());
        verify(er, times(1)).filtrarPaises("ACTIVO", null);
    }

    @Test
    void testFiltrarPorEstado_sinEstado() {
        List<PaisDto> expectedList = List.of(paisDto);
        when(er.filtrarPaises(null, null)).thenReturn(expectedList);

        Response response = paisesResource.filtrarPorEstado(null, null);

        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        assertEquals(expectedList, response.getEntity());
        verify(er, times(1)).filtrarPaises(null, null);
    }

    @Test
    void testFiltrarPorEstado_soloNombre() {
        List<PaisDto> expectedList = List.of(paisDto);
        when(er.filtrarPaises(null, "uruguay")).thenReturn(expectedList);

        Response response = paisesResource.filtrarPorEstado(null, "uruguay");

        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        assertEquals(expectedList, response.getEntity());
        verify(er, times(1)).filtrarPaises(null, "uruguay");
    }

    @Test
    void testFiltrarPorEstado_estadoYNombre() {
        List<PaisDto> expectedList = List.of(paisDto);
        when(er.filtrarPaises("ACTIVO", "uruguay")).thenReturn(expectedList);

        Response response = paisesResource.filtrarPorEstado("ACTIVO", "uruguay");

        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        assertEquals(expectedList, response.getEntity());
        verify(er, times(1)).filtrarPaises("ACTIVO", "uruguay");
    }

    @Test
    void testFiltrarPorEstado_estadoInvalido() {
        when(er.filtrarPaises("ESTADO_INVALIDO", null)).thenThrow(new IllegalArgumentException("Estado inválido"));

        Response response = paisesResource.filtrarPorEstado("ESTADO_INVALIDO", null);

        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
        Map<String, String> entity = (Map<String, String>) response.getEntity();
        assertTrue(entity.get("error").contains("Estado inválido"));
    }

    @Test
    void testInactivarPais_success() {
        doNothing().when(er).inactivarPais(1L);

        Response response = paisesResource.inactivarPais(1L);

        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        Map<String, String> entity = (Map<String, String>) response.getEntity();
        assertEquals("País desactivado correctamente", entity.get("message"));
        verify(er, times(1)).inactivarPais(1L);
    }

    @Test
    void testInactivarPais_error() {
        doThrow(new IllegalArgumentException("País no encontrado")).when(er).inactivarPais(1L);

        Response response = paisesResource.inactivarPais(1L);

        assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());
        Map<String, String> entity = (Map<String, String>) response.getEntity();
        assertTrue(entity.get("error").contains("País no encontrado"));
    }
}
