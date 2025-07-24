package codigocreativo.uy.servidorapp.dtos;

import codigocreativo.uy.servidorapp.enumerados.Estados;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UbicacionDtoTest {

    @Test
    void testConstructorsAndGettersSetters() {
        UbicacionDto dto = new UbicacionDto();
        dto.setId(1L)
           .setNombre("Sala A")
           .setSector("Cardiología")
           .setPiso(2L)
           .setNumero(10L)
           .setCama(5L);
        dto.setEstado(Estados.ACTIVO);
        InstitucionDto inst = new InstitucionDto();
        dto.setIdInstitucion(inst);

        assertEquals(1L, dto.getId());
        assertEquals("Sala A", dto.getNombre());
        assertEquals("Cardiología", dto.getSector());
        assertEquals(2L, dto.getPiso());
        assertEquals(10L, dto.getNumero());
        assertEquals(5L, dto.getCama());
        assertEquals(Estados.ACTIVO, dto.getEstado());
        assertEquals(inst, dto.getIdInstitucion());
    }

    @Test
    void testAllArgsConstructor() {
        UbicacionDto dto = new UbicacionDto(2L, "Sala B", "Oncología", 3L, 20L, 8L, Estados.INACTIVO);
        assertEquals(2L, dto.getId());
        assertEquals("Sala B", dto.getNombre());
        assertEquals("Oncología", dto.getSector());
        assertEquals(3L, dto.getPiso());
        assertEquals(20L, dto.getNumero());
        assertEquals(8L, dto.getCama());
        assertEquals(Estados.INACTIVO, dto.getEstado());
    }

    @Test
    void testEqualsAndHashCode() {
        UbicacionDto dto1 = new UbicacionDto(1L, "A", "B", 1L, 2L, 3L, Estados.ACTIVO);
        UbicacionDto dto2 = new UbicacionDto(1L, "A", "B", 1L, 2L, 3L, Estados.ACTIVO);
        UbicacionDto dto3 = new UbicacionDto(2L, "C", "D", 2L, 3L, 4L, Estados.INACTIVO);
        assertEquals(dto1, dto2);
        assertEquals(dto1.hashCode(), dto2.hashCode());
        assertNotEquals(dto1, dto3);
    }

    @Test
    void testToString() {
        UbicacionDto dto = new UbicacionDto();
        dto.setNombre("Sala X");
        dto.setSector("Pediatría");
        assertEquals("Sala X - Pediatría", dto.toString());
    }
}
