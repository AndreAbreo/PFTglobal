package codigocreativo.uy.servidorapp.entidades;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.mockito.Mockito;
import java.time.LocalDate;

 class BajaEquipoTest {

    private BajaEquipo bajaEquipo;
    private Usuario usuario;
    private Equipo equipo;
    private LocalDate fecha;

    @BeforeEach
    public void setUp() {
        usuario = Mockito.mock(Usuario.class);
        equipo = Mockito.mock(Equipo.class);
        fecha = LocalDate.now();

        bajaEquipo = new BajaEquipo();
        bajaEquipo.setId(1L);
        bajaEquipo.setIdUsuario(usuario);
        bajaEquipo.setIdEquipo(equipo);
        bajaEquipo.setRazon("Razón de la baja");
        bajaEquipo.setFecha(fecha);
        bajaEquipo.setEstado("ACTIVO");
        bajaEquipo.setComentarios("Comentario sobre la baja");
    }

    @Test
     void testGetAndSetId() {
        bajaEquipo.setId(2L);
        assertEquals(2L, bajaEquipo.getId());
    }

    @Test
    void testGetAndSetIdUsuario() {
        Usuario newUsuario = Mockito.mock(Usuario.class);
        bajaEquipo.setIdUsuario(newUsuario);
        assertEquals(newUsuario, bajaEquipo.getIdUsuario());
    }

    @Test
     void testGetAndSetIdEquipo() {
        Equipo newEquipo = Mockito.mock(Equipo.class);
        bajaEquipo.setIdEquipo(newEquipo);
        assertEquals(newEquipo, bajaEquipo.getIdEquipo());
    }

    @Test
     void testGetAndSetRazon() {
        bajaEquipo.setRazon("Nueva razón de baja");
        assertEquals("Nueva razón de baja", bajaEquipo.getRazon());
    }

    @Test
     void testGetAndSetFecha() {
        LocalDate newFecha = LocalDate.of(2024, 12, 25);
        bajaEquipo.setFecha(newFecha);
        assertEquals(newFecha, bajaEquipo.getFecha());
    }

    @Test
     void testGetAndSetEstado() {
        bajaEquipo.setEstado("INACTIVO");
        assertEquals("INACTIVO", bajaEquipo.getEstado());
    }

    @Test
     void testGetAndSetComentarios() {
        bajaEquipo.setComentarios("Nuevo comentario sobre la baja");
        assertEquals("Nuevo comentario sobre la baja", bajaEquipo.getComentarios());
    }

    @Test
     void testConstructor() {
        assertNotNull(bajaEquipo.getId());
        assertNotNull(bajaEquipo.getIdUsuario());
        assertNotNull(bajaEquipo.getIdEquipo());
        assertNotNull(bajaEquipo.getRazon());
        assertNotNull(bajaEquipo.getFecha());
        assertNotNull(bajaEquipo.getEstado());
        assertNotNull(bajaEquipo.getComentarios());
    }
}
