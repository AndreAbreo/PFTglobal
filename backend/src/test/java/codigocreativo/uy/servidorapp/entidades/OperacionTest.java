package codigocreativo.uy.servidorapp.entidades;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

 class OperacionTest {

    private Operacion operacion;

    @BeforeEach
    public void setUp() {
        operacion = new Operacion();
    }

    @Test
     void testSetAndGetId() {
        Long id = 1L;
        operacion.setId(id);
        assertEquals(id, operacion.getId());
    }

    @Test
     void testSetAndGetNombreOperacion() {
        String nombreOperacion = "Operacion1";
        operacion.setNombreOperacion(nombreOperacion);
        assertEquals(nombreOperacion, operacion.getNombreOperacion());
    }

    @Test
     void testConstructorAndSetters() {
        Long id = 1L;
        String nombreOperacion = "Operacion2";
        operacion.setId(id);
        operacion.setNombreOperacion(nombreOperacion);

        assertEquals(id, operacion.getId());
        assertEquals(nombreOperacion, operacion.getNombreOperacion());
    }
}
