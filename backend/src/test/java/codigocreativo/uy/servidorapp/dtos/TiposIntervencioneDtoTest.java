package codigocreativo.uy.servidorapp.dtos;

import codigocreativo.uy.servidorapp.enumerados.Estados;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TiposIntervencioneDtoTest {

    @Test
    void testEquals_SameObject() {
        TiposIntervencioneDto dto = new TiposIntervencioneDto(1L, "Tipo1", Estados.ACTIVO);
        assertEquals(dto, dto);
    }

    @Test
    void testEquals_NullObject() {
        TiposIntervencioneDto dto = new TiposIntervencioneDto(1L, "Tipo1", Estados.ACTIVO);
        assertNotEquals(null, dto);
    }

    @Test
    void testEquals_DifferentClass() {
        TiposIntervencioneDto dto = new TiposIntervencioneDto(1L, "Tipo1", Estados.ACTIVO);
        assertNotEquals("some string", dto);
    }

    @Test
    void testEquals_DifferentId() {
        TiposIntervencioneDto dto1 = new TiposIntervencioneDto(1L, "Tipo1", Estados.ACTIVO);
        TiposIntervencioneDto dto2 = new TiposIntervencioneDto(2L, "Tipo1", Estados.ACTIVO);
        assertNotEquals(dto1, dto2);
    }

    @Test
    void testEquals_DifferentNombreTipo() {
        TiposIntervencioneDto dto1 = new TiposIntervencioneDto(1L, "Tipo1", Estados.ACTIVO);
        TiposIntervencioneDto dto2 = new TiposIntervencioneDto(1L, "Tipo2", Estados.ACTIVO);
        assertNotEquals(dto1, dto2);
    }

    @Test
    void testEquals_DifferentEstado() {
        TiposIntervencioneDto dto1 = new TiposIntervencioneDto(1L, "Tipo1", Estados.ACTIVO);
        TiposIntervencioneDto dto2 = new TiposIntervencioneDto(1L, "Tipo1", Estados.INACTIVO);
        assertNotEquals(dto1, dto2);
    }

    @Test
    void testHashCode_SameObject() {
        TiposIntervencioneDto dto = new TiposIntervencioneDto(1L, "Tipo1", Estados.ACTIVO);
        assertEquals(dto.hashCode(), dto.hashCode());
    }

    @Test
    void testHashCode_EqualObjects() {
        TiposIntervencioneDto dto1 = new TiposIntervencioneDto(1L, "Tipo1", Estados.ACTIVO);
        TiposIntervencioneDto dto2 = new TiposIntervencioneDto(1L, "Tipo1", Estados.ACTIVO);
        assertEquals(dto1.hashCode(), dto2.hashCode());
    }

    @Test
    void testHashCode_DifferentObjects() {
        TiposIntervencioneDto dto1 = new TiposIntervencioneDto(1L, "Tipo1", Estados.ACTIVO);
        TiposIntervencioneDto dto2 = new TiposIntervencioneDto(2L, "Tipo1", Estados.ACTIVO);
        assertNotEquals(dto1.hashCode(), dto2.hashCode());
    }

    @Test
    void testToString() {
        TiposIntervencioneDto dto = new TiposIntervencioneDto(1L, "Tipo1", Estados.ACTIVO);
        String expected = "Tipo1";
        assertEquals(expected, dto.toString());
    }

    @Test
    void testGettersAndSetters() {
        Long id = 1L;
        String nombreTipo = "Tipo1";
        Estados estado = Estados.ACTIVO;

        TiposIntervencioneDto dto = new TiposIntervencioneDto();
        dto.setId(id);
        dto.setNombreTipo(nombreTipo);
        dto.setEstado(estado);

        assertEquals(id, dto.getId());
        assertEquals(nombreTipo, dto.getNombreTipo());
        assertEquals(estado, dto.getEstado());
    }
}