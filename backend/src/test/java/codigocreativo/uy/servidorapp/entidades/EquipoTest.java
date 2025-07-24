package codigocreativo.uy.servidorapp.entidades;

import codigocreativo.uy.servidorapp.enumerados.Estados;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.mockito.Mockito;
import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;

 class EquipoTest {

    private Equipo equipo;
    private TiposEquipo tiposEquipo;
    private ProveedoresEquipo proveedoresEquipo;
    private codigocreativo.uy.servidorapp.entidades.Pais pais;
    private ModelosEquipo modelosEquipo;
    private Ubicacion ubicacion;
    private LocalDate fechaAdquisicion;

    @BeforeEach
    public void setUp() {
        tiposEquipo = Mockito.mock(TiposEquipo.class);
        proveedoresEquipo = Mockito.mock(ProveedoresEquipo.class);
        pais = Mockito.mock(codigocreativo.uy.servidorapp.entidades.Pais.class);
        modelosEquipo = Mockito.mock(ModelosEquipo.class);
        ubicacion = Mockito.mock(Ubicacion.class);
        fechaAdquisicion = LocalDate.now();

        equipo = new Equipo();
        equipo.setId(1L);
        equipo.setIdInterno("ID123");
        equipo.setNroSerie("SN12345");
        equipo.setNombre("Equipo A");
        equipo.setIdTipo(tiposEquipo);
        equipo.setIdProveedor(proveedoresEquipo);
        equipo.setIdPais(pais);
        equipo.setIdModelo(modelosEquipo);
        equipo.setImagen("image.jpg");
        equipo.setFechaAdquisicion(fechaAdquisicion);
        equipo.setEstado(Estados.ACTIVO);
        equipo.setIdUbicacion(ubicacion);
    }

    @Test
    void testGetAndSetId() {
        equipo.setId(2L);
        assertEquals(2L, equipo.getId());
    }

    @Test
     void testGetAndSetIdInterno() {
        equipo.setIdInterno("ID456");
        assertEquals("ID456", equipo.getIdInterno());
    }

    @Test
     void testGetAndSetNroSerie() {
        equipo.setNroSerie("SN67890");
        assertEquals("SN67890", equipo.getNroSerie());
    }

    @Test
     void testGetAndSetNombre() {
        equipo.setNombre("Equipo B");
        assertEquals("Equipo B", equipo.getNombre());
    }

    @Test
     void testGetAndSetIdTipo() {
        TiposEquipo newTipo = Mockito.mock(TiposEquipo.class);
        equipo.setIdTipo(newTipo);
        assertEquals(newTipo, equipo.getIdTipo());
    }

    @Test
     void testGetAndSetIdProveedor() {
        ProveedoresEquipo newProveedor = Mockito.mock(ProveedoresEquipo.class);
        equipo.setIdProveedor(newProveedor);
        assertEquals(newProveedor, equipo.getIdProveedor());
    }

    @Test
     void testGetAndSetIdPais() {
        codigocreativo.uy.servidorapp.entidades.Pais newPais = Mockito.mock(codigocreativo.uy.servidorapp.entidades.Pais.class);
        equipo.setIdPais(newPais);
        assertEquals(newPais, equipo.getIdPais());
    }

    @Test
     void testGetAndSetIdModelo() {
        ModelosEquipo newModelo = Mockito.mock(ModelosEquipo.class);
        equipo.setIdModelo(newModelo);
        assertEquals(newModelo, equipo.getIdModelo());
    }

    @Test
     void testGetAndSetImagen() {
        equipo.setImagen("newImage.jpg");
        assertEquals("newImage.jpg", equipo.getImagen());
    }

    @Test
     void testGetAndSetFechaAdquisicion() {
        LocalDate newFecha = LocalDate.of(2024, 12, 25);
        equipo.setFechaAdquisicion(newFecha);
        assertEquals(newFecha, equipo.getFechaAdquisicion());
    }

    @Test
     void testGetAndSetEstado() {
        equipo.setEstado(Estados.INACTIVO);
        assertEquals(Estados.INACTIVO, equipo.getEstado());
    }

    @Test
    void testGetAndSetEquiposUbicaciones() {
        Set<EquiposUbicacione> newEquiposUbicaciones = new LinkedHashSet<>();
        equipo.setEquiposUbicaciones(newEquiposUbicaciones);
        assertEquals(newEquiposUbicaciones, equipo.getEquiposUbicaciones());
    }

    @Test
     void testGetAndSetIdUbicacion() {
        Ubicacion newUbicacion = Mockito.mock(Ubicacion.class);
        equipo.setIdUbicacion(newUbicacion);
        assertEquals(newUbicacion, equipo.getIdUbicacion());
    }

    @Test
     void testConstructor() {
        assertNotNull(equipo.getId());
        assertNotNull(equipo.getIdInterno());
        assertNotNull(equipo.getNroSerie());
        assertNotNull(equipo.getNombre());
        assertNotNull(equipo.getIdTipo());
        assertNotNull(equipo.getIdProveedor());
        assertNotNull(equipo.getIdPais());
        assertNotNull(equipo.getIdModelo());
        assertNotNull(equipo.getImagen());
        assertNotNull(equipo.getFechaAdquisicion());
        assertNotNull(equipo.getEstado());
        assertNotNull(equipo.getIdUbicacion());
    }
}
