package codigocreativo.uy.servidorapp.excepciones;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class UsuarioNoEncontradoExceptionTest {
    @Test
    void testConstructorAndMessage() {
        String msg = "Usuario no encontrado";
        UsuarioNoEncontradoException ex = new UsuarioNoEncontradoException(msg);
        assertEquals(msg, ex.getMessage());
    }

    @Test
    void testIsRuntimeException() {
        UsuarioNoEncontradoException ex = new UsuarioNoEncontradoException("msg");
        assertTrue(ex instanceof RuntimeException);
    }
} 