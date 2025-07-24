package codigocreativo.uy.servidorapp.dtos;

import codigocreativo.uy.servidorapp.enumerados.Estados;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TiposEquipoDtoTest {

    @Test
    void testEquals_SameObject() {
        TiposEquipoDto dto = new TiposEquipoDto(1L, "Tipo1", Estados.ACTIVO);
        assertEquals(dto, dto);
    }

    @Test
    void testEquals_NullObject() {
        TiposEquipoDto dto = new TiposEquipoDto(1L, "Tipo1", Estados.ACTIVO);
        assertNotEquals(null, dto);
    }

    @Test
    void testEquals_DifferentClass() {
        TiposEquipoDto dto = new TiposEquipoDto(1L, "Tipo1", Estados.ACTIVO);
        assertNotEquals("some string", dto);
    }

    @Test
    void testEquals_DifferentId() {
        TiposEquipoDto dto1 = new TiposEquipoDto(1L, "Tipo1", Estados.ACTIVO);
        TiposEquipoDto dto2 = new TiposEquipoDto(2L, "Tipo1", Estados.ACTIVO);
        assertNotEquals(dto1, dto2);
    }

    @Test
    void testEquals_DifferentNombre() {
        TiposEquipoDto dto1 = new TiposEquipoDto(1L, "Tipo1", Estados.ACTIVO);
        TiposEquipoDto dto2 = new TiposEquipoDto(1L, "Tipo2", Estados.ACTIVO);
        assertNotEquals(dto1, dto2);
    }

    @Test
    void testEquals_DifferentEstado() {
        TiposEquipoDto dto1 = new TiposEquipoDto(1L, "Tipo1", Estados.ACTIVO);
        TiposEquipoDto dto2 = new TiposEquipoDto(1L, "Tipo1", Estados.INACTIVO);
        assertEquals(dto1, dto2); // Estado no se considera en equals
    }

    @Test
    void testHashCode_SameObject() {
        TiposEquipoDto dto = new TiposEquipoDto(1L, "Tipo1", Estados.ACTIVO);
        assertEquals(dto.hashCode(), dto.hashCode());
    }

    @Test
    void testHashCode_EqualObjects() {
        TiposEquipoDto dto1 = new TiposEquipoDto(1L, "Tipo1", Estados.ACTIVO);
        TiposEquipoDto dto2 = new TiposEquipoDto(1L, "Tipo1", Estados.ACTIVO);
        assertEquals(dto1.hashCode(), dto2.hashCode());
    }

    @Test
    void testHashCode_DifferentObjects() {
        TiposEquipoDto dto1 = new TiposEquipoDto(1L, "Tipo1", Estados.ACTIVO);
        TiposEquipoDto dto2 = new TiposEquipoDto(2L, "Tipo1", Estados.ACTIVO);
        assertNotEquals(dto1.hashCode(), dto2.hashCode());
    }

    @Test
    void testToString() {
        TiposEquipoDto dto = new TiposEquipoDto(1L, "Tipo1", Estados.ACTIVO);
        String expected = "Tipo1";
        assertEquals(expected, dto.toString());
    }

    @Test
    void testGettersAndSetters() {
        Long id = 1L;
        String nombre = "Tipo1";
        Estados estado = Estados.ACTIVO;

        TiposEquipoDto dto = new TiposEquipoDto();
        dto.setId(id);
        dto.setNombreTipo(nombre);
        dto.setEstado(estado);

        assertEquals(id, dto.getId());
        assertEquals(nombre, dto.getNombreTipo());
        assertEquals(estado, dto.getEstado());
    }
}