package codigocreativo.uy.servidorapp.entidades;

import static org.junit.jupiter.api.Assertions.*;

import codigocreativo.uy.servidorapp.enumerados.Estados;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class UbicacionTest {

    private Ubicacion ubicacion;
    private Institucion institucion;

    @BeforeEach
    void setUp() {
        ubicacion = new Ubicacion();
        institucion = new Institucion(); // Suponiendo que tienes una clase Institucion
    }

    @Test
    void testGetAndSetId() {
        Long id = 1L;
        ubicacion.setId(id);
        assertEquals(id, ubicacion.getId());
    }

    @Test
    void testGetAndSetNombre() {
        String nombre = "Sala A";
        ubicacion.setNombre(nombre);
        assertEquals(nombre, ubicacion.getNombre());
    }

    @Test
    void testGetAndSetSector() {
        String sector = "Oncología";
        ubicacion.setSector(sector);
        assertEquals(sector, ubicacion.getSector());
    }

    @Test
    void testGetAndSetPiso() {
        Long piso = 3L;
        ubicacion.setPiso(piso);
        assertEquals(piso, ubicacion.getPiso());
    }

    @Test
    void testGetAndSetNumero() {
        Long numero = 101L;
        ubicacion.setNumero(numero);
        assertEquals(numero, ubicacion.getNumero());
    }

    @Test
    void testGetAndSetCama() {
        Long cama = 5L;
        ubicacion.setCama(cama);
        assertEquals(cama, ubicacion.getCama());
    }

    @Test
    void testGetAndSetIdInstitucion() {
        ubicacion.setIdInstitucion(institucion);
        assertEquals(institucion, ubicacion.getIdInstitucion());
    }

    @Test
    void testGetAndSetEstado() {
        Estados estado = Estados.ACTIVO; // Suponiendo que tienes un enum Estados con valor ACTIVO
        ubicacion.setEstado(estado);
        assertEquals(estado, ubicacion.getEstado());
    }

    @Test
    void testToString() {
        String nombre = "Sala B";
        ubicacion.setNombre(nombre);
        assertEquals(nombre, ubicacion.toString());
    }
}
