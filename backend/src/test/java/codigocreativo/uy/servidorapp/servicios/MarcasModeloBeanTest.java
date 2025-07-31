package codigocreativo.uy.servidorapp.servicios;

import codigocreativo.uy.servidorapp.dtos.dtomappers.MarcasModeloMapper;
import codigocreativo.uy.servidorapp.dtos.MarcasModeloDto;
import codigocreativo.uy.servidorapp.entidades.MarcasModelo;
import codigocreativo.uy.servidorapp.enumerados.Estados;
import codigocreativo.uy.servidorapp.excepciones.ServiciosException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class MarcasModeloBeanTest {

    @Mock
    private EntityManager em;

    @Mock
    private MarcasModeloMapper marcasModeloMapper;

    @InjectMocks
    private MarcasModeloBean marcasModeloBean;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
        marcasModeloBean = new MarcasModeloBean(marcasModeloMapper);

        Field emField = MarcasModeloBean.class.getDeclaredField("em");
        emField.setAccessible(true);
        emField.set(marcasModeloBean, em);
    }


    @Test
    void testCrearMarcasModelo_conDtoNulo() {
        ServiciosException exception = assertThrows(ServiciosException.class, 
            () -> marcasModeloBean.crearMarcasModelo(null));
        assertEquals("La marca es obligatoria", exception.getMessage());
    }

    @Test
    void testCrearMarcasModelo_conNombreNulo() {
        MarcasModeloDto dto = new MarcasModeloDto();
        dto.setNombre(null);

        ServiciosException exception = assertThrows(ServiciosException.class, 
            () -> marcasModeloBean.crearMarcasModelo(dto));
        assertEquals("El nombre de la marca es obligatorio", exception.getMessage());
    }

    @Test
    void testCrearMarcasModelo_conNombreVacio() {
        MarcasModeloDto dto = new MarcasModeloDto();
        dto.setNombre("");

        ServiciosException exception = assertThrows(ServiciosException.class, 
            () -> marcasModeloBean.crearMarcasModelo(dto));
        assertEquals("El nombre de la marca es obligatorio", exception.getMessage());
    }

    @Test
    void testCrearMarcasModelo_conNombreDuplicado() {
        MarcasModeloDto dto = new MarcasModeloDto();
        dto.setNombre("MarcaExistente");

        @SuppressWarnings("unchecked")
        TypedQuery<MarcasModelo> queryMock = mock(TypedQuery.class);
        when(em.createQuery(anyString(), eq(MarcasModelo.class))).thenReturn(queryMock);
        when(queryMock.setParameter("nombre", "MARCAEXISTENTE")).thenReturn(queryMock);
        when(queryMock.getSingleResult()).thenReturn(new MarcasModelo()); // Existe

        ServiciosException exception = assertThrows(ServiciosException.class, 
            () -> marcasModeloBean.crearMarcasModelo(dto));
        assertEquals("Ya existe una marca con el nombre: MarcaExistente", exception.getMessage());
    }

    @Test
    void testCrearMarcasModelo_exitoso() throws ServiciosException {
        MarcasModeloDto dto = new MarcasModeloDto();
        dto.setNombre("NuevaMarca");
        MarcasModelo entity = new MarcasModelo();

        @SuppressWarnings("unchecked")
        TypedQuery<MarcasModelo> queryMock = mock(TypedQuery.class);
        when(em.createQuery(anyString(), eq(MarcasModelo.class))).thenReturn(queryMock);
        when(queryMock.setParameter("nombre", "NUEVAMARCA")).thenReturn(queryMock);
        when(queryMock.getSingleResult()).thenThrow(new NoResultException("No existe"));

        when(marcasModeloMapper.toEntity(dto)).thenReturn(entity);

        assertDoesNotThrow(() -> marcasModeloBean.crearMarcasModelo(dto));
        
        assertEquals(Estados.ACTIVO, dto.getEstado());
        verify(em, times(1)).persist(entity);
        verify(em, times(1)).flush();
    }


    @Test
    void testModificarMarcasModelo_conDtoNulo() {
        ServiciosException exception = assertThrows(ServiciosException.class, 
            () -> marcasModeloBean.modificarMarcasModelo(null));
        assertEquals("La marca es obligatoria", exception.getMessage());
    }

    @Test
    void testModificarMarcasModelo_conIdNulo() {
        MarcasModeloDto dto = new MarcasModeloDto();
        dto.setNombre("Marca");

        ServiciosException exception = assertThrows(ServiciosException.class, 
            () -> marcasModeloBean.modificarMarcasModelo(dto));
        assertEquals("El ID de la marca es obligatorio para modificar", exception.getMessage());
    }

    @Test
    void testModificarMarcasModelo_noExiste() {
        MarcasModeloDto dto = new MarcasModeloDto();
        dto.setId(1L);
        dto.setNombre("Marca");

        when(em.find(MarcasModelo.class, 1L)).thenReturn(null);

        ServiciosException exception = assertThrows(ServiciosException.class, 
            () -> marcasModeloBean.modificarMarcasModelo(dto));
        assertEquals("No se encontró la marca con ID: 1", exception.getMessage());
    }

    @Test
    void testModificarMarcasModelo_nombreNoPermitido() {
        MarcasModeloDto dto = new MarcasModeloDto();
        dto.setId(1L);
        dto.setNombre("MarcaNueva");
        dto.setEstado(Estados.ACTIVO);
        
        MarcasModelo actual = new MarcasModelo();
        actual.setId(1L);
        actual.setNombre("MarcaOriginal");
        actual.setEstado("ACTIVO");
        
        when(em.find(MarcasModelo.class, 1L)).thenReturn(actual);

        ServiciosException exception = assertThrows(ServiciosException.class, 
            () -> marcasModeloBean.modificarMarcasModelo(dto));
        assertEquals("No se permite modificar el nombre de la marca", exception.getMessage());
    }

    @Test
    void testModificarMarcasModelo_exitoso() {
        MarcasModeloDto dto = new MarcasModeloDto();
        dto.setId(1L);
        dto.setNombre("Marca1");
        dto.setEstado(Estados.ACTIVO);
        
        MarcasModelo actual = new MarcasModelo();
        actual.setId(1L);
        actual.setNombre("Marca1");
        actual.setEstado("INACTIVO");
        
        when(em.find(MarcasModelo.class, 1L)).thenReturn(actual);

        assertDoesNotThrow(() -> marcasModeloBean.modificarMarcasModelo(dto));
        
        assertEquals("ACTIVO", actual.getEstado());
        verify(em, times(1)).merge(actual);
        verify(em, times(1)).flush();
    }


    @Test
    void testObtenerMarca_conIdNulo() {
        ServiciosException exception = assertThrows(ServiciosException.class, 
            () -> marcasModeloBean.obtenerMarca(null));
        assertEquals("El ID es obligatorio", exception.getMessage());
    }

    @Test
    void testObtenerMarca_noExiste() {
        when(em.find(MarcasModelo.class, 1L)).thenReturn(null);

        ServiciosException exception = assertThrows(ServiciosException.class, 
            () -> marcasModeloBean.obtenerMarca(1L));
        assertEquals("Marca no encontrada", exception.getMessage());
    }

    @Test
    void testObtenerMarca_exitoso() throws ServiciosException {
        Long id = 1L;
        MarcasModelo entity = new MarcasModelo();
        MarcasModeloDto dto = new MarcasModeloDto();

        when(em.find(MarcasModelo.class, id)).thenReturn(entity);
        when(marcasModeloMapper.toDto(entity)).thenReturn(dto);

        MarcasModeloDto result = marcasModeloBean.obtenerMarca(id);

        assertEquals(dto, result);
        verify(em).find(MarcasModelo.class, id);
        verify(marcasModeloMapper).toDto(entity);
    }


    @Test
    void testObtenerMarcasLista_exitoso() {
        List<MarcasModelo> entities = new ArrayList<>();
        List<MarcasModeloDto> dtos = new ArrayList<>();
        
        @SuppressWarnings("unchecked")
        TypedQuery<MarcasModelo> query = mock(TypedQuery.class);
        when(em.createQuery(anyString(), eq(MarcasModelo.class))).thenReturn(query);
        when(query.getResultList()).thenReturn(entities);
        when(marcasModeloMapper.toDto(entities)).thenReturn(dtos);

        List<MarcasModeloDto> result = marcasModeloBean.obtenerMarcasLista();

        assertNotNull(result);
        assertEquals(dtos, result);
        verify(query, times(1)).getResultList();
        verify(marcasModeloMapper).toDto(entities);
    }


    @Test
    void testEliminarMarca_conIdNulo() {
        ServiciosException exception = assertThrows(ServiciosException.class, 
            () -> marcasModeloBean.eliminarMarca(null));
        assertEquals("El ID es obligatorio", exception.getMessage());
    }

    @Test
    void testEliminarMarca_exitoso() {
        Long id = 1L;
        MarcasModelo marca = new MarcasModelo();
        marca.setId(id);
        
        when(em.find(MarcasModelo.class, id)).thenReturn(marca);
        
        @SuppressWarnings("unchecked")
        jakarta.persistence.Query query = mock(jakarta.persistence.Query.class);
        when(em.createQuery(anyString())).thenReturn(query);
        when(query.setParameter("id", id)).thenReturn(query);
        when(query.executeUpdate()).thenReturn(1);

        assertDoesNotThrow(() -> marcasModeloBean.eliminarMarca(id));

        verify(query, times(1)).executeUpdate();
        verify(em, times(1)).flush();
    }

    @Test
    void testEliminarMarca_noExiste() {
        Long id = 1L;
        when(em.find(MarcasModelo.class, id)).thenReturn(null);

        ServiciosException exception = assertThrows(ServiciosException.class, 
            () -> marcasModeloBean.eliminarMarca(id));
        assertEquals("Marca no encontrada", exception.getMessage());
    }


    @Test
    void testCrearMarcasModelo_errorBaseDatos() {
        MarcasModeloDto dto = new MarcasModeloDto();
        dto.setNombre("NuevaMarca");

        @SuppressWarnings("unchecked")
        TypedQuery<MarcasModelo> queryMock = mock(TypedQuery.class);
        when(em.createQuery(anyString(), eq(MarcasModelo.class))).thenReturn(queryMock);
        when(queryMock.setParameter("nombre", "NUEVAMARCA")).thenReturn(queryMock);
        when(queryMock.getSingleResult()).thenThrow(new NoResultException("No existe"));

        when(marcasModeloMapper.toEntity(dto)).thenReturn(new MarcasModelo());
        doThrow(new RuntimeException("Error de BD")).when(em).persist(any());

        ServiciosException exception = assertThrows(ServiciosException.class, 
            () -> marcasModeloBean.crearMarcasModelo(dto));
        assertEquals("Error al crear la marca", exception.getMessage());
    }
}
