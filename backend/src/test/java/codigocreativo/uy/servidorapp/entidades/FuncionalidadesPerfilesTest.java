package codigocreativo.uy.servidorapp.entidades;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.mockito.Mockito;

 class FuncionalidadesPerfilesTest {

    private FuncionalidadesPerfiles funcionalidadesPerfiles;
    private FuncionalidadesPerfilesId id;
    private Funcionalidad funcionalidad;
    private Perfil perfil;

    @BeforeEach
    public void setUp() {
        id = new FuncionalidadesPerfilesId(1L, 2L);  // Mocked primary key
        funcionalidad = Mockito.mock(Funcionalidad.class);  // Mocked Funcionalidad
        perfil = Mockito.mock(Perfil.class);  // Mocked Perfil
        funcionalidadesPerfiles = new FuncionalidadesPerfiles(id, funcionalidad, perfil);
    }

    @Test
     void testGetAndSetId() {
        FuncionalidadesPerfilesId newId = new FuncionalidadesPerfilesId(3L, 4L);
        funcionalidadesPerfiles.setId(newId);
        assertEquals(newId, funcionalidadesPerfiles.getId());
    }

    @Test
     void testGetAndSetFuncionalidad() {
        Funcionalidad newFuncionalidad = Mockito.mock(Funcionalidad.class);
        funcionalidadesPerfiles.setFuncionalidad(newFuncionalidad);
        assertEquals(newFuncionalidad, funcionalidadesPerfiles.getFuncionalidad());
    }

    @Test
     void testGetAndSetPerfil() {
        Perfil newPerfil = Mockito.mock(Perfil.class);
        funcionalidadesPerfiles.setPerfil(newPerfil);
        assertEquals(newPerfil, funcionalidadesPerfiles.getPerfil());
    }

    @Test
     void testConstructor() {
        assertNotNull(funcionalidadesPerfiles.getId());
        assertNotNull(funcionalidadesPerfiles.getFuncionalidad());
        assertNotNull(funcionalidadesPerfiles.getPerfil());
    }
}
