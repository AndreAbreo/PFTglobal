package codigocreativo.uy.servidorapp.ws;

import codigocreativo.uy.servidorapp.dtos.PerfilDto;
import codigocreativo.uy.servidorapp.enumerados.Estados;
import codigocreativo.uy.servidorapp.excepciones.ServiciosException;
import codigocreativo.uy.servidorapp.servicios.PerfilRemote;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import jakarta.ws.rs.core.Response;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class PerfilResourceTest {

    @InjectMocks
    private PerfilResource perfilResource;

    @Mock
    private PerfilRemote perfilRemote;

    private PerfilDto perfilDto;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        perfilDto = new PerfilDto();
        perfilDto.setNombrePerfil("Admin");
    }

    @Test
    void testCrearPerfilExitoso() throws ServiciosException {
        PerfilDto perfilCreado = new PerfilDto();
        perfilCreado.setId(1L);
        perfilCreado.setNombrePerfil("Admin");
        perfilCreado.setEstado(Estados.ACTIVO);
        
        when(perfilRemote.crearPerfil(any(PerfilDto.class))).thenReturn(perfilCreado);

        Response response = perfilResource.crearPerfil(perfilDto);

        assertEquals(Response.Status.CREATED.getStatusCode(), response.getStatus());
        assertEquals(perfilCreado, response.getEntity());
        verify(perfilRemote, times(1)).crearPerfil(any(PerfilDto.class));
    }

    @Test
    void testCrearPerfilNull() throws ServiciosException {
        Response response = perfilResource.crearPerfil(null);

        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
        assertEquals("{\"error\":\"El perfil no puede ser null\"}", response.getEntity());
        verify(perfilRemote, never()).crearPerfil(any(PerfilDto.class));
    }

    @Test
    void testCrearPerfilServiciosException() throws ServiciosException {
        String errorMessage = "Ya existe un perfil con el nombre: Admin";
        doThrow(new ServiciosException(errorMessage)).when(perfilRemote).crearPerfil(any(PerfilDto.class));

        Response response = perfilResource.crearPerfil(perfilDto);

        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
        assertEquals("{\"error\":\"" + errorMessage + "\"}", response.getEntity());
        verify(perfilRemote, times(1)).crearPerfil(any(PerfilDto.class));
    }

    @Test
    void testModificarPerfilExitoso() throws ServiciosException {
        perfilDto.setId(1L);
        doNothing().when(perfilRemote).modificarPerfil(any(PerfilDto.class));

        Response response = perfilResource.modificarPerfil(perfilDto);

        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        verify(perfilRemote, times(1)).modificarPerfil(any(PerfilDto.class));
    }

    @Test
    void testModificarPerfilNull() throws ServiciosException {
        Response response = perfilResource.modificarPerfil(null);

        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
        assertEquals("{\"error\":\"El perfil no puede ser null\"}", response.getEntity());
        verify(perfilRemote, never()).modificarPerfil(any(PerfilDto.class));
    }

    @Test
    void testModificarPerfilServiciosException() throws ServiciosException {
        perfilDto.setId(1L);
        String errorMessage = "Ya existe un perfil con el nombre: Admin";
        doThrow(new ServiciosException(errorMessage)).when(perfilRemote).modificarPerfil(any(PerfilDto.class));

        Response response = perfilResource.modificarPerfil(perfilDto);

        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
        assertEquals("{\"error\":\"" + errorMessage + "\"}", response.getEntity());
        verify(perfilRemote, times(1)).modificarPerfil(any(PerfilDto.class));
    }

    @Test
    void testEliminarPerfilExitoso() throws ServiciosException {
        Long id = 1L;
        perfilDto.setId(id);
        when(perfilRemote.obtenerPerfil(id)).thenReturn(perfilDto);
        doNothing().when(perfilRemote).eliminarPerfil(any(PerfilDto.class));

        Response response = perfilResource.eliminarPerfil(id);

        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        assertEquals("{\"message\":\"Perfil inactivado correctamente\"}", response.getEntity());
        verify(perfilRemote, times(1)).obtenerPerfil(id);
        verify(perfilRemote, times(1)).eliminarPerfil(any(PerfilDto.class));
    }

    @Test
    void testEliminarPerfilIdNull() throws ServiciosException {
        Response response = perfilResource.eliminarPerfil(null);

        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
        assertEquals("{\"error\":\"El ID del perfil es obligatorio\"}", response.getEntity());
        verify(perfilRemote, never()).obtenerPerfil(anyLong());
        verify(perfilRemote, never()).eliminarPerfil(any(PerfilDto.class));
    }

    @Test
    void testEliminarPerfilNoExistente() throws ServiciosException {
        Long id = 999L;
        when(perfilRemote.obtenerPerfil(id)).thenReturn(null);

        Response response = perfilResource.eliminarPerfil(id);

        assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());
        assertEquals("{\"error\":\"Perfil no encontrado con ID: 999\"}", response.getEntity());
        verify(perfilRemote, times(1)).obtenerPerfil(id);
        verify(perfilRemote, never()).eliminarPerfil(any(PerfilDto.class));
    }

    @Test
    void testEliminarPerfilServiciosException() throws ServiciosException {
        Long id = 1L;
        perfilDto.setId(id);
        when(perfilRemote.obtenerPerfil(id)).thenReturn(perfilDto);
        String errorMessage = "Error al eliminar el perfil";
        doThrow(new ServiciosException(errorMessage)).when(perfilRemote).eliminarPerfil(any(PerfilDto.class));

        Response response = perfilResource.eliminarPerfil(id);

        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
        assertEquals("{\"error\":\"" + errorMessage + "\"}", response.getEntity());
        verify(perfilRemote, times(1)).obtenerPerfil(id);
        verify(perfilRemote, times(1)).eliminarPerfil(any(PerfilDto.class));
    }

    @Test
    void testEliminarPerfilExistente() throws ServiciosException {
        Long id = 1L;
        when(perfilRemote.obtenerPerfil(id)).thenReturn(perfilDto);
        doNothing().when(perfilRemote).eliminarPerfil(any(PerfilDto.class));

        Response response = perfilResource.eliminarPerfil(id);

        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        verify(perfilRemote, times(1)).obtenerPerfil(id);
        verify(perfilRemote, times(1)).eliminarPerfil(any(PerfilDto.class));
    }

    @Test
    void testObtenerPerfilExistente() {
        Long id = 1L;
        when(perfilRemote.obtenerPerfil(id)).thenReturn(perfilDto);

        Response response = perfilResource.obtenerPerfil(id);

        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        assertNotNull(response.getEntity());
        verify(perfilRemote, times(1)).obtenerPerfil(id);
    }

    @Test
    void testObtenerPerfilNoExistente() {
        Long id = 1L;
        when(perfilRemote.obtenerPerfil(id)).thenReturn(null);

        Response response = perfilResource.obtenerPerfil(id);

        assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());
        verify(perfilRemote, times(1)).obtenerPerfil(id);
    }

    @Test
    void testObtenerPerfiles() {
        List<PerfilDto> expectedList = List.of(perfilDto);
        when(perfilRemote.obtenerPerfiles()).thenReturn(expectedList);

        List<PerfilDto> result = perfilResource.obtenerPerfiles();

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(perfilRemote, times(1)).obtenerPerfiles();
    }

    @Test
    void testListarPerfilesPorNombre() {
        String nombre = "Admin";
        List<PerfilDto> expectedList = List.of(perfilDto);
        when(perfilRemote.listarPerfilesPorNombre(nombre)).thenReturn(expectedList);

        List<PerfilDto> result = perfilResource.listarPerfilesPorNombre(nombre);

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(perfilRemote, times(1)).listarPerfilesPorNombre(nombre);
    }

    @Test
    void testListarPerfilesPorEstado() {
        Estados estado = Estados.ACTIVO;
        List<PerfilDto> expectedList = List.of(perfilDto);
        when(perfilRemote.listarPerfilesPorEstado(estado)).thenReturn(expectedList);

        List<PerfilDto> result = perfilResource.listarPerfilesPorEstado(estado);

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(perfilRemote, times(1)).listarPerfilesPorEstado(estado);
    }
}
