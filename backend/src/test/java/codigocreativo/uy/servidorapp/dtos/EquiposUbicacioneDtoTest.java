package codigocreativo.uy.servidorapp.dtos;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class EquiposUbicacioneDtoTest {

    @Test
    void testEquals_SameObject() {
        EquiposUbicacioneDto dto = new EquiposUbicacioneDto(1L, new EquipoDto(), new UbicacionDto(), LocalDate.now(), new UsuarioDto(), "Observaciones");
        assertEquals(dto, dto);
    }

    @Test
    void testEquals_NullObject() {
        EquiposUbicacioneDto dto = new EquiposUbicacioneDto(1L, new EquipoDto(), new UbicacionDto(), LocalDate.now(), new UsuarioDto(), "Observaciones");
        assertNotEquals(null, dto);
    }

    @Test
    void testEquals_DifferentClass() {
        EquiposUbicacioneDto dto = new EquiposUbicacioneDto(1L, new EquipoDto(), new UbicacionDto(), LocalDate.now(), new UsuarioDto(), "Observaciones");
        assertNotEquals("some string", dto);
    }

    @Test
    void testEquals_DifferentId() {
        EquiposUbicacioneDto dto1 = new EquiposUbicacioneDto(1L, new EquipoDto(), new UbicacionDto(), LocalDate.now(), new UsuarioDto(), "Observaciones");
        EquiposUbicacioneDto dto2 = new EquiposUbicacioneDto(2L, new EquipoDto(), new UbicacionDto(), LocalDate.now(), new UsuarioDto(), "Observaciones");
        assertNotEquals(dto1, dto2);
    }

    @Test
    void testEquals_DifferentFecha() {
        EquiposUbicacioneDto dto1 = new EquiposUbicacioneDto(1L, new EquipoDto(), new UbicacionDto(), LocalDate.now(), new UsuarioDto(), "Observaciones");
        EquiposUbicacioneDto dto2 = new EquiposUbicacioneDto(1L, new EquipoDto(), new UbicacionDto(), LocalDate.now().plusDays(1), new UsuarioDto(), "Observaciones");
        assertNotEquals(dto1, dto2);
    }

    @Test
    void testHashCode_SameObject() {
        EquiposUbicacioneDto dto = new EquiposUbicacioneDto(1L, new EquipoDto(), new UbicacionDto(), LocalDate.now(), new UsuarioDto(), "Observaciones");
        assertEquals(dto.hashCode(), dto.hashCode());
    }

    @Test
    void testHashCode_EqualObjects() {
        EquiposUbicacioneDto dto1 = new EquiposUbicacioneDto(1L, new EquipoDto(), new UbicacionDto(), LocalDate.now(), new UsuarioDto(), "Observaciones");
        EquiposUbicacioneDto dto2 = new EquiposUbicacioneDto(1L, new EquipoDto(), new UbicacionDto(), LocalDate.now(), new UsuarioDto(), "Observaciones");
        assertEquals(dto1.hashCode(), dto2.hashCode());
    }

    @Test
    void testHashCode_DifferentObjects() {
        EquiposUbicacioneDto dto1 = new EquiposUbicacioneDto(1L, new EquipoDto(), new UbicacionDto(), LocalDate.now(), new UsuarioDto(), "Observaciones");
        EquiposUbicacioneDto dto2 = new EquiposUbicacioneDto(2L, new EquipoDto(), new UbicacionDto(), LocalDate.now(), new UsuarioDto(), "Observaciones");
        assertNotEquals(dto1.hashCode(), dto2.hashCode());
    }

    @Test
    void testToString() {
        EquiposUbicacioneDto dto = new EquiposUbicacioneDto(1L, new EquipoDto(), new UbicacionDto(), LocalDate.now(), new UsuarioDto(), "Observaciones");
        String expected = "EquiposUbicacioneDto(id = 1, fecha = " + dto.getFecha() + ")";
        assertEquals(expected, dto.toString());
    }

    @Test
    void testGettersAndSetters() {
        EquipoDto equipo = new EquipoDto();
        UbicacionDto ubicacion = new UbicacionDto();
        UsuarioDto usuario = new UsuarioDto();
        String observaciones = "Observaciones";
        Long id = 1L;
        LocalDate fecha = LocalDate.now();

        EquiposUbicacioneDto dto = new EquiposUbicacioneDto();
        dto.setIdEquipo(equipo);
        dto.setIdUbicacion(ubicacion);
        dto.setUsuario(usuario);
        dto.setObservaciones(observaciones);
        dto.setId(id);
        dto.setFecha(fecha);

        assertEquals(equipo, dto.getIdEquipo());
        assertEquals(ubicacion, dto.getIdUbicacion());
        assertEquals(usuario, dto.getUsuario());
        assertEquals(observaciones, dto.getObservaciones());
        assertEquals(id, dto.getId());
        assertEquals(fecha, dto.getFecha());
    }
}