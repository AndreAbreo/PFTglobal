package codigocreativo.uy.servidorapp.dtos;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class AuditoriaDtoTest {

    @Test
    void testEquals_SameObject() {
        AuditoriaDto dto = new AuditoriaDto(1L, LocalDate.now(), new UsuarioDto(), new OperacionDto());
        assertEquals(dto, dto);
    }

    @Test
    void testEquals_NullObject() {
        AuditoriaDto dto = new AuditoriaDto(1L, LocalDate.now(), new UsuarioDto(), new OperacionDto());
        assertNotEquals(null, dto);
    }

    @Test
    void testEquals_DifferentClass() {
        AuditoriaDto dto = new AuditoriaDto(1L, LocalDate.now(), new UsuarioDto(), new OperacionDto());
        assertNotEquals("some string", dto);
    }

    @Test
    void testEquals_DifferentId() {
        AuditoriaDto dto1 = new AuditoriaDto(1L, LocalDate.now(), new UsuarioDto(), new OperacionDto());
        AuditoriaDto dto2 = new AuditoriaDto(2L, LocalDate.now(), new UsuarioDto(), new OperacionDto());
        assertNotEquals(dto1, dto2);
    }

    @Test
    void testEquals_DifferentFechaHora() {
        AuditoriaDto dto1 = new AuditoriaDto(1L, LocalDate.now(), new UsuarioDto(), new OperacionDto());
        AuditoriaDto dto2 = new AuditoriaDto(1L, LocalDate.now().plusDays(1), new UsuarioDto(), new OperacionDto());
        assertNotEquals(dto1, dto2);
    }

    @Test
    void testHashCode_SameObject() {
        AuditoriaDto dto = new AuditoriaDto(1L, LocalDate.now(), new UsuarioDto(), new OperacionDto());
        assertEquals(dto.hashCode(), dto.hashCode());
    }

    @Test
    void testHashCode_EqualObjects() {
        AuditoriaDto dto1 = new AuditoriaDto(1L, LocalDate.now(), new UsuarioDto(), new OperacionDto());
        AuditoriaDto dto2 = new AuditoriaDto(1L, LocalDate.now(), new UsuarioDto(), new OperacionDto());
        assertEquals(dto1.hashCode(), dto2.hashCode());
    }

    @Test
    void testHashCode_DifferentObjects() {
        AuditoriaDto dto1 = new AuditoriaDto(1L, LocalDate.now(), new UsuarioDto(), new OperacionDto());
        AuditoriaDto dto2 = new AuditoriaDto(2L, LocalDate.now(), new UsuarioDto(), new OperacionDto());
        assertNotEquals(dto1.hashCode(), dto2.hashCode());
    }

    @Test
    void testToString() {
        AuditoriaDto dto = new AuditoriaDto(1L, LocalDate.now(), new UsuarioDto(), new OperacionDto());
        String expected = "AuditoriaDto(id = 1, fechaHora = " + dto.getFechaHora() + ")";
        assertEquals(expected, dto.toString());
    }

    @Test
    void testGettersAndSetters() {
        UsuarioDto usuario = new UsuarioDto();
        OperacionDto operacion = new OperacionDto();
        Long id = 1L;
        LocalDate fechaHora = LocalDate.now();

        AuditoriaDto dto = new AuditoriaDto();
        dto.setId(id);
        dto.setFechaHora(fechaHora);
        dto.setIdUsuario(usuario);
        dto.setIdOperacion(operacion);

        assertEquals(id, dto.getId());
        assertEquals(fechaHora, dto.getFechaHora());
        assertEquals(usuario, dto.getIdUsuario());
        assertEquals(operacion, dto.getIdOperacion());
    }
}