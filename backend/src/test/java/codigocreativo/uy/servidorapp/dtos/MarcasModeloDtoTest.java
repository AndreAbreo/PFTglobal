package codigocreativo.uy.servidorapp.dtos;

import codigocreativo.uy.servidorapp.enumerados.Estados;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MarcasModeloDtoTest {

    @Test
    void testEquals_SameObject() {
        MarcasModeloDto dto = new MarcasModeloDto(1L, "Modelo1", Estados.ACTIVO);
        assertEquals(dto, dto);
    }

    @Test
    void testEquals_NullObject() {
        MarcasModeloDto dto = new MarcasModeloDto(1L, "Modelo1", Estados.ACTIVO);
        assertNotEquals(null, dto);
    }

    @Test
    void testEquals_DifferentClass() {
        MarcasModeloDto dto = new MarcasModeloDto(1L, "Modelo1", Estados.ACTIVO);
        assertNotEquals("some string", dto);
    }

    @Test
    void testEquals_DifferentId() {
        MarcasModeloDto dto1 = new MarcasModeloDto(1L, "Modelo1", Estados.ACTIVO);
        MarcasModeloDto dto2 = new MarcasModeloDto(2L, "Modelo1", Estados.ACTIVO);
        assertNotEquals(dto1, dto2);
    }

    @Test
    void testEquals_DifferentNombre() {
        MarcasModeloDto dto1 = new MarcasModeloDto(1L, "Modelo1", Estados.ACTIVO);
        MarcasModeloDto dto2 = new MarcasModeloDto(1L, "Modelo2", Estados.ACTIVO);
        assertNotEquals(dto1, dto2);
    }

    @Test
    void testHashCode_SameObject() {
        MarcasModeloDto dto = new MarcasModeloDto(1L, "Modelo1", Estados.ACTIVO);
        assertEquals(dto.hashCode(), dto.hashCode());
    }

    @Test
    void testHashCode_EqualObjects() {
        MarcasModeloDto dto1 = new MarcasModeloDto(1L, "Modelo1", Estados.ACTIVO);
        MarcasModeloDto dto2 = new MarcasModeloDto(1L, "Modelo1", Estados.ACTIVO);
        assertEquals(dto1.hashCode(), dto2.hashCode());
    }

    @Test
    void testHashCode_DifferentObjects() {
        MarcasModeloDto dto1 = new MarcasModeloDto(1L, "Modelo1", Estados.ACTIVO);
        MarcasModeloDto dto2 = new MarcasModeloDto(2L, "Modelo1", Estados.ACTIVO);
        assertNotEquals(dto1.hashCode(), dto2.hashCode());
    }

    @Test
    void testToString() {
        MarcasModeloDto dto = new MarcasModeloDto(1L, "Modelo1", Estados.ACTIVO);
        String expected = "Modelo1";
        assertEquals(expected, dto.toString());
    }

    @Test
    void testGettersAndSetters() {
        Long id = 1L;
        String nombre = "Modelo1";
        Estados estado = Estados.ACTIVO;

        MarcasModeloDto dto = new MarcasModeloDto();
        dto.setId(id);
        dto.setNombre(nombre);
        dto.setEstado(estado);

        assertEquals(id, dto.getId());
        assertEquals(nombre, dto.getNombre());
        assertEquals(estado, dto.getEstado());
    }
}