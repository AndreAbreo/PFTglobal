package codigocreativo.uy.servidorapp.entidades;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


 class FuncionalidadesPerfilesIdTest {

    public FuncionalidadesPerfilesId id1;
    public FuncionalidadesPerfilesId id2;

    @BeforeEach
    public void setUp() {
        id1 = new FuncionalidadesPerfilesId(1L, 2L);
        id2 = new FuncionalidadesPerfilesId(1L, 2L);
    }

    @Test
     void testSetAndGetIdFuncionalidad() {
        Long idFuncionalidad = 1L;
        id1.setIdFuncionalidad(idFuncionalidad);
        assertEquals(idFuncionalidad, id1.getIdFuncionalidad());
    }

    @Test
     void testSetAndGetIdPerfil() {
        Long idPerfil = 2L;
        id1.setIdPerfil(idPerfil);
        assertEquals(idPerfil, id1.getIdPerfil());
    }

    @Test
     void testEqualsAndHashCode() {
        FuncionalidadesPerfilesId id1T = new FuncionalidadesPerfilesId(1L, 2L);
        FuncionalidadesPerfilesId id2T = new FuncionalidadesPerfilesId(1L, 2L);

        assertEquals(id1T, id2T);  // Test equality
        assertEquals(id1T.hashCode(), id2T.hashCode());  // Test hashcode

        FuncionalidadesPerfilesId id3 = new FuncionalidadesPerfilesId(2L, 3L);
        assertNotEquals(id1T, id3);  // Test inequality with different IDs
        assertNotEquals(id1T.hashCode(), id3.hashCode());  // Test hashcode inequality
    }

    @Test
    void testDefaultConstructor() {
        FuncionalidadesPerfilesId id = new FuncionalidadesPerfilesId();
        assertNull(id.getIdFuncionalidad());
        assertNull(id.getIdPerfil());
    }

    @Test
    void testEqualsWithNull() {
        assertNotEquals(id1, null);
    }

    @Test
    void testEqualsWithOtherClass() {
        assertNotEquals(id1, "some string");
    }
}
