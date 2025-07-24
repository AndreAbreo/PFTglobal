package codigocreativo.uy.servidorapp.dtos;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;

class BajaEquipoDtoTest {

    @Test
    void testConstructor() {
        BajaEquipoDto bajaEquipoDto = new BajaEquipoDto(1L, "Razon", LocalDate.of(2023, 1, 1), new UsuarioDto(), new EquipoDto(), "ACTIVO", "Comentarios");

        assertEquals(1L, bajaEquipoDto.getId());
        assertEquals("Razon", bajaEquipoDto.getRazon());
        assertEquals(LocalDate.of(2023, 1, 1), bajaEquipoDto.getFecha());
        assertNotNull(bajaEquipoDto.getIdUsuario());
        assertNotNull(bajaEquipoDto.getIdEquipo());
        assertEquals("ACTIVO", bajaEquipoDto.getEstado());
        assertEquals("Comentarios", bajaEquipoDto.getComentarios());
    }

    @Test
    void testSetters() {
        BajaEquipoDto bajaEquipoDto = new BajaEquipoDto();
        bajaEquipoDto.setId(1L);
        bajaEquipoDto.setRazon("Razon");
        bajaEquipoDto.setFecha(LocalDate.of(2023, 1, 1));
        bajaEquipoDto.setIdUsuario(new UsuarioDto());
        bajaEquipoDto.setIdEquipo(new EquipoDto());
        bajaEquipoDto.setEstado("ACTIVO");
        bajaEquipoDto.setComentarios("Comentarios");

        assertEquals(1L, bajaEquipoDto.getId());
        assertEquals("Razon", bajaEquipoDto.getRazon());
        assertEquals(LocalDate.of(2023, 1, 1), bajaEquipoDto.getFecha());
        assertNotNull(bajaEquipoDto.getIdUsuario());
        assertNotNull(bajaEquipoDto.getIdEquipo());
        assertEquals("ACTIVO", bajaEquipoDto.getEstado());
        assertEquals("Comentarios", bajaEquipoDto.getComentarios());
    }

    @Test
    void testEqualsAndHashCode() {
        BajaEquipoDto bajaEquipoDto1 = new BajaEquipoDto(1L, "Razon", LocalDate.of(2023, 1, 1), new UsuarioDto(), new EquipoDto(), "ACTIVO", "Comentarios");
        BajaEquipoDto bajaEquipoDto2 = new BajaEquipoDto(1L, "Razon", LocalDate.of(2023, 1, 1), new UsuarioDto(), new EquipoDto(), "ACTIVO", "Comentarios");

        assertEquals(bajaEquipoDto1, bajaEquipoDto2);
        assertEquals(bajaEquipoDto1.hashCode(), bajaEquipoDto2.hashCode());
    }

    @Test
    void testToString() {
        BajaEquipoDto bajaEquipoDto = new BajaEquipoDto();
        bajaEquipoDto.setId(1L);
        bajaEquipoDto.setRazon("Razon");
        bajaEquipoDto.setFecha(LocalDate.of(2023, 1, 1));
        bajaEquipoDto.setEstado("ACTIVO");
        bajaEquipoDto.setComentarios("Comentarios");

        String result = bajaEquipoDto.toString();
        assertNotNull(result);
        assertTrue(result.contains("BajaEquipoDto"));
        assertTrue(result.contains("id=1"));
        assertTrue(result.contains("razon=Razon"));
    }
}
