package codigocreativo.uy.servidorapp.ws;

import codigocreativo.uy.servidorapp.dtos.UbicacionDto;
import codigocreativo.uy.servidorapp.enumerados.Estados;
import codigocreativo.uy.servidorapp.enumerados.Sectores;
import codigocreativo.uy.servidorapp.excepciones.ServiciosException;
import codigocreativo.uy.servidorapp.servicios.UbicacionRemote;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import jakarta.ws.rs.core.Response;
import java.util.List;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UbicacionesResourceTest {

    @InjectMocks
    private UbicacionesResource ubicacionesResource;

    @Mock
    private UbicacionRemote er;

    private UbicacionDto ubicacionDto;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        ubicacionDto = new UbicacionDto();
        ubicacionDto.setId(1L);
        ubicacionDto.setNombre("UbicacionTest");
        ubicacionDto.setSector(Sectores.POLICLINICO.getValor());
    }


    @Test
    void testListarUbicaciones_exitoso() throws ServiciosException {
        List<UbicacionDto> expectedList = List.of(ubicacionDto);
        when(er.listarUbicaciones()).thenReturn(expectedList);

        Response response = ubicacionesResource.listarUbicaciones();

        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        assertEquals(expectedList, response.getEntity());
        verify(er, times(1)).listarUbicaciones();
    }

    @Test
    void testListarUbicaciones_listaVacia() throws ServiciosException {
        List<UbicacionDto> emptyList = List.of();
        when(er.listarUbicaciones()).thenReturn(emptyList);

        Response response = ubicacionesResource.listarUbicaciones();

        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        assertEquals(emptyList, response.getEntity());
        verify(er, times(1)).listarUbicaciones();
    }

    @Test
    void testListarUbicaciones_conError() throws ServiciosException {
        when(er.listarUbicaciones()).thenThrow(new ServiciosException("Error al listar ubicaciones"));

        Response response = ubicacionesResource.listarUbicaciones();

        assertEquals(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), response.getStatus());
        
        @SuppressWarnings("unchecked")
        Map<String, String> responseBody = (Map<String, String>) response.getEntity();
        assertEquals("Error al listar ubicaciones", responseBody.get("error"));
        
        verify(er, times(1)).listarUbicaciones();
    }


    @Test
    void testBuscarPorId_exitoso() throws ServiciosException {
        Long id = 1L;
        when(er.obtenerUbicacionPorId(id)).thenReturn(ubicacionDto);

        Response response = ubicacionesResource.buscarPorId(id);

        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        assertEquals(ubicacionDto, response.getEntity());
        verify(er, times(1)).obtenerUbicacionPorId(id);
    }

    @Test
    void testBuscarPorId_noEncontrado() throws ServiciosException {
        Long id = 999L;
        when(er.obtenerUbicacionPorId(id)).thenThrow(new ServiciosException("No se encontró la ubicación"));

        Response response = ubicacionesResource.buscarPorId(id);

        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
        
        @SuppressWarnings("unchecked")
        Map<String, String> responseBody = (Map<String, String>) response.getEntity();
        assertEquals("No se encontró la ubicación", responseBody.get("error"));
        
        verify(er, times(1)).obtenerUbicacionPorId(id);
    }





}
