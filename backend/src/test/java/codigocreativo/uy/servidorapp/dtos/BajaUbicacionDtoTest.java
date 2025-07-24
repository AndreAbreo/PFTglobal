package codigocreativo.uy.servidorapp.dtos;

import codigocreativo.uy.servidorapp.enumerados.Estados;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class BajaUbicacionDtoTest {

    @Test
    void testEquals_SameObject() {
        BajaUbicacionDto dto = new BajaUbicacionDto(1L, new UsuarioDto(), new UbicacionDto(), "Razon", "Comentario", LocalDate.now());
        assertEquals(dto, dto);
    }

    @Test
    void testEquals_NullObject() {
        BajaUbicacionDto dto = new BajaUbicacionDto(1L, new UsuarioDto(), new UbicacionDto(), "Razon", "Comentario", LocalDate.now());
        assertNotEquals(null, dto);
    }

    @Test
    void testEquals_DifferentClass() {
        BajaUbicacionDto dto = new BajaUbicacionDto(1L, new UsuarioDto(), new UbicacionDto(), "Razon", "Comentario", LocalDate.now());
        assertNotEquals("some string", dto);
    }

    @Test
    void testEquals_DifferentId() {
        BajaUbicacionDto dto1 = new BajaUbicacionDto(1L, new UsuarioDto(), new UbicacionDto(), "Razon", "Comentario", LocalDate.now());
        BajaUbicacionDto dto2 = new BajaUbicacionDto(2L, new UsuarioDto(), new UbicacionDto(), "Razon", "Comentario", LocalDate.now());
        assertNotEquals(dto1, dto2);
    }

    @Test
    void testEquals_DifferentIdUsuario() {
        UsuarioDto usuario1 = new UsuarioDto();
        usuario1.setId(1L);
        UsuarioDto usuario2 = new UsuarioDto();
        usuario2.setId(2L);
        BajaUbicacionDto dto1 = new BajaUbicacionDto(1L, usuario1, new UbicacionDto(), "Razon", "Comentario", LocalDate.now());
        BajaUbicacionDto dto2 = new BajaUbicacionDto(1L, usuario2, new UbicacionDto(), "Razon", "Comentario", LocalDate.now());
        assertNotEquals(dto1, dto2);
    }

    @Test
    void testEquals_DifferentIdUbicacion() {
        UbicacionDto ubicacion1 = new UbicacionDto(1L, "Nombre1", "Sector1", 1L, 1L, 1L, Estados.ACTIVO);
        UbicacionDto ubicacion2 = new UbicacionDto(2L, "Nombre2", "Sector2", 2L, 2L, 2L, Estados.INACTIVO);
        BajaUbicacionDto dto1 = new BajaUbicacionDto(1L, new UsuarioDto(), ubicacion1, "Razon", "Comentario", LocalDate.now());
        BajaUbicacionDto dto2 = new BajaUbicacionDto(1L, new UsuarioDto(), ubicacion2, "Razon", "Comentario", LocalDate.now());
        assertNotEquals(dto1, dto2);
    }

    @Test
    void testEquals_DifferentRazon() {
        BajaUbicacionDto dto1 = new BajaUbicacionDto(1L, new UsuarioDto(), new UbicacionDto(), "Razon1", "Comentario", LocalDate.now());
        BajaUbicacionDto dto2 = new BajaUbicacionDto(1L, new UsuarioDto(), new UbicacionDto(), "Razon2", "Comentario", LocalDate.now());
        assertNotEquals(dto1, dto2);
    }

    @Test
    void testEquals_DifferentComentario() {
        BajaUbicacionDto dto1 = new BajaUbicacionDto(1L, new UsuarioDto(), new UbicacionDto(), "Razon", "Comentario1", LocalDate.now());
        BajaUbicacionDto dto2 = new BajaUbicacionDto(1L, new UsuarioDto(), new UbicacionDto(), "Razon", "Comentario2", LocalDate.now());
        assertNotEquals(dto1, dto2);
    }

    @Test
    void testEquals_DifferentFecha() {
        BajaUbicacionDto dto1 = new BajaUbicacionDto(1L, new UsuarioDto(), new UbicacionDto(), "Razon", "Comentario", LocalDate.now());
        BajaUbicacionDto dto2 = new BajaUbicacionDto(1L, new UsuarioDto(), new UbicacionDto(), "Razon", "Comentario", LocalDate.now().plusDays(1));
        assertNotEquals(dto1, dto2);
    }

    @Test
    void testHashCode_SameObject() {
        BajaUbicacionDto dto = new BajaUbicacionDto(1L, new UsuarioDto(), new UbicacionDto(), "Razon", "Comentario", LocalDate.now());
        assertEquals(dto.hashCode(), dto.hashCode());
    }

    @Test
    void testHashCode_EqualObjects() {
        BajaUbicacionDto dto1 = new BajaUbicacionDto(1L, new UsuarioDto(), new UbicacionDto(), "Razon", "Comentario", LocalDate.now());
        BajaUbicacionDto dto2 = new BajaUbicacionDto(1L, new UsuarioDto(), new UbicacionDto(), "Razon", "Comentario", LocalDate.now());
        assertEquals(dto1.hashCode(), dto2.hashCode());
    }

    @Test
    void testHashCode_DifferentObjects() {
        BajaUbicacionDto dto1 = new BajaUbicacionDto(1L, new UsuarioDto(), new UbicacionDto(), "Razon", "Comentario", LocalDate.now());
        BajaUbicacionDto dto2 = new BajaUbicacionDto(2L, new UsuarioDto(), new UbicacionDto(), "Razon", "Comentario", LocalDate.now());
        assertNotEquals(dto1.hashCode(), dto2.hashCode());
    }

    @Test
    void testToString() {
        BajaUbicacionDto dto = new BajaUbicacionDto(1L, new UsuarioDto(), new UbicacionDto(), "Razon", "Comentario", LocalDate.now());
        String expected = "BajaUbicacionDto(id = 1, idUsuario = " + dto.getIdUsuario() + ", idUbicacion = " + dto.getIdUbicacion() + ", razon = Razon, comentario = Comentario, fecha = " + dto.getFecha() + ")";
        assertEquals(expected, dto.toString());
    }

    @Test
    void testGettersAndSetters() {
        UsuarioDto usuario = new UsuarioDto();
        UbicacionDto ubicacion = new UbicacionDto();
        String razon = "Razon";
        String comentario = "Comentario";
        Long id = 1L;
        LocalDate fecha = LocalDate.now();

        BajaUbicacionDto dto = new BajaUbicacionDto();
        dto.setId(id);
        dto.setIdUsuario(usuario);
        dto.setIdUbicacion(ubicacion);
        dto.setRazon(razon);
        dto.setComentario(comentario);
        dto.setFecha(fecha);

        assertEquals(id, dto.getId());
        assertEquals(usuario, dto.getIdUsuario());
        assertEquals(ubicacion, dto.getIdUbicacion());
        assertEquals(razon, dto.getRazon());
        assertEquals(comentario, dto.getComentario());
        assertEquals(fecha, dto.getFecha());
    }
}