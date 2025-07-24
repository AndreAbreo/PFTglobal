package codigocreativo.uy.servidorapp.dtos;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class IntervencionDtoTest {

    @Test
    void testConstructorAndGetters() {
        Long id = 1L;
        String motivo = "Test Motivo";
        LocalDateTime fechaHora = LocalDateTime.now();
        String comentarios = "Test Comentarios";
        UsuarioDto usuario = new UsuarioDto();
        TiposIntervencioneDto tipo = new TiposIntervencioneDto();
        EquipoDto equipo = new EquipoDto();

        IntervencionDto dto = new IntervencionDto(id, motivo, fechaHora, comentarios, usuario, tipo, equipo);

        assertEquals(id, dto.getId());
        assertEquals(motivo, dto.getMotivo());
        assertEquals(fechaHora, dto.getFechaHora());
        assertEquals(comentarios, dto.getComentarios());
        assertEquals(usuario, dto.getIdUsuario());
        assertEquals(tipo, dto.getIdTipo());
        assertEquals(equipo, dto.getIdEquipo());
    }

    @Test
    void testSetters() {
        IntervencionDto dto = new IntervencionDto();
        Long id = 1L;
        String motivo = "Test Motivo";
        LocalDateTime fechaHora = LocalDateTime.now();
        String comentarios = "Test Comentarios";
        UsuarioDto usuario = new UsuarioDto();
        TiposIntervencioneDto tipo = new TiposIntervencioneDto();
        EquipoDto equipo = new EquipoDto();

        dto.setId(id);
        dto.setMotivo(motivo);
        dto.setFechaHora(fechaHora);
        dto.setComentarios(comentarios);
        dto.setIdUsuario(usuario);
        dto.setIdTipo(tipo);
        dto.setIdEquipo(equipo);

        assertEquals(id, dto.getId());
        assertEquals(motivo, dto.getMotivo());
        assertEquals(fechaHora, dto.getFechaHora());
        assertEquals(comentarios, dto.getComentarios());
        assertEquals(usuario, dto.getIdUsuario());
        assertEquals(tipo, dto.getIdTipo());
        assertEquals(equipo, dto.getIdEquipo());
    }



    @Test
    void testHashCode() {
        UsuarioDto usuario = new UsuarioDto();
        TiposIntervencioneDto tipo = new TiposIntervencioneDto();
        EquipoDto equipo = new EquipoDto();


        IntervencionDto dto1 = new IntervencionDto(1L, "Motivo", LocalDate.of(2023, 1, 1).atStartOfDay(), "Comentarios", usuario, tipo, equipo);
        IntervencionDto dto2 = new IntervencionDto(1L, "Motivo", LocalDate.of(2023, 1, 1).atStartOfDay(), "Comentarios", usuario, tipo, equipo);
        assertEquals(dto1.hashCode(), dto2.hashCode());
    }

    @Test
    void testToString() {
        IntervencionDto dto = new IntervencionDto(1L, "Motivo", LocalDateTime.now(), "Comentarios", new UsuarioDto(), new TiposIntervencioneDto(), new EquipoDto());
        assertEquals("Motivo", dto.toString());
    }

    @Test
    void testEquals_SameObject() {
        IntervencionDto dto = new IntervencionDto(1L, "Motivo", LocalDateTime.now(), "Comentarios", new UsuarioDto(), new TiposIntervencioneDto(), new EquipoDto());
        assertEquals(dto, dto);
    }

    @Test
    void testEquals_NullObject() {
        IntervencionDto dto = new IntervencionDto(1L, "Motivo", LocalDateTime.now(), "Comentarios", new UsuarioDto(), new TiposIntervencioneDto(), new EquipoDto());
        assertNotEquals(null, dto);
    }

    @Test
    void testEquals_DifferentClass() {
        IntervencionDto dto = new IntervencionDto(1L, "Motivo", LocalDateTime.now(), "Comentarios", new UsuarioDto(), new TiposIntervencioneDto(), new EquipoDto());
        assertNotEquals("some string", dto);
    }

    @Test
    void testEquals_DifferentId() {
        IntervencionDto dto1 = new IntervencionDto(1L, "Motivo", LocalDateTime.now(), "Comentarios", new UsuarioDto(), new TiposIntervencioneDto(), new EquipoDto());
        IntervencionDto dto2 = new IntervencionDto(2L, "Motivo", LocalDateTime.now(), "Comentarios", new UsuarioDto(), new TiposIntervencioneDto(), new EquipoDto());
        assertNotEquals(dto1, dto2);
    }

    @Test
    void testEquals_DifferentMotivo() {
        IntervencionDto dto1 = new IntervencionDto(1L, "Motivo1", LocalDateTime.now(), "Comentarios", new UsuarioDto(), new TiposIntervencioneDto(), new EquipoDto());
        IntervencionDto dto2 = new IntervencionDto(1L, "Motivo2", LocalDateTime.now(), "Comentarios", new UsuarioDto(), new TiposIntervencioneDto(), new EquipoDto());
        assertNotEquals(dto1, dto2);
    }

    @Test
    void testEquals_DifferentFechaHora() {
        IntervencionDto dto1 = new IntervencionDto(1L, "Motivo", LocalDateTime.now(), "Comentarios", new UsuarioDto(), new TiposIntervencioneDto(), new EquipoDto());
        IntervencionDto dto2 = new IntervencionDto(1L, "Motivo", LocalDateTime.now().plusDays(1), "Comentarios", new UsuarioDto(), new TiposIntervencioneDto(), new EquipoDto());
        assertNotEquals(dto1, dto2);
    }

    @Test
    void testEquals_DifferentComentarios() {
        IntervencionDto dto1 = new IntervencionDto(1L, "Motivo", LocalDateTime.now(), "Comentarios1", new UsuarioDto(), new TiposIntervencioneDto(), new EquipoDto());
        IntervencionDto dto2 = new IntervencionDto(1L, "Motivo", LocalDateTime.now(), "Comentarios2", new UsuarioDto(), new TiposIntervencioneDto(), new EquipoDto());
        assertNotEquals(dto1, dto2);
    }

}