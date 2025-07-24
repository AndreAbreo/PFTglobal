package codigocreativo.uy.servidorapp.dtos;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UsuariosTelefonoDtoTest {

    @Test
    void testEquals_SameObject() {
        UsuariosTelefonoDto dto = new UsuariosTelefonoDto(1L, "123456789", new UsuarioDto());
        assertEquals(dto, dto);
    }

    @Test
    void testEquals_NullObject() {
        UsuariosTelefonoDto dto = new UsuariosTelefonoDto(1L, "123456789", new UsuarioDto());
        assertNotEquals(null, dto);
    }

    @Test
    void testEquals_DifferentClass() {
        UsuariosTelefonoDto dto = new UsuariosTelefonoDto(1L, "123456789", new UsuarioDto());
        assertNotEquals("some string", dto);
    }

    @Test
    void testEquals_DifferentId() {
        UsuariosTelefonoDto dto1 = new UsuariosTelefonoDto(1L, "123456789", new UsuarioDto());
        UsuariosTelefonoDto dto2 = new UsuariosTelefonoDto(2L, "123456789", new UsuarioDto());
        assertNotEquals(dto1, dto2);
    }

    @Test
    void testEquals_DifferentNumero() {
        UsuariosTelefonoDto dto1 = new UsuariosTelefonoDto(1L, "123456789", new UsuarioDto());
        UsuariosTelefonoDto dto2 = new UsuariosTelefonoDto(1L, "987654321", new UsuarioDto());
        assertNotEquals(dto1, dto2);
    }

    @Test
    void testHashCode_SameObject() {
        UsuariosTelefonoDto dto = new UsuariosTelefonoDto(1L, "123456789", new UsuarioDto());
        assertEquals(dto.hashCode(), dto.hashCode());
    }

    @Test
    void testHashCode_EqualObjects() {
        UsuariosTelefonoDto dto1 = new UsuariosTelefonoDto(1L, "123456789", new UsuarioDto());
        UsuariosTelefonoDto dto2 = new UsuariosTelefonoDto(1L, "123456789", new UsuarioDto());
        assertEquals(dto1.hashCode(), dto2.hashCode());
    }

    @Test
    void testHashCode_DifferentObjects() {
        UsuariosTelefonoDto dto1 = new UsuariosTelefonoDto(1L, "123456789", new UsuarioDto());
        UsuariosTelefonoDto dto2 = new UsuariosTelefonoDto(2L, "123456789", new UsuarioDto());
        assertNotEquals(dto1.hashCode(), dto2.hashCode());
    }

    @Test
    void testToString() {
        UsuariosTelefonoDto dto = new UsuariosTelefonoDto(1L, "123456789", new UsuarioDto());
        String expected = "UsuariosTelefonoDto(id = 1, numero = 123456789)";
        assertEquals(expected, dto.toString());
    }

    @Test
    void testGettersAndSetters() {
        UsuarioDto usuario = new UsuarioDto();
        String numero = "123456789";
        Long id = 1L;

        UsuariosTelefonoDto dto = new UsuariosTelefonoDto();
        dto.setId(id);
        dto.setNumero(numero);
        dto.setIdUsuario(usuario);

        assertEquals(id, dto.getId());
        assertEquals(numero, dto.getNumero());
        assertEquals(usuario, dto.getIdUsuario());
    }
}