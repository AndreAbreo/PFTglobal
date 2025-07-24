package codigocreativo.uy.servidorapp.entidades;

import codigocreativo.uy.servidorapp.enumerados.Estados;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import java.util.List;

 class PerfilTest {

    private Perfil perfil;
    private List<FuncionalidadesPerfiles> funcionalidadesPerfiles;

    @BeforeEach
    public void setUp() {
        perfil = new Perfil();
        funcionalidadesPerfiles = new ArrayList<>();
    }

    @Test
     void testSetAndGetId() {
        Long id = 1L;
        perfil.setId(id);
        assertEquals(id, perfil.getId());
    }

    @Test
     void testSetAndGetNombrePerfil() {
        String nombrePerfil = "Admin";
        perfil.setNombrePerfil(nombrePerfil);
        assertEquals(nombrePerfil, perfil.getNombrePerfil());
    }

    @Test
     void testSetAndGetEstado() {
        Estados estado = Estados.ACTIVO;
        perfil.setEstado(estado);
        assertEquals(estado, perfil.getEstado());
    }

    @Test
     void testSetAndGetFuncionalidadesPerfiles() {
        FuncionalidadesPerfiles funcionalidad = new FuncionalidadesPerfiles();
        funcionalidadesPerfiles.add(funcionalidad);

        perfil.setFuncionalidadesPerfiles(funcionalidadesPerfiles);
        assertEquals(funcionalidadesPerfiles, perfil.getFuncionalidadesPerfiles());
        assertTrue(perfil.getFuncionalidadesPerfiles().contains(funcionalidad));
    }

    @Test
     void testAddFuncionalidadPerfil() {
        FuncionalidadesPerfiles funcionalidad = new FuncionalidadesPerfiles();
        funcionalidadesPerfiles.add(funcionalidad);
        perfil.setFuncionalidadesPerfiles(funcionalidadesPerfiles);

        assertEquals(1, perfil.getFuncionalidadesPerfiles().size());
        assertEquals(funcionalidad, perfil.getFuncionalidadesPerfiles().get(0));
    }

    @Test
     void testRemoveFuncionalidadPerfil() {
        FuncionalidadesPerfiles funcionalidad = new FuncionalidadesPerfiles();
        funcionalidadesPerfiles.add(funcionalidad);
        perfil.setFuncionalidadesPerfiles(funcionalidadesPerfiles);

        perfil.getFuncionalidadesPerfiles().remove(funcionalidad);

        assertTrue(perfil.getFuncionalidadesPerfiles().isEmpty());
    }
}
