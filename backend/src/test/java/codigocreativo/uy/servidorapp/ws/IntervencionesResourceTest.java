package codigocreativo.uy.servidorapp.ws;

import codigocreativo.uy.servidorapp.dtos.IntervencionDto;
import codigocreativo.uy.servidorapp.excepciones.ServiciosException;
import codigocreativo.uy.servidorapp.servicios.IntervencionRemote;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class IntervencionesResourceTest {
    @Mock IntervencionRemote intervencionRemote;
    IntervencionesResource resource;

    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
        resource = new IntervencionesResource();
        
        // Inyectar el mock usando reflexión
        Field erField = IntervencionesResource.class.getDeclaredField("er");
        erField.setAccessible(true);
        erField.set(resource, intervencionRemote);
    }

    @Test
    void testCrear() throws Exception {
        IntervencionDto dto = new IntervencionDto();
        doNothing().when(intervencionRemote).crear(dto);
        
        Response response = resource.crear(dto);
        
        assertEquals(201, response.getStatus());
        verify(intervencionRemote).crear(dto);
    }

    @Test
    void testCrearThrowsException() throws Exception {
        IntervencionDto dto = new IntervencionDto();
        doThrow(new ServiciosException("Error al crear")).when(intervencionRemote).crear(dto);
        
        assertThrows(ServiciosException.class, () -> resource.crear(dto));
    }

    @Test
    void testModificar() throws Exception {
        IntervencionDto dto = new IntervencionDto();
        doNothing().when(intervencionRemote).actualizar(dto);
        
        Response response = resource.modificar(dto);
        
        assertEquals(200, response.getStatus());
        verify(intervencionRemote).actualizar(dto);
    }

    @Test
    void testModificarThrowsException() throws Exception {
        IntervencionDto dto = new IntervencionDto();
        doThrow(new ServiciosException("Error al modificar")).when(intervencionRemote).actualizar(dto);
        
        assertThrows(ServiciosException.class, () -> resource.modificar(dto));
    }

    @Test
    void testListar() throws Exception {
        List<IntervencionDto> expectedList = List.of(new IntervencionDto());
        when(intervencionRemote.obtenerTodas()).thenReturn(expectedList);
        
        List<IntervencionDto> result = resource.listar();
        
        assertEquals(expectedList, result);
        verify(intervencionRemote).obtenerTodas();
    }

    @Test
    void testListarThrowsException() throws Exception {
        when(intervencionRemote.obtenerTodas()).thenThrow(new ServiciosException("Error al listar"));
        
        assertThrows(ServiciosException.class, () -> resource.listar());
    }

    @Test
    void testBuscarPorId() throws Exception {
        Long id = 1L;
        IntervencionDto expectedDto = new IntervencionDto();
        when(intervencionRemote.buscarId(id)).thenReturn(expectedDto);
        
        Response response = resource.buscarPorId(id);
        
        assertEquals(200, response.getStatus());
        assertEquals(expectedDto, response.getEntity());
        verify(intervencionRemote).buscarId(id);
    }

    @Test
    void testBuscarPorIdThrowsException() throws Exception {
        Long id = 1L;
        when(intervencionRemote.buscarId(id)).thenThrow(new ServiciosException("No encontrado"));
        
        assertThrows(ServiciosException.class, () -> resource.buscarPorId(id));
    }

    @Test
    void testBuscarPorIdNotFound() throws Exception {
        Long id = 1L;
        when(intervencionRemote.buscarId(id)).thenReturn(null);
        
        Response response = resource.buscarPorId(id);
        
        assertEquals(404, response.getStatus());
        assertEquals("{\"error\":\"Intervención no encontrada\"}", response.getEntity());
        verify(intervencionRemote).buscarId(id);
    }

    @Test
    void testObtenerPorRangoDeFecha() throws Exception {
        String fechaDesde = "2023-01-01T00:00:00";
        String fechaHasta = "2023-12-31T23:59:59";
        Long idEquipo = 1L;
        List<IntervencionDto> expectedList = List.of(new IntervencionDto());
        
        when(intervencionRemote.obtenerPorRangoDeFecha(any(LocalDateTime.class), any(LocalDateTime.class), eq(idEquipo)))
            .thenReturn(expectedList);
        
        List<IntervencionDto> result = resource.obtenerPorRangoDeFecha(fechaDesde, fechaHasta, idEquipo);
        
        assertEquals(expectedList, result);
        verify(intervencionRemote).obtenerPorRangoDeFecha(any(LocalDateTime.class), any(LocalDateTime.class), eq(idEquipo));
    }

    @Test
    void testObtenerPorRangoDeFechaThrowsException() throws Exception {
        String fechaDesde = "2023-01-01T00:00:00";
        String fechaHasta = "2023-12-31T23:59:59";
        Long idEquipo = 1L;
        
        when(intervencionRemote.obtenerPorRangoDeFecha(any(LocalDateTime.class), any(LocalDateTime.class), eq(idEquipo)))
            .thenThrow(new ServiciosException("Error en rango de fechas"));
        
        assertThrows(ServiciosException.class, () -> resource.obtenerPorRangoDeFecha(fechaDesde, fechaHasta, idEquipo));
    }

    @Test
    void testObtenerPorRangoDeFechaInvalidDateFormat() {
        String fechaDesde = "fecha-invalida";
        String fechaHasta = "2023-12-31T23:59:59";
        Long idEquipo = 1L;
        
        assertThrows(DateTimeParseException.class, () -> resource.obtenerPorRangoDeFecha(fechaDesde, fechaHasta, idEquipo));
    }

    @Test
    void testObtenerCantidadPorTipo() throws Exception {
        String fechaDesde = "2023-01-01T00:00:00";
        String fechaHasta = "2023-12-31T23:59:59";
        Long idTipo = 1L;
        Map<String, Long> expectedMap = new HashMap<>();
        expectedMap.put("Preventivo", 10L);
        expectedMap.put("Correctivo", 5L);
        
        when(intervencionRemote.obtenerCantidadPorTipo(any(LocalDateTime.class), any(LocalDateTime.class), eq(idTipo)))
            .thenReturn(expectedMap);
        
        Map<String, Long> result = resource.obtenerCantidadPorTipo(fechaDesde, fechaHasta, idTipo);
        
        assertEquals(expectedMap, result);
        verify(intervencionRemote).obtenerCantidadPorTipo(any(LocalDateTime.class), any(LocalDateTime.class), eq(idTipo));
    }

    @Test
    void testObtenerCantidadPorTipoThrowsException() throws Exception {
        String fechaDesde = "2023-01-01T00:00:00";
        String fechaHasta = "2023-12-31T23:59:59";
        Long idTipo = 1L;
        
        when(intervencionRemote.obtenerCantidadPorTipo(any(LocalDateTime.class), any(LocalDateTime.class), eq(idTipo)))
            .thenThrow(new ServiciosException("Error en reporte por tipo"));
        
        assertThrows(ServiciosException.class, () -> resource.obtenerCantidadPorTipo(fechaDesde, fechaHasta, idTipo));
    }

    @Test
    void testObtenerCantidadPorTipoWithNullDates() throws Exception {
        String fechaDesde = null;
        String fechaHasta = null;
        Long idTipo = 1L;
        Map<String, Long> expectedMap = new HashMap<>();
        expectedMap.put("Total", 15L);
        
        when(intervencionRemote.obtenerCantidadPorTipo(isNull(), isNull(), eq(idTipo)))
            .thenReturn(expectedMap);
        
        Map<String, Long> result = resource.obtenerCantidadPorTipo(fechaDesde, fechaHasta, idTipo);
        
        assertEquals(expectedMap, result);
        verify(intervencionRemote).obtenerCantidadPorTipo(isNull(), isNull(), eq(idTipo));
    }

    @Test
    void testObtenerCantidadPorTipoWithMixedNullDates() throws Exception {
        String fechaDesde = "2023-01-01T00:00:00";
        String fechaHasta = null;
        Long idTipo = 1L;
        Map<String, Long> expectedMap = new HashMap<>();
        expectedMap.put("Desde 2023", 8L);
        
        when(intervencionRemote.obtenerCantidadPorTipo(any(LocalDateTime.class), isNull(), eq(idTipo)))
            .thenReturn(expectedMap);
        
        Map<String, Long> result = resource.obtenerCantidadPorTipo(fechaDesde, fechaHasta, idTipo);
        
        assertEquals(expectedMap, result);
        verify(intervencionRemote).obtenerCantidadPorTipo(any(LocalDateTime.class), isNull(), eq(idTipo));
    }

    @Test
    void testObtenerCantidadPorTipoInvalidDateFormat() {
        String fechaDesde = "fecha-invalida";
        String fechaHasta = "2023-12-31T23:59:59";
        Long idTipo = 1L;
        
        assertThrows(DateTimeParseException.class, () -> resource.obtenerCantidadPorTipo(fechaDesde, fechaHasta, idTipo));
    }
} 