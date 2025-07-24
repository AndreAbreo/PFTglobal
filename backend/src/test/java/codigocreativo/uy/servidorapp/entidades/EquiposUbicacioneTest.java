package codigocreativo.uy.servidorapp.entidades;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.mockito.Mockito;
import java.time.LocalDate;

 class EquiposUbicacioneTest {

    private EquiposUbicacione equiposUbicacione;
    private Usuario usuario;
    private Ubicacion ubicacion;
    private Equipo equipo;
    private LocalDate fecha;

    @BeforeEach
    public void setUp() {
        usuario = Mockito.mock(Usuario.class);
        ubicacion = Mockito.mock(Ubicacion.class);
        equipo = Mockito.mock(Equipo.class);
        fecha = LocalDate.now();

        equiposUbicacione = new EquiposUbicacione();
        equiposUbicacione.setId(1L);
        equiposUbicacione.setFecha(fecha);
        equiposUbicacione.setIdEquipo(equipo);
        equiposUbicacione.setIdUbicacion(ubicacion);
        equiposUbicacione.setUsuario(usuario);
        equiposUbicacione.setObservaciones("Observaciones de prueba");
    }

    @Test
     void testGetAndSetId() {
        equiposUbicacione.setId(2L);
        assertEquals(2L, equiposUbicacione.getId());
    }

    @Test
    void testGetAndSetFecha() {
        LocalDate newFecha = LocalDate.of(2024, 11, 12);
        equiposUbicacione.setFecha(newFecha);
        assertEquals(newFecha, equiposUbicacione.getFecha());
    }

    @Test
     void testGetAndSetIdEquipo() {
        Equipo newEquipo = Mockito.mock(Equipo.class);
        equiposUbicacione.setIdEquipo(newEquipo);
        assertEquals(newEquipo, equiposUbicacione.getIdEquipo());
    }

    @Test
    void testGetAndSetIdUbicacion() {
        Ubicacion newUbicacion = Mockito.mock(Ubicacion.class);
        equiposUbicacione.setIdUbicacion(newUbicacion);
        assertEquals(newUbicacion, equiposUbicacione.getIdUbicacion());
    }

    @Test
    void testGetAndSetUsuario() {
        Usuario newUsuario = Mockito.mock(Usuario.class);
        equiposUbicacione.setUsuario(newUsuario);
        assertEquals(newUsuario, equiposUbicacione.getUsuario());
    }

    @Test
     void testGetAndSetObservaciones() {
        equiposUbicacione.setObservaciones("Nuevo comentario");
        assertEquals("Nuevo comentario", equiposUbicacione.getObservaciones());
    }

    @Test
     void testConstructor() {
        assertNotNull(equiposUbicacione.getId());
        assertNotNull(equiposUbicacione.getFecha());
        assertNotNull(equiposUbicacione.getIdEquipo());
        assertNotNull(equiposUbicacione.getIdUbicacion());
        assertNotNull(equiposUbicacione.getUsuario());
        assertNotNull(equiposUbicacione.getObservaciones());
    }
}
