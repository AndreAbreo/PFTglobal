package codigocreativo.uy.servidorapp.entidades;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDateTime;

 class IntervencionTest {

    private Intervencion intervencion;
    private Usuario usuario;
    private TiposIntervencione tipoIntervencion;
    private Equipo equipo;

    @BeforeEach
    public void setUp() {
        intervencion = new Intervencion();
        usuario = new Usuario();  // Inicialización del usuario
        tipoIntervencion = new TiposIntervencione();  // Inicialización del tipo de intervención
        equipo = new Equipo();  // Inicialización del equipo
    }

    @Test
     void testSetAndGetId() {
        Long id = 1L;
        intervencion.setId(id);
        assertEquals(id, intervencion.getId());
    }

    @Test
     void testSetAndGetIdUsuario() {
        intervencion.setIdUsuario(usuario);
        assertEquals(usuario, intervencion.getIdUsuario());
    }

    @Test
     void testSetAndGetIdTipo() {
        intervencion.setIdTipo(tipoIntervencion);
        assertEquals(tipoIntervencion, intervencion.getIdTipo());
    }

    @Test
     void testSetAndGetIdEquipo() {
        intervencion.setIdEquipo(equipo);
        assertEquals(equipo, intervencion.getIdEquipo());
    }

    @Test
     void testSetAndGetMotivo() {
        String motivo = "Reparación del equipo";
        intervencion.setMotivo(motivo);
        assertEquals(motivo, intervencion.getMotivo());
    }

    @Test
     void testSetAndGetFechaHora() {
        LocalDateTime fechaHora = LocalDateTime.now();
        intervencion.setFechaHora(fechaHora);
        assertEquals(fechaHora, intervencion.getFechaHora());
    }

    @Test
     void testSetAndGetComentarios() {
        String comentarios = "Se realizó una revisión general";
        intervencion.setComentarios(comentarios);
        assertEquals(comentarios, intervencion.getComentarios());
    }

    @Test
     void testConstructorAndSetters() {
        Long id = 1L;
        String motivo = "Mantenimiento preventivo";
        LocalDateTime fechaHora = LocalDateTime.now();
        String comentarios = "Se verificó el funcionamiento del equipo.";

        intervencion.setId(id);
        intervencion.setMotivo(motivo);
        intervencion.setFechaHora(fechaHora);
        intervencion.setComentarios(comentarios);

        assertEquals(id, intervencion.getId());
        assertEquals(motivo, intervencion.getMotivo());
        assertEquals(fechaHora, intervencion.getFechaHora());
        assertEquals(comentarios, intervencion.getComentarios());
    }
}
