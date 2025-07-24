package codigocreativo.uy.servidorapp.entidades;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class ProveedoresEquipoTest {

    private ProveedoresEquipo proveedoresEquipo;
    private Pais paisMock;

    @BeforeEach
    void setUp() {
        proveedoresEquipo = new ProveedoresEquipo();
        paisMock = Mockito.mock(Pais.class);
    }

    @Test
    void testGetAndSetId() {
        Long id = 1L;
        proveedoresEquipo.setId(id);
        assertEquals(id, proveedoresEquipo.getId());
    }

    @Test
    void testGetAndSetNombre() {
        String nombre = "Proveedor A";
        proveedoresEquipo.setNombre(nombre);
        assertEquals(nombre, proveedoresEquipo.getNombre());
    }

    @Test
    void testGetAndSetEstado() {
        String estado = "Activo";
        proveedoresEquipo.setEstado(estado);
        assertEquals(estado, proveedoresEquipo.getEstado());
    }

    @Test
    void testGetAndSetPais() {
        proveedoresEquipo.setPais(paisMock);
        assertEquals(paisMock, proveedoresEquipo.getPais());
    }

    @Test
    void testToString() {
        String nombre = "Proveedor B";
        proveedoresEquipo.setNombre(nombre);
        assertEquals(nombre, proveedoresEquipo.toString());
    }
}
