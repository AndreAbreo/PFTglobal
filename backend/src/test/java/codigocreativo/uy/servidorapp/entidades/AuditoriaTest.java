package codigocreativo.uy.servidorapp.entidades;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.mockito.Mockito;
import java.time.LocalDate;

 class AuditoriaTest {

    private Auditoria auditoria;
    private Usuario usuario;
    private Operacion operacion;
    private LocalDate fechaHora;

    @BeforeEach
    void setUp() {
        usuario = Mockito.mock(Usuario.class);
        operacion = Mockito.mock(Operacion.class);
        fechaHora = LocalDate.now();

        auditoria = new Auditoria();
        auditoria.setId(1L);
        auditoria.setFechaHora(fechaHora);
        auditoria.setIdUsuario(usuario);
        auditoria.setIdOperacion(operacion);
    }

    @Test
     void testGetAndSetId() {
        auditoria.setId(2L);
        assertEquals(2L, auditoria.getId());
    }

    @Test
    void testGetAndSetFechaHora() {
        LocalDate newFechaHora = LocalDate.of(2024, 12, 25);
        auditoria.setFechaHora(newFechaHora);
        assertEquals(newFechaHora, auditoria.getFechaHora());
    }

    @Test
     void testGetAndSetIdUsuario() {
        Usuario newUsuario = Mockito.mock(Usuario.class);
        auditoria.setIdUsuario(newUsuario);
        assertEquals(newUsuario, auditoria.getIdUsuario());
    }

    @Test
     void testGetAndSetIdOperacion() {
        Operacion newOperacion = Mockito.mock(Operacion.class);
        auditoria.setIdOperacion(newOperacion);
        assertEquals(newOperacion, auditoria.getIdOperacion());
    }

    @Test
     void testConstructor() {
        assertNotNull(auditoria.getId());
        assertNotNull(auditoria.getFechaHora());
        assertNotNull(auditoria.getIdUsuario());
        assertNotNull(auditoria.getIdOperacion());
    }
}
