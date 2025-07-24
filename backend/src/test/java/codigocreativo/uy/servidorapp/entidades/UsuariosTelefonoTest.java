package codigocreativo.uy.servidorapp.entidades;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class UsuariosTelefonoTest {

    private UsuariosTelefono usuariosTelefono;
    private Usuario usuario;

    @BeforeEach
    void setUp() {
        usuariosTelefono = new UsuariosTelefono();
        usuario = new Usuario(); // Suponiendo que tienes una clase Usuario
    }

    @Test
    void testGetAndSetNumero() {
        String numero = "123456789";
        usuariosTelefono.setNumero(numero);
        assertEquals(numero, usuariosTelefono.getNumero(), "El número de teléfono debería coincidir");
    }

    @Test
    void testGetAndSetId() {
        Long id = 1L;
        usuariosTelefono.setId(id);
        assertEquals(id, usuariosTelefono.getId(), "El ID debería coincidir");
    }

    @Test
    void testGetAndSetIdUsuario() {
        usuariosTelefono.setIdUsuario(usuario);
        assertEquals(usuario, usuariosTelefono.getIdUsuario(), "El usuario debería coincidir");
    }
}
