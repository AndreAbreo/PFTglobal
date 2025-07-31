package codigocreativo.uy.servidorapp.ws;

import codigocreativo.uy.servidorapp.dtos.TiposEquipoDto;
import codigocreativo.uy.servidorapp.excepciones.ServiciosException;
import codigocreativo.uy.servidorapp.servicios.TiposEquipoRemote;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class TipoEquipoResourceTest {

    @Mock
    private TiposEquipoRemote er;

    @InjectMocks
    private TipoEquipoResource resource;

    private TiposEquipoDto tipoDto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        tipoDto = new TiposEquipoDto();
        tipoDto.setId(1L);
        tipoDto.setNombreTipo("TipoTest");
    }


    @Test
    void testCrear_exitoso() throws ServiciosException {
        doNothing().when(er).crearTiposEquipo(tipoDto);

        Response response = resource.crear(tipoDto);

        assertEquals(Response.Status.CREATED.getStatusCode(), response.getStatus());
        
        @SuppressWarnings("unchecked")
        Map<String, String> responseBody = (Map<String, String>) response.getEntity();
        assertEquals("Tipo de equipo creado correctamente", responseBody.get("message"));
        
        verify(er, times(1)).crearTiposEquipo(tipoDto);
    }

    @Test
    void testCrear_conError() throws ServiciosException {
        doThrow(new ServiciosException("Error al crear tipo")).when(er).crearTiposEquipo(tipoDto);

        Response response = resource.crear(tipoDto);

        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
        
        @SuppressWarnings("unchecked")
        Map<String, String> responseBody = (Map<String, String>) response.getEntity();
        assertEquals("Error al crear tipo", responseBody.get("error"));
        
        verify(er, times(1)).crearTiposEquipo(tipoDto);
    }

    @Test
    void testCrear_conExcepcionGenerica() throws ServiciosException {
        doThrow(new RuntimeException("Error inesperado")).when(er).crearTiposEquipo(tipoDto);

        Response response = resource.crear(tipoDto);

        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
        
        @SuppressWarnings("unchecked")
        Map<String, String> responseBody = (Map<String, String>) response.getEntity();
        assertEquals("Error inesperado", responseBody.get("error"));
    }


    @Test
    void testModificar_exitoso() throws ServiciosException {
        doNothing().when(er).modificarTiposEquipo(tipoDto);

        Response response = resource.modificar(tipoDto);

        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        
        @SuppressWarnings("unchecked")
        Map<String, String> responseBody = (Map<String, String>) response.getEntity();
        assertEquals("Tipo de equipo modificado correctamente", responseBody.get("message"));
        
        verify(er, times(1)).modificarTiposEquipo(tipoDto);
    }

    @Test
    void testModificar_conError() throws ServiciosException {
        doThrow(new ServiciosException("Error al modificar tipo")).when(er).modificarTiposEquipo(tipoDto);

        Response response = resource.modificar(tipoDto);

        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
        
        @SuppressWarnings("unchecked")
        Map<String, String> responseBody = (Map<String, String>) response.getEntity();
        assertEquals("Error al modificar tipo", responseBody.get("error"));
    }


    @Test
    void testEliminar_exitoso() throws ServiciosException {
        Long id = 1L;
        doNothing().when(er).eliminarTiposEquipo(id);

        Response response = resource.eliminar(id);

        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        
        @SuppressWarnings("unchecked")
        Map<String, String> responseBody = (Map<String, String>) response.getEntity();
        assertEquals("Tipo de equipo desactivado correctamente", responseBody.get("message"));
        
        verify(er, times(1)).eliminarTiposEquipo(id);
    }

    @Test
    void testEliminar_conError() throws ServiciosException {
        Long id = 1L;
        doThrow(new ServiciosException("Error al eliminar tipo")).when(er).eliminarTiposEquipo(id);

        Response response = resource.eliminar(id);

        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
        
        @SuppressWarnings("unchecked")
        Map<String, String> responseBody = (Map<String, String>) response.getEntity();
        assertEquals("Error al eliminar tipo", responseBody.get("error"));
    }


    @Test
    void testListar_exitoso() {
        List<TiposEquipoDto> expectedList = Arrays.asList(tipoDto, new TiposEquipoDto());
        when(er.listarTiposEquipo()).thenReturn(expectedList);

        List<TiposEquipoDto> actualList = resource.listar();

        assertNotNull(actualList);
        assertEquals(expectedList.size(), actualList.size());
        assertEquals(expectedList, actualList);
        verify(er, times(1)).listarTiposEquipo();
    }

    @Test
    void testListar_listaVacia() {
        List<TiposEquipoDto> emptyList = Arrays.asList();
        when(er.listarTiposEquipo()).thenReturn(emptyList);

        List<TiposEquipoDto> actualList = resource.listar();

        assertNotNull(actualList);
        assertEquals(0, actualList.size());
        assertTrue(actualList.isEmpty());
        verify(er, times(1)).listarTiposEquipo();
    }


    @Test
    void testBuscarPorId_exitoso() throws ServiciosException {
        Long id = 1L;
        when(er.obtenerPorId(id)).thenReturn(tipoDto);

        Response response = resource.buscarPorId(id);

        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        assertEquals(tipoDto, response.getEntity());
        verify(er, times(1)).obtenerPorId(id);
    }

    @Test
    void testBuscarPorId_noEncontrado() throws ServiciosException {
        Long id = 999L;
        when(er.obtenerPorId(id)).thenThrow(new ServiciosException("No se encontró el tipo"));

        Response response = resource.buscarPorId(id);

        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
        
        @SuppressWarnings("unchecked")
        Map<String, String> responseBody = (Map<String, String>) response.getEntity();
        assertEquals("No se encontró el tipo", responseBody.get("error"));
        
        verify(er, times(1)).obtenerPorId(id);
    }
}