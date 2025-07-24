package codigocreativo.uy.servidorapp.dtos;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PaisDtoTest {

    @Test
    void testEquals_SameObject() {
        PaisDto dto = new PaisDto(1L, "Pais1");
        assertEquals(dto, dto);
    }

    @Test
    void testEquals_NullObject() {
        PaisDto dto = new PaisDto(1L, "Pais1");
        assertNotEquals(null, dto);
    }

    @Test
    void testEquals_DifferentClass() {
        PaisDto dto = new PaisDto(1L, "Pais1");
        assertNotEquals("some string", dto);
    }

    @Test
    void testEquals_DifferentId() {
        PaisDto dto1 = new PaisDto(1L, "Pais1");
        PaisDto dto2 = new PaisDto(2L, "Pais1");
        assertNotEquals(dto1, dto2);
    }

    @Test
    void testEquals_DifferentNombre() {
        PaisDto dto1 = new PaisDto(1L, "Pais1");
        PaisDto dto2 = new PaisDto(1L, "Pais2");
        assertNotEquals(dto1, dto2);
    }

    @Test
    void testHashCode_SameObject() {
        PaisDto dto = new PaisDto(1L, "Pais1");
        assertEquals(dto.hashCode(), dto.hashCode());
    }

    @Test
    void testHashCode_EqualObjects() {
        PaisDto dto1 = new PaisDto(1L, "Pais1");
        PaisDto dto2 = new PaisDto(1L, "Pais1");
        assertEquals(dto1.hashCode(), dto2.hashCode());
    }

    @Test
    void testHashCode_DifferentObjects() {
        PaisDto dto1 = new PaisDto(1L, "Pais1");
        PaisDto dto2 = new PaisDto(2L, "Pais1");
        assertNotEquals(dto1.hashCode(), dto2.hashCode());
    }

    @Test
    void testToString() {
        PaisDto dto = new PaisDto(1L, "Pais1");
        String expected = "Pais1";
        assertEquals(expected, dto.toString());
    }

    @Test
    void testGettersAndSetters() {
        Long id = 1L;
        String nombre = "Pais1";

        PaisDto dto = new PaisDto();
        dto.setId(id);
        dto.setNombre(nombre);

        assertEquals(id, dto.getId());
        assertEquals(nombre, dto.getNombre());
    }
}