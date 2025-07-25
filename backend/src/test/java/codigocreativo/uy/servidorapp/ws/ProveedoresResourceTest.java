package codigocreativo.uy.servidorapp.ws;

import codigocreativo.uy.servidorapp.dtos.ProveedoresEquipoDto;
import codigocreativo.uy.servidorapp.enumerados.Estados;
import codigocreativo.uy.servidorapp.servicios.ProveedoresEquipoRemote;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import jakarta.ws.rs.core.Response;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ProveedoresResourceTest {

    @Mock
    private ProveedoresEquipoRemote er;

    @InjectMocks
    private ProveedoresResource resource;

    private ProveedoresEquipoDto proveedorDto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        proveedorDto = new ProveedoresEquipoDto();
        proveedorDto.setNombre("Proveedor Test");
        proveedorDto.setEstado(Estados.ACTIVO);
    }

    @Test
    void testCrearProveedor_success() {
        doNothing().when(er).crearProveedor(any(ProveedoresEquipoDto.class));

        Response response = resource.crearProveedor(proveedorDto);

        assertEquals(Response.Status.CREATED.getStatusCode(), response.getStatus());
        Map<String, String> entity = (Map<String, String>) response.getEntity();
        assertEquals("Proveedor creado correctamente", entity.get("message"));
        verify(er, times(1)).crearProveedor(any(ProveedoresEquipoDto.class));
    }

    @Test
    void testCrearProveedor_error() {
        doThrow(new IllegalArgumentException("Ya existe un proveedor con ese nombre")).when(er).crearProveedor(any(ProveedoresEquipoDto.class));

        Response response = resource.crearProveedor(proveedorDto);

        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
        Map<String, String> entity = (Map<String, String>) response.getEntity();
        assertEquals("Ya existe un proveedor con ese nombre", entity.get("error"));
    }

    @Test
    void testModificarProveedor_success() {
        doNothing().when(er).modificarProveedor(any(ProveedoresEquipoDto.class));

        Response response = resource.modificarProveedor(proveedorDto);

        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        Map<String, String> entity = (Map<String, String>) response.getEntity();
        assertEquals("Proveedor modificado correctamente", entity.get("message"));
        verify(er, times(1)).modificarProveedor(any(ProveedoresEquipoDto.class));
    }

    @Test
    void testModificarProveedor_error() {
        doThrow(new IllegalArgumentException("Ya existe otro proveedor con ese nombre")).when(er).modificarProveedor(any(ProveedoresEquipoDto.class));

        Response response = resource.modificarProveedor(proveedorDto);

        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
        Map<String, String> entity = (Map<String, String>) response.getEntity();
        assertEquals("Ya existe otro proveedor con ese nombre", entity.get("error"));
    }

    @Test
    void testEliminarProveedor_success() {
        doNothing().when(er).eliminarProveedor(1L);

        Response response = resource.eliminarProveedor(1L);

        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        Map<String, String> entity = (Map<String, String>) response.getEntity();
        assertEquals("Proveedor inactivado correctamente", entity.get("message"));
        verify(er, times(1)).eliminarProveedor(1L);
    }

    @Test
    void testEliminarProveedor_error() {
        doThrow(new IllegalArgumentException("Proveedor no encontrado")).when(er).eliminarProveedor(1L);

        Response response = resource.eliminarProveedor(1L);

        assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());
        Map<String, String> entity = (Map<String, String>) response.getEntity();
        assertEquals("Proveedor no encontrado", entity.get("error"));
    }

    @Test
    void testListarProveedores() {
        List<ProveedoresEquipoDto> expectedList = Arrays.asList(proveedorDto);
        when(er.obtenerProveedores()).thenReturn(expectedList);

        List<ProveedoresEquipoDto> result = resource.listarProveedores();

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(er, times(1)).obtenerProveedores();
    }

    @Test
    void testBuscarPorId_success() {
        when(er.obtenerProveedor(1L)).thenReturn(proveedorDto);

        Response response = resource.buscarPorId(1L);

        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        assertEquals(proveedorDto, response.getEntity());
        verify(er, times(1)).obtenerProveedor(1L);
    }

    @Test
    void testBuscarPorId_notFound() {
        when(er.obtenerProveedor(1L)).thenReturn(null);

        Response response = resource.buscarPorId(1L);

        assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());
        Map<String, String> entity = (Map<String, String>) response.getEntity();
        assertEquals("Proveedor no encontrado", entity.get("error"));
    }

    @Test
    void testFiltrarProveedores_soloNombre() {
        List<ProveedoresEquipoDto> expectedList = Arrays.asList(proveedorDto);
        when(er.filtrarProveedores("test", null, null)).thenReturn(expectedList);

        Response response = resource.filtrarProveedores("test", null, null);

        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        assertEquals(expectedList, response.getEntity());
        verify(er, times(1)).filtrarProveedores("test", null, null);
    }

    @Test
    void testFiltrarProveedores_soloEstado() {
        List<ProveedoresEquipoDto> expectedList = Arrays.asList(proveedorDto);
        when(er.filtrarProveedores(null, "ACTIVO", null)).thenReturn(expectedList);

        Response response = resource.filtrarProveedores(null, "ACTIVO", null);

        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        assertEquals(expectedList, response.getEntity());
        verify(er, times(1)).filtrarProveedores(null, "ACTIVO", null);
    }

    @Test
    void testFiltrarProveedores_nombreYEstado() {
        List<ProveedoresEquipoDto> expectedList = Arrays.asList(proveedorDto);
        when(er.filtrarProveedores("test", "ACTIVO", null)).thenReturn(expectedList);

        Response response = resource.filtrarProveedores("test", "ACTIVO", null);

        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        assertEquals(expectedList, response.getEntity());
        verify(er, times(1)).filtrarProveedores("test", "ACTIVO", null);
    }

    @Test
    void testFiltrarProveedores_porPais() {
        List<ProveedoresEquipoDto> expectedList = Arrays.asList(proveedorDto);
        when(er.filtrarProveedores(null, null, "Uruguay")).thenReturn(expectedList);

        Response response = resource.filtrarProveedores(null, null, "Uruguay");

        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        assertEquals(expectedList, response.getEntity());
        verify(er, times(1)).filtrarProveedores(null, null, "Uruguay");
    }

    @Test
    void testFiltrarProveedores_sinFiltros() {
        List<ProveedoresEquipoDto> expectedList = Arrays.asList(proveedorDto);
        when(er.filtrarProveedores(null, null, null)).thenReturn(expectedList);

        Response response = resource.filtrarProveedores(null, null, null);

        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        assertEquals(expectedList, response.getEntity());
        verify(er, times(1)).filtrarProveedores(null, null, null);
    }

    @Test
    void testFiltrarProveedores_estadoInvalido() {
        when(er.filtrarProveedores(null, "ESTADO_INVALIDO", null)).thenThrow(new IllegalArgumentException("Estado inválido"));

        Response response = resource.filtrarProveedores(null, "ESTADO_INVALIDO", null);

        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
        Map<String, String> entity = (Map<String, String>) response.getEntity();
        assertTrue(entity.get("error").contains("Estado inválido"));
    }
}
