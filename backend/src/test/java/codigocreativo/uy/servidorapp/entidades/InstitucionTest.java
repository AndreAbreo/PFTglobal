package codigocreativo.uy.servidorapp.entidades;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

 class InstitucionTest {

    private Institucion institucion;

    @BeforeEach
    public void setUp() {
        institucion = new Institucion();
    }

    @Test
     void testSetAndGetId() {
        Long id = 1L;
        institucion.setId(id);
        assertEquals(id, institucion.getId());
    }

    @Test
     void testSetAndGetNombre() {
        String nombre = "Hospital General";
        institucion.setNombre(nombre);
        assertEquals(nombre, institucion.getNombre());
    }

    @Test
     void testToString() {
        Long id = 1L;
        String nombre = "Hospital General";
        institucion.setId(id);
        institucion.setNombre(nombre);

        String expected = id + " - " + nombre;
        assertEquals(expected, institucion.toString());
    }
}
