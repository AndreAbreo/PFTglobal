package codigocreativo.uy.servidorapp.enumerados;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class SectoresTest {
    @Test
    void testGetValor() {
        assertEquals("Policlínico", Sectores.POLICLINICO.getValor());
        assertEquals("Internación", Sectores.INTERNACION.getValor());
        assertEquals("Emergencia", Sectores.EMERGENCIA.getValor());
        assertEquals("CTI", Sectores.CTI.getValor());
        assertEquals("Otro", Sectores.OTRO.getValor());
    }

    @Test
    void testEnumValuesUnique() {
        Sectores[] values = Sectores.values();
        assertEquals(5, values.length);
        for (int i = 0; i < values.length; i++) {
            for (int j = i + 1; j < values.length; j++) {
                assertNotEquals(values[i], values[j]);
            }
        }
    }
} 