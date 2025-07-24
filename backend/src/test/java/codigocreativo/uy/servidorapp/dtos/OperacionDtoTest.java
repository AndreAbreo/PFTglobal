package codigocreativo.uy.servidorapp.dtos;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OperacionDtoTest {

    @Test
    void testEquals_SameObject() {
        OperacionDto dto = new OperacionDto(1L, "Operacion1");
        assertEquals(dto, dto);
    }

    @Test
    void testEquals_NullObject() {
        OperacionDto dto = new OperacionDto(1L, "Operacion1");
        assertNotEquals(null, dto);
    }

    @Test
    void testEquals_DifferentClass() {
        OperacionDto dto = new OperacionDto(1L, "Operacion1");
        assertNotEquals("some string", dto);
    }

    @Test
    void testEquals_DifferentId() {
        OperacionDto dto1 = new OperacionDto(1L, "Operacion1");
        OperacionDto dto2 = new OperacionDto(2L, "Operacion1");
        assertNotEquals(dto1, dto2);
    }

    @Test
    void testEquals_DifferentNombreOperacion() {
        OperacionDto dto1 = new OperacionDto(1L, "Operacion1");
        OperacionDto dto2 = new OperacionDto(1L, "Operacion2");
        assertNotEquals(dto1, dto2);
    }

    @Test
    void testHashCode_SameObject() {
        OperacionDto dto = new OperacionDto(1L, "Operacion1");
        assertEquals(dto.hashCode(), dto.hashCode());
    }

    @Test
    void testHashCode_EqualObjects() {
        OperacionDto dto1 = new OperacionDto(1L, "Operacion1");
        OperacionDto dto2 = new OperacionDto(1L, "Operacion1");
        assertEquals(dto1.hashCode(), dto2.hashCode());
    }

    @Test
    void testHashCode_DifferentObjects() {
        OperacionDto dto1 = new OperacionDto(1L, "Operacion1");
        OperacionDto dto2 = new OperacionDto(2L, "Operacion1");
        assertNotEquals(dto1.hashCode(), dto2.hashCode());
    }

    @Test
    void testToString() {
        OperacionDto dto = new OperacionDto(1L, "Operacion1");
        String expected = "Operacion1";
        assertEquals(expected, dto.toString());
    }

    @Test
    void testGettersAndSetters() {
        Long id = 1L;
        String nombreOperacion = "Operacion1";

        OperacionDto dto = new OperacionDto();
        dto.setId(id);
        dto.setNombreOperacion(nombreOperacion);

        assertEquals(id, dto.getId());
        assertEquals(nombreOperacion, dto.getNombreOperacion());
    }
}