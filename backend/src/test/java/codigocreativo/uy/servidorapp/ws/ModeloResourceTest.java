package codigocreativo.uy.servidorapp.ws;

import codigocreativo.uy.servidorapp.dtos.ModelosEquipoDto;
import codigocreativo.uy.servidorapp.enumerados.Estados;
import codigocreativo.uy.servidorapp.excepciones.ServiciosException;
import codigocreativo.uy.servidorapp.servicios.ModelosEquipoRemote;
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

class ModeloResourceTest {

    @InjectMocks
    private ModeloResource modeloResource;

    @Mock
    private ModelosEquipoRemote er;

    private ModelosEquipoDto modeloDto;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        modeloDto = new ModelosEquipoDto();
        modeloDto.setId(1L);
        modeloDto.setNombre("ModeloTest");
    }

    // ========== TESTS PARA CREAR MODELO ==========

    @Test
    void testCrearModelo_exitoso() throws ServiciosException {
        doNothing().when(er).crearModelos(any(ModelosEquipoDto.class));

        Response response = modeloResource.crearModelo(modeloDto);

        assertEquals(Response.Status.CREATED.getStatusCode(), response.getStatus());
        
        @SuppressWarnings("unchecked")
        Map<String, String> responseBody = (Map<String, String>) response.getEntity();
        assertEquals("Modelo creado correctamente", responseBody.get("message"));
        
        verify(er, times(1)).crearModelos(modeloDto);
    }

    @Test
    void testCrearModelo_conError() throws ServiciosException {
        doThrow(new ServiciosException("Error al crear modelo")).when(er).crearModelos(any(ModelosEquipoDto.class));

        Response response = modeloResource.crearModelo(modeloDto);

        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
        
        @SuppressWarnings("unchecked")
        Map<String, String> responseBody = (Map<String, String>) response.getEntity();
        assertEquals("Error al crear modelo", responseBody.get("error"));
        
        verify(er, times(1)).crearModelos(modeloDto);
    }

    // ========== TESTS PARA MODIFICAR MODELO ==========

    @Test
    void testModificarModelo_exitoso() throws ServiciosException {
        doNothing().when(er).modificarModelos(any(ModelosEquipoDto.class));

        Response response = modeloResource.modificarModelo(modeloDto);

        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        
        @SuppressWarnings("unchecked")
        Map<String, String> responseBody = (Map<String, String>) response.getEntity();
        assertEquals("Modelo modificado correctamente", responseBody.get("message"));
        
        verify(er, times(1)).modificarModelos(modeloDto);
    }

    @Test
    void testModificarModelo_conError() throws ServiciosException {
        doThrow(new ServiciosException("Error al modificar modelo")).when(er).modificarModelos(any(ModelosEquipoDto.class));

        Response response = modeloResource.modificarModelo(modeloDto);

        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
        
        @SuppressWarnings("unchecked")
        Map<String, String> responseBody = (Map<String, String>) response.getEntity();
        assertEquals("Error al modificar modelo", responseBody.get("error"));
    }

    // ========== TESTS PARA ELIMINAR MODELO ==========

    @Test
    void testEliminarModelo_exitoso() throws ServiciosException {
        Long id = 1L;
        doNothing().when(er).eliminarModelos(id);

        Response response = modeloResource.eliminarModelo(id);

        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        
        @SuppressWarnings("unchecked")
        Map<String, String> responseBody = (Map<String, String>) response.getEntity();
        assertEquals("Modelo desactivado correctamente", responseBody.get("message"));
        
        verify(er, times(1)).eliminarModelos(id);
    }

    @Test
    void testEliminarModelo_conError() throws ServiciosException {
        Long id = 1L;
        doThrow(new ServiciosException("Error al eliminar modelo")).when(er).eliminarModelos(id);

        Response response = modeloResource.eliminarModelo(id);

        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
        
        @SuppressWarnings("unchecked")
        Map<String, String> responseBody = (Map<String, String>) response.getEntity();
        assertEquals("Error al eliminar modelo", responseBody.get("error"));
    }

    // ========== TESTS PARA LISTAR MODELOS ==========

    @Test
    void testListarTodos_exitoso() {
        List<ModelosEquipoDto> expectedList = List.of(modeloDto);
        when(er.listarModelos()).thenReturn(expectedList);

        List<ModelosEquipoDto> result = modeloResource.listarTodos();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(expectedList, result);
        verify(er, times(1)).listarModelos();
    }

    @Test
    void testListarTodos_listaVacia() {
        List<ModelosEquipoDto> emptyList = List.of();
        when(er.listarModelos()).thenReturn(emptyList);

        List<ModelosEquipoDto> result = modeloResource.listarTodos();

        assertNotNull(result);
        assertEquals(0, result.size());
        assertTrue(result.isEmpty());
        verify(er, times(1)).listarModelos();
    }

    // ========== TESTS PARA BUSCAR POR ID ==========

    @Test
    void testBuscarPorId_exitoso() throws ServiciosException {
        Long id = 1L;
        when(er.obtenerModelos(id)).thenReturn(modeloDto);

        Response response = modeloResource.buscarPorId(id);

        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        assertEquals(modeloDto, response.getEntity());
        verify(er, times(1)).obtenerModelos(id);
    }

    @Test
    void testBuscarPorId_noEncontrado() throws ServiciosException {
        Long id = 999L;
        when(er.obtenerModelos(id)).thenThrow(new RuntimeException("No se encontró el modelo"));

        Response response = modeloResource.buscarPorId(id);

        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
        
        @SuppressWarnings("unchecked")
        Map<String, String> responseBody = (Map<String, String>) response.getEntity();
        assertEquals("No se encontró el modelo", responseBody.get("error"));
        
        verify(er, times(1)).obtenerModelos(id);
    }
}
