package codigocreativo.uy.servidorapp.ws;

import codigocreativo.uy.servidorapp.dtos.MarcasModeloDto;
import codigocreativo.uy.servidorapp.excepciones.ServiciosException;
import codigocreativo.uy.servidorapp.servicios.MarcasModeloRemote;
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
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class MarcaResourceTest {

    @InjectMocks
    private MarcaResource marcaResource;

    @Mock
    private MarcasModeloRemote er;

    private MarcasModeloDto marcaDto;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        marcaDto = new MarcasModeloDto();
        marcaDto.setId(1L);
        marcaDto.setNombre("MarcaTest");
    }

    // ========== TESTS PARA CREAR MARCA ==========

    @Test
    void testCrearMarca_exitoso() throws ServiciosException {
        doNothing().when(er).crearMarcasModelo(any(MarcasModeloDto.class));

        Response response = marcaResource.crearMarca(marcaDto);

        assertEquals(Response.Status.CREATED.getStatusCode(), response.getStatus());
        
        @SuppressWarnings("unchecked")
        Map<String, String> responseBody = (Map<String, String>) response.getEntity();
        assertEquals("Marca creada correctamente", responseBody.get("message"));
        
        verify(er, times(1)).crearMarcasModelo(marcaDto);
    }

    @Test
    void testCrearMarca_conError() throws ServiciosException {
        doThrow(new ServiciosException("Error al crear marca")).when(er).crearMarcasModelo(any(MarcasModeloDto.class));

        Response response = marcaResource.crearMarca(marcaDto);

        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
        
        @SuppressWarnings("unchecked")
        Map<String, String> responseBody = (Map<String, String>) response.getEntity();
        assertEquals("Error al crear marca", responseBody.get("error"));
        
        verify(er, times(1)).crearMarcasModelo(marcaDto);
    }

    @Test
    void testCrearMarca_conExcepcionGenerica() throws ServiciosException {
        doThrow(new RuntimeException("Error inesperado")).when(er).crearMarcasModelo(any(MarcasModeloDto.class));

        Response response = marcaResource.crearMarca(marcaDto);

        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
        
        @SuppressWarnings("unchecked")
        Map<String, String> responseBody = (Map<String, String>) response.getEntity();
        assertEquals("Error inesperado", responseBody.get("error"));
    }

    // ========== TESTS PARA MODIFICAR MARCA ==========

    @Test
    void testModificarMarca_exitoso() throws ServiciosException {
        doNothing().when(er).modificarMarcasModelo(any(MarcasModeloDto.class));

        Response response = marcaResource.modificarMarca(marcaDto);

        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        
        @SuppressWarnings("unchecked")
        Map<String, String> responseBody = (Map<String, String>) response.getEntity();
        assertEquals("Marca modificada correctamente", responseBody.get("message"));
        
        verify(er, times(1)).modificarMarcasModelo(marcaDto);
    }

    @Test
    void testModificarMarca_conError() throws ServiciosException {
        doThrow(new ServiciosException("Error al modificar marca")).when(er).modificarMarcasModelo(any(MarcasModeloDto.class));

        Response response = marcaResource.modificarMarca(marcaDto);

        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
        
        @SuppressWarnings("unchecked")
        Map<String, String> responseBody = (Map<String, String>) response.getEntity();
        assertEquals("Error al modificar marca", responseBody.get("error"));
    }

    // ========== TESTS PARA ELIMINAR MARCA ==========

    @Test
    void testEliminarMarca_exitoso() throws ServiciosException {
        Long id = 1L;
        doNothing().when(er).eliminarMarca(id);

        Response response = marcaResource.eliminarMarca(id);

        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        
        @SuppressWarnings("unchecked")
        Map<String, String> responseBody = (Map<String, String>) response.getEntity();
        assertEquals("Marca inactivada correctamente", responseBody.get("message"));
        
        verify(er, times(1)).eliminarMarca(id);
    }

    @Test
    void testEliminarMarca_conError() throws ServiciosException {
        Long id = 1L;
        doThrow(new ServiciosException("Error al eliminar marca")).when(er).eliminarMarca(id);

        Response response = marcaResource.eliminarMarca(id);

        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
        
        @SuppressWarnings("unchecked")
        Map<String, String> responseBody = (Map<String, String>) response.getEntity();
        assertEquals("Error al eliminar marca", responseBody.get("error"));
    }

    // ========== TESTS PARA LISTAR MARCAS ==========

    @Test
    void testListarTodas_exitoso() {
        List<MarcasModeloDto> expectedList = List.of(marcaDto);
        when(er.obtenerMarcasLista()).thenReturn(expectedList);

        List<MarcasModeloDto> result = marcaResource.listarTodas();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(expectedList, result);
        verify(er, times(1)).obtenerMarcasLista();
    }

    @Test
    void testListarTodas_listaVacia() {
        List<MarcasModeloDto> emptyList = List.of();
        when(er.obtenerMarcasLista()).thenReturn(emptyList);

        List<MarcasModeloDto> result = marcaResource.listarTodas();

        assertNotNull(result);
        assertEquals(0, result.size());
        assertTrue(result.isEmpty());
        verify(er, times(1)).obtenerMarcasLista();
    }

    // ========== TESTS PARA BUSCAR POR ID ==========

    @Test
    void testBuscarPorId_exitoso() throws ServiciosException {
        Long id = 1L;
        when(er.obtenerMarca(id)).thenReturn(marcaDto);

        Response response = marcaResource.buscarPorId(id);

        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        assertEquals(marcaDto, response.getEntity());
        verify(er, times(1)).obtenerMarca(id);
    }

    @Test
    void testBuscarPorId_noEncontrado() throws ServiciosException {
        Long id = 999L;
        when(er.obtenerMarca(id)).thenThrow(new ServiciosException("No se encontró la marca"));

        Response response = marcaResource.buscarPorId(id);

        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
        
        @SuppressWarnings("unchecked")
        Map<String, String> responseBody = (Map<String, String>) response.getEntity();
        assertEquals("No se encontró la marca", responseBody.get("error"));
        
        verify(er, times(1)).obtenerMarca(id);
    }

    // ========== TESTS OBSOLETOS REMOVIDOS ==========
    // Los siguientes tests fueron removidos porque ya no aplicaban después de la refactorización:
    // - testModificarMarca_estadoPermitido: La validación ahora está en el bean
    // - testModificarMarca_nombreNoPermitido: La validación ahora está en el bean
    // - Validaciones de lógica de negocio que se movieron al bean
}




