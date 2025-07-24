package codigocreativo.uy.servidorapp.entidades;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.mockito.Mockito;
import java.time.LocalDate;

 class BajaUbicacionTest {

    private BajaUbicacion bajaUbicacion;
    private Usuario usuario;
    private Ubicacion ubicacion;
    private LocalDate fecha;

    @BeforeEach
    public void setUp() {
        usuario = Mockito.mock(Usuario.class);
        ubicacion = Mockito.mock(Ubicacion.class);
        fecha = LocalDate.now();

        bajaUbicacion = new BajaUbicacion();
        bajaUbicacion.setId(1L);
        bajaUbicacion.setIdUsuario(usuario);
        bajaUbicacion.setIdUbicacion(ubicacion);
        bajaUbicacion.setRazon("Razón de la baja");
        bajaUbicacion.setComentario("Comentario sobre la baja");
        bajaUbicacion.setFecha(fecha);
    }

    @Test
     void testGetAndSetId() {
        bajaUbicacion.setId(2L);
        assertEquals(2L, bajaUbicacion.getId());
    }

    @Test
     void testGetAndSetIdUsuario() {
        Usuario newUsuario = Mockito.mock(Usuario.class);
        bajaUbicacion.setIdUsuario(newUsuario);
        assertEquals(newUsuario, bajaUbicacion.getIdUsuario());
    }

    @Test
     void testGetAndSetIdUbicacion() {
        Ubicacion newUbicacion = Mockito.mock(Ubicacion.class);
        bajaUbicacion.setIdUbicacion(newUbicacion);
        assertEquals(newUbicacion, bajaUbicacion.getIdUbicacion());
    }

    @Test
     void testGetAndSetRazon() {
        bajaUbicacion.setRazon("Nueva razón de baja");
        assertEquals("Nueva razón de baja", bajaUbicacion.getRazon());
    }

    @Test
     void testGetAndSetComentario() {
        bajaUbicacion.setComentario("Nuevo comentario sobre la baja");
        assertEquals("Nuevo comentario sobre la baja", bajaUbicacion.getComentario());
    }

    @Test
     void testGetAndSetFecha() {
        LocalDate newFecha = LocalDate.of(2024, 12, 25);
        bajaUbicacion.setFecha(newFecha);
        assertEquals(newFecha, bajaUbicacion.getFecha());
    }

    @Test
     void testConstructor() {
        assertNotNull(bajaUbicacion.getId());
        assertNotNull(bajaUbicacion.getIdUsuario());
        assertNotNull(bajaUbicacion.getIdUbicacion());
        assertNotNull(bajaUbicacion.getRazon());
        assertNotNull(bajaUbicacion.getComentario());
        assertNotNull(bajaUbicacion.getFecha());
    }
}
