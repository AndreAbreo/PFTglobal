package codigocreativo.uy.servidorapp.entidades;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

 class PaisTest {

    private Pais pais;

    @BeforeEach
    public void setUp() {
        pais = new Pais();
    }

    @Test
     void testSetAndGetId() {
        Long id = 1L;
        pais.setId(id);
        assertEquals(id, pais.getId());
    }

    @Test
     void testSetAndGetNombre() {
        String nombre = "Argentina";
        pais.setNombre(nombre);
        assertEquals(nombre, pais.getNombre());
    }

    @Test
     void testToString() {
        String nombre = "Argentina";
        pais.setNombre(nombre);
        assertEquals(nombre, pais.toString());
    }

    @Test
     void testConstructorWithArguments() {
        Long id = 1L;
        String nombre = "Argentina";
        pais = new Pais(id, nombre);

        assertEquals(id, pais.getId());
        assertEquals(nombre, pais.getNombre());
    }

    @Test
    void testDefaultConstructor() {
        pais = new Pais();
        assertNull(pais.getId());
        assertNull(pais.getNombre());
    }
}
