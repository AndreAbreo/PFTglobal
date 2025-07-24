package codigocreativo.uy.servidorapp.entidades;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TiposEquipoTest {

    private TiposEquipo tiposEquipo;

    @BeforeEach
    void setUp() {
        tiposEquipo = new TiposEquipo();
    }

    @Test
    void testGetAndSetId() {
        Long id = 1L;
        tiposEquipo.setId(id);
        assertEquals(id, tiposEquipo.getId());
    }

    @Test
    void testGetAndSetNombreTipo() {
        String nombreTipo = "Monitores";
        tiposEquipo.setNombreTipo(nombreTipo);
        assertEquals(nombreTipo, tiposEquipo.getNombreTipo());
    }

    @Test
    void testGetAndSetEstado() {
        String estado = "Activo";
        tiposEquipo.setEstado(estado);
        assertEquals(estado, tiposEquipo.getEstado());
    }

    @Test
    void testToString() {
        String nombreTipo = "Rayos X";
        tiposEquipo.setNombreTipo(nombreTipo);
        assertEquals(nombreTipo, tiposEquipo.toString());
    }
}
