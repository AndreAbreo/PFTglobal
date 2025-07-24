package codigocreativo.uy.servidorapp.ws;

import codigocreativo.uy.servidorapp.dtos.FuncionalidadDto;
import codigocreativo.uy.servidorapp.servicios.FuncionalidadRemote;
import codigocreativo.uy.servidorapp.excepciones.ServiciosException;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class FuncionalidadResourceTest {

    @InjectMocks
    private FuncionalidadResource funcionalidadResource;

    @Mock
    private FuncionalidadRemote funcionalidadRemote;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCrearFuncionalidad() {
        FuncionalidadDto funcionalidadDto = new FuncionalidadDto();
        FuncionalidadDto funcionalidadCreada = new FuncionalidadDto();
        funcionalidadCreada.setId(1L);
        funcionalidadCreada.setNombreFuncionalidad("Nueva Funcionalidad");

        when(funcionalidadRemote.crear(any(FuncionalidadDto.class))).thenReturn(funcionalidadCreada);

        Response response = funcionalidadResource.crear(funcionalidadDto);

        assertEquals(Response.Status.CREATED.getStatusCode(), response.getStatus());
        assertEquals(funcionalidadCreada, response.getEntity());
        verify(funcionalidadRemote, times(1)).crear(any(FuncionalidadDto.class));
    }

    @Test
    void testModificarFuncionalidad() {
        FuncionalidadDto funcionalidadDto = new FuncionalidadDto();
        funcionalidadDto.setId(1L);
        funcionalidadDto.setNombreFuncionalidad("Funcionalidad Modificada");

        when(funcionalidadRemote.actualizar(any(FuncionalidadDto.class))).thenReturn(funcionalidadDto);

        Response response = funcionalidadResource.modificar(funcionalidadDto);

        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        String responseBody = (String) response.getEntity();
        assertTrue(responseBody.contains("Funcionalidad modificada correctamente"));
        verify(funcionalidadRemote, times(1)).actualizar(any(FuncionalidadDto.class));
    }

    @Test
    void testModificarFuncionalidadNoEncontrada() {
        FuncionalidadDto funcionalidadDto = new FuncionalidadDto();
        funcionalidadDto.setId(999L);

        when(funcionalidadRemote.actualizar(any(FuncionalidadDto.class))).thenReturn(null);

        Response response = funcionalidadResource.modificar(funcionalidadDto);

        assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());
        String responseBody = (String) response.getEntity();
        assertTrue(responseBody.contains("Funcionalidad no encontrada"));
        verify(funcionalidadRemote, times(1)).actualizar(any(FuncionalidadDto.class));
    }

    @Test
    void testEliminarFuncionalidad() throws ServiciosException {
        FuncionalidadDto funcionalidadDto = new FuncionalidadDto();
        funcionalidadDto.setId(1L);
        
        doNothing().when(funcionalidadRemote).eliminar(anyLong());

        Response response = funcionalidadResource.eliminar(funcionalidadDto);

        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        String responseBody = (String) response.getEntity();
        assertTrue(responseBody.contains("Funcionalidad eliminada correctamente"));
        verify(funcionalidadRemote, times(1)).eliminar(anyLong());
    }

    @Test
    void testEliminarFuncionalidadConPerfilesAsociados() throws ServiciosException {
        FuncionalidadDto funcionalidadDto = new FuncionalidadDto();
        funcionalidadDto.setId(1L);
        
        doThrow(new ServiciosException("No se puede eliminar la funcionalidad porque tiene perfiles asociados"))
                .when(funcionalidadRemote).eliminar(anyLong());

        Response response = funcionalidadResource.eliminar(funcionalidadDto);

        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
        String responseBody = (String) response.getEntity();
        assertTrue(responseBody.contains("No se puede eliminar la funcionalidad porque tiene perfiles asociados"));
        verify(funcionalidadRemote, times(1)).eliminar(anyLong());
    }

    @Test
    void testEliminarFuncionalidadNoEncontrada() throws ServiciosException {
        FuncionalidadDto funcionalidadDto = new FuncionalidadDto();
        funcionalidadDto.setId(1L);
        
        doThrow(new ServiciosException("Funcionalidad no encontrada con ID: 999"))
                .when(funcionalidadRemote).eliminar(anyLong());

        Response response = funcionalidadResource.eliminar(funcionalidadDto);

        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
        String responseBody = (String) response.getEntity();
        assertTrue(responseBody.contains("Funcionalidad no encontrada con ID: 999"));
        verify(funcionalidadRemote, times(1)).eliminar(anyLong());
    }

    @Test
    void testEliminarFuncionalidadConErrorInterno() throws ServiciosException {
        FuncionalidadDto funcionalidadDto = new FuncionalidadDto();
        funcionalidadDto.setId(1L);
        
        doThrow(new RuntimeException("Error de base de datos")).when(funcionalidadRemote).eliminar(anyLong());

        Response response = funcionalidadResource.eliminar(funcionalidadDto);

        assertEquals(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), response.getStatus());
        String responseBody = (String) response.getEntity();
        assertTrue(responseBody.contains("Error interno del servidor"));
        verify(funcionalidadRemote, times(1)).eliminar(anyLong());
    }

    @Test
    void testListarFuncionalidades() {
        List<FuncionalidadDto> funcionalidades = List.of(new FuncionalidadDto(), new FuncionalidadDto());
        when(funcionalidadRemote.obtenerTodas()).thenReturn(funcionalidades);

        List<FuncionalidadDto> result = funcionalidadResource.listar();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(funcionalidadRemote, times(1)).obtenerTodas();
    }

    @Test
    void testBuscarFuncionalidadPorId() {
        FuncionalidadDto funcionalidadDto = new FuncionalidadDto();
        funcionalidadDto.setId(1L);
        when(funcionalidadRemote.buscarPorId(1L)).thenReturn(funcionalidadDto);

        Response response = funcionalidadResource.buscarPorId(1L);

        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        FuncionalidadDto result = (FuncionalidadDto) response.getEntity();
        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(funcionalidadRemote, times(1)).buscarPorId(anyLong());
    }

    @Test
    void testBuscarFuncionalidadPorIdNoEncontrada() {
        when(funcionalidadRemote.buscarPorId(999L)).thenReturn(null);

        Response response = funcionalidadResource.buscarPorId(999L);

        assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());
        String responseBody = (String) response.getEntity();
        assertTrue(responseBody.contains("Funcionalidad no encontrada"));
        verify(funcionalidadRemote, times(1)).buscarPorId(anyLong());
    }

    @Test
    void testEliminarFuncionalidadConDtoNull() throws ServiciosException {
        Response response = funcionalidadResource.eliminar(null);

        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
        String responseBody = (String) response.getEntity();
        assertTrue(responseBody.contains("El ID de la funcionalidad es obligatorio para la eliminación"));
        verify(funcionalidadRemote, never()).eliminar(anyLong());
    }

    @Test
    void testEliminarFuncionalidadConIdNull() throws ServiciosException {
        FuncionalidadDto funcionalidadDto = new FuncionalidadDto();
        // No establecer el ID, dejarlo null

        Response response = funcionalidadResource.eliminar(funcionalidadDto);

        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
        String responseBody = (String) response.getEntity();
        assertTrue(responseBody.contains("El ID de la funcionalidad es obligatorio para la eliminación"));
        verify(funcionalidadRemote, never()).eliminar(anyLong());
    }
}
