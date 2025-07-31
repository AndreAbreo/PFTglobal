package codigocreativo.uy.servidorapp.entidades;

import static org.junit.jupiter.api.Assertions.*;

import codigocreativo.uy.servidorapp.enumerados.Estados;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

class UsuarioTest {

    private Usuario usuario;
    private Institucion institucion;
    private Perfil perfil;
    private Set<UsuariosTelefono> usuariosTelefonos;

    @BeforeEach
    void setUp() {
        usuario = new Usuario();
        institucion = new Institucion(); // Suponiendo que tienes una clase Institucion
        perfil = new Perfil();           // Suponiendo que tienes una clase Perfil
        usuariosTelefonos = new HashSet<>();
    }

    @Test
    void testGetAndSetId() {
        Long id = 1L;
        usuario.setId(id);
        assertEquals(id, usuario.getId());
    }

    @Test
    void testGetAndSetCedula() {
        String cedula = "12345678";
        usuario.setCedula(cedula);
        assertEquals(cedula, usuario.getCedula());
    }

    @Test
    void testGetAndSetIdInstitucion() {
        usuario.setIdInstitucion(institucion);
        assertEquals(institucion, usuario.getIdInstitucion());
    }

    @Test
    void testGetAndSetEmail() {
        String email = "test@example.com";
        usuario.setEmail(email);
        assertEquals(email, usuario.getEmail());
    }

    @Test
    void testGetAndSetContrasenia() {
        String contrasenia = "password";
        usuario.setContrasenia(contrasenia);
        assertEquals(contrasenia, usuario.getContrasenia());
    }

    @Test
    void testGetAndSetFechaNacimiento() {
        LocalDate fechaNacimiento = LocalDate.of(1990, 1, 1);
        usuario.setFechaNacimiento(fechaNacimiento);
        assertEquals(fechaNacimiento, usuario.getFechaNacimiento());
    }

    @Test
    void testGetAndSetEstado() {
        Estados estado = Estados.ACTIVO; // Suponiendo que tienes un enum Estados con valor ACTIVO
        usuario.setEstado(estado);
        assertEquals(estado, usuario.getEstado());
    }

    @Test
    void testGetAndSetNombre() {
        String nombre = "Juan";
        usuario.setNombre(nombre);
        assertEquals(nombre, usuario.getNombre());
    }

    @Test
    void testGetAndSetApellido() {
        String apellido = "Pérez";
        usuario.setApellido(apellido);
        assertEquals(apellido, usuario.getApellido());
    }

    @Test
    void testGetAndSetNombreUsuario() {
        String nombreUsuario = "juanp";
        usuario.setNombreUsuario(nombreUsuario);
        assertEquals(nombreUsuario, usuario.getNombreUsuario());
    }

    @Test
    void testGetAndSetIdPerfil() {
        usuario.setIdPerfil(perfil);
        assertEquals(perfil, usuario.getIdPerfil());
    }

    @Test
    void testGetAndSetUsuariosTelefonos() {
        usuario.setUsuariosTelefonos(usuariosTelefonos);
        assertEquals(usuariosTelefonos, usuario.getUsuariosTelefonos());
    }

    @Test
    void testToString() {
        usuario.setId(1L);
        usuario.setNombre("Juan");
        usuario.setApellido("Pérez");
        usuario.setEmail("juan.perez@example.com");

        institucion.setNombre("InstitucionEjemplo");
        perfil.setNombrePerfil("PerfilEjemplo");

        usuario.setIdInstitucion(institucion);
        usuario.setIdPerfil(perfil);

        String expected = "1 Juan Pérez - juan.perez@example.com - PerfilEjemplo - InstitucionEjemplo";
        assertEquals(expected, usuario.toString());
    }
}
