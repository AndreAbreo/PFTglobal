package codigocreativo.uy.servidorapp.entidades;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

 class MarcasModeloTest {

    private MarcasModelo marcasModelo;

    @BeforeEach
    public void setUp() {
        marcasModelo = new MarcasModelo();
    }

    @Test
     void testSetAndGetId() {
        Long id = 1L;
        marcasModelo.setId(id);
        assertEquals(id, marcasModelo.getId());
    }

    @Test
     void testSetAndGetNombre() {
        String nombre = "Marca X";
        marcasModelo.setNombre(nombre);
        assertEquals(nombre, marcasModelo.getNombre());
    }

    @Test
     void testSetAndGetEstado() {
        String estado = "Activo";
        marcasModelo.setEstado(estado);
        assertEquals(estado, marcasModelo.getEstado());
    }

    @Test
     void testConstructorAndSetters() {
        Long id = 1L;
        String nombre = "Marca Y";
        String estado = "Inactivo";

        marcasModelo.setId(id);
        marcasModelo.setNombre(nombre);
        marcasModelo.setEstado(estado);

        assertEquals(id, marcasModelo.getId());
        assertEquals(nombre, marcasModelo.getNombre());
        assertEquals(estado, marcasModelo.getEstado());
    }

    @Test
     void testToString() {
        String nombre = "Marca Z";
        marcasModelo.setNombre(nombre);
        assertEquals(nombre, marcasModelo.getNombre());
    }
}
