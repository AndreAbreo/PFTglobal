package codigocreativo.uy.servidorapp.dtos;

import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

class InstitucionDtoTest {

    @Test
    void testEquals_SameObject() {
        InstitucionDto dto = new InstitucionDto(1L, "Institucion1");
        assertEquals(dto, dto);
    }

    @Test
    void testEquals_NullObject() {
        InstitucionDto dto = new InstitucionDto(1L, "Institucion1");
        assertNotEquals(null, dto);
    }

    @Test
    void testEquals_DifferentClass() {
        InstitucionDto dto = new InstitucionDto(1L, "Institucion1");
        assertNotEquals("some string", dto);
    }

    @Test
    void testEquals_DifferentId() {
        InstitucionDto dto1 = new InstitucionDto(1L, "Institucion1");
        InstitucionDto dto2 = new InstitucionDto(2L, "Institucion1");
        assertNotEquals(dto1, dto2);
    }

    @Test
    void testEquals_DifferentNombre() {
        InstitucionDto dto1 = new InstitucionDto(1L, "Institucion1");
        InstitucionDto dto2 = new InstitucionDto(1L, "Institucion2");
        assertNotEquals(dto1, dto2);
    }

    @Test
    void testHashCode_SameObject() {
        InstitucionDto dto = new InstitucionDto(1L, "Institucion1");
        assertEquals(dto.hashCode(), dto.hashCode());
    }

    @Test
    void testHashCode_EqualObjects() {
        InstitucionDto dto1 = new InstitucionDto(1L, "Institucion1");
        InstitucionDto dto2 = new InstitucionDto(1L, "Institucion1");
        assertEquals(dto1.hashCode(), dto2.hashCode());
    }

    @Test
    void testHashCode_DifferentObjects() {
        InstitucionDto dto1 = new InstitucionDto(1L, "Institucion1");
        InstitucionDto dto2 = new InstitucionDto(2L, "Institucion1");
        assertNotEquals(dto1.hashCode(), dto2.hashCode());
    }

    @Test
    void testToString() {
        InstitucionDto dto = new InstitucionDto(1L, "Institucion1");
        String expected = "1 - Institucion1";
        assertEquals(expected, dto.toString());
    }

    @Test
    void testGettersAndSetters() {
        Long id = 1L;
        String nombre = "Institucion1";

        InstitucionDto dto = new InstitucionDto();
        dto.setId(id);
        dto.setNombre(nombre);

        assertEquals(id, dto.getId());
        assertEquals(nombre, dto.getNombre());

    }
}