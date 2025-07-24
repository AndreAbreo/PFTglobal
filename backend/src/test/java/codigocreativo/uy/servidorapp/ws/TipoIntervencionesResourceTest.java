package codigocreativo.uy.servidorapp.ws;

import codigocreativo.uy.servidorapp.dtos.TiposIntervencioneDto;
import codigocreativo.uy.servidorapp.servicios.TipoIntervencioneRemote;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import jakarta.ws.rs.core.Response;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

class TipoIntervencionesResourceTest {

    @Mock
    private TipoIntervencioneRemote er;

    @InjectMocks
    private TipoIntervencionesResource resource;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCrear() {
        TiposIntervencioneDto dto = new TiposIntervencioneDto();
        Response response = resource.crear(dto);

        verify(er, times(1)).crearTipoIntervencion(dto);
        assertEquals(201, response.getStatus());
    }

    @Test
    void testModificar() {
        TiposIntervencioneDto dto = new TiposIntervencioneDto();
        Response response = resource.modificar(dto);

        verify(er, times(1)).modificarTipoIntervencion(dto);
        assertEquals(200, response.getStatus());
    }

    @Test
    void testEliminar() {
        Long id = 1L;
        Response response = resource.eliminar(id);

        verify(er, times(1)).eliminarTipoIntervencion(id);
        assertEquals(200, response.getStatus());
    }

    @Test
    void testListarTodos() {
        List<TiposIntervencioneDto> expectedList = Arrays.asList(new TiposIntervencioneDto(), new TiposIntervencioneDto());
        when(er.obtenerTiposIntervenciones()).thenReturn(expectedList);

        List<TiposIntervencioneDto> actualList = resource.listarTodos();

        verify(er, times(1)).obtenerTiposIntervenciones();
        assertEquals(expectedList.size(), actualList.size());
        assertEquals(expectedList, actualList);
    }

    @Test
    void testBuscarPorId() {
        Long id = 1L;
        TiposIntervencioneDto expectedDto = new TiposIntervencioneDto();
        when(er.obtenerTipoIntervencion(id)).thenReturn(expectedDto);

        TiposIntervencioneDto actualDto = resource.buscarPorId(id);

        verify(er, times(1)).obtenerTipoIntervencion(id);
        assertNotNull(actualDto);
        assertEquals(expectedDto, actualDto);
    }
}
