package codigocreativo.uy.servidorapp.enumerados;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class EstadosTest {
    @Test
    void testGetValor() {
        assertEquals("Activo", Estados.ACTIVO.getValor());
        assertEquals("Inactivo", Estados.INACTIVO.getValor());
        assertEquals("Sin validar", Estados.SIN_VALIDAR.getValor());
    }

    @Test
    void testToString() {
        assertEquals("Activo", Estados.ACTIVO.toString());
        assertEquals("Inactivo", Estados.INACTIVO.toString());
        assertEquals("Sin validar", Estados.SIN_VALIDAR.toString());
    }

    @Test
    void testEnumValuesUnique() {
        Estados[] values = Estados.values();
        assertEquals(3, values.length);
        assertNotEquals(values[0], values[1]);
        assertNotEquals(values[1], values[2]);
        assertNotEquals(values[0], values[2]);
    }
} 