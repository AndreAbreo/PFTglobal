package codigocreativo.uy.servidorapp.entidades;

import codigocreativo.uy.servidorapp.enumerados.Estados;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.mockito.Mockito;
import java.util.List;

 class FuncionalidadTest {

    private Funcionalidad funcionalidad;
    private Estados estado;
    private List<FuncionalidadesPerfiles> funcionalidadesPerfiles;

    @SuppressWarnings("unchecked")
    @BeforeEach
    public void setUp() {
        estado = Estados.ACTIVO;  // Asumimos un estado como ejemplo
        funcionalidadesPerfiles = Mockito.mock(List.class);  // Mocked List of FuncionalidadesPerfiles
        funcionalidad = new Funcionalidad();
        funcionalidad.setId(1L);
        funcionalidad.setNombreFuncionalidad("Funcionalidad 1");
        funcionalidad.setRuta("/ruta/funcionalidad");
        funcionalidad.setEstado(estado);
        funcionalidad.setFuncionalidadesPerfiles(funcionalidadesPerfiles);
    }

    @Test
     void testGetAndSetId() {
        funcionalidad.setId(2L);
        assertEquals(2L, funcionalidad.getId());
    }

    @Test
     void testGetAndSetNombreFuncionalidad() {
        funcionalidad.setNombreFuncionalidad("Funcionalidad 2");
        assertEquals("Funcionalidad 2", funcionalidad.getNombreFuncionalidad());
    }

    @Test
     void testGetAndSetRuta() {
        funcionalidad.setRuta("/nueva/ruta");
        assertEquals("/nueva/ruta", funcionalidad.getRuta());
    }

    @Test
     void testGetAndSetEstado() {
        funcionalidad.setEstado(Estados.INACTIVO);
        assertEquals(Estados.INACTIVO, funcionalidad.getEstado());
    }

    @SuppressWarnings("unchecked")
    @Test
     void testGetAndSetFuncionalidadesPerfiles() {
        List<FuncionalidadesPerfiles> newFuncionalidades = Mockito.mock(List.class);
        funcionalidad.setFuncionalidadesPerfiles(newFuncionalidades);
        assertEquals(newFuncionalidades, funcionalidad.getFuncionalidadesPerfiles());
    }

    @Test
     void testConstructor() {
        assertNotNull(funcionalidad.getId());
        assertNotNull(funcionalidad.getNombreFuncionalidad());
        assertNotNull(funcionalidad.getRuta());
        assertNotNull(funcionalidad.getEstado());
        assertNotNull(funcionalidad.getFuncionalidadesPerfiles());
    }
}
