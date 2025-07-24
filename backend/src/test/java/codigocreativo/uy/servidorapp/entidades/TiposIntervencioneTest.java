package codigocreativo.uy.servidorapp.entidades;

import static org.junit.jupiter.api.Assertions.*;

import codigocreativo.uy.servidorapp.enumerados.Estados;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TiposIntervencioneTest {

    private TiposIntervencione tiposIntervencione;

    @BeforeEach
    void setUp() {
        tiposIntervencione = new TiposIntervencione();
    }

    @Test
    void testGetAndSetId() {
        Long id = 1L;
        tiposIntervencione.setId(id);
        assertEquals(id, tiposIntervencione.getId());
    }

    @Test
    void testGetAndSetNombreTipo() {
        String nombreTipo = "Mantenimiento";
        tiposIntervencione.setNombreTipo(nombreTipo);
        assertEquals(nombreTipo, tiposIntervencione.getNombreTipo());
    }

    @Test
    void testGetAndSetEstado() {
        Estados estado = Estados.ACTIVO; // Suponiendo que tienes un enum Estados con valor ACTIVO
        tiposIntervencione.setEstado(estado);
        assertEquals(estado, tiposIntervencione.getEstado());
    }

    @Test
    void testToString() {
        String nombreTipo = "Reparación";
        tiposIntervencione.setNombreTipo(nombreTipo);
        assertEquals(nombreTipo, tiposIntervencione.toString());
    }
}
