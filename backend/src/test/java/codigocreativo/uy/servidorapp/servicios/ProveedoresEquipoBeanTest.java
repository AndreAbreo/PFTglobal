package codigocreativo.uy.servidorapp.servicios;

import codigocreativo.uy.servidorapp.dtos.ProveedoresEquipoDto;
import codigocreativo.uy.servidorapp.dtos.dtomappers.ProveedoresEquipoMapper;
import codigocreativo.uy.servidorapp.entidades.ProveedoresEquipo;
import codigocreativo.uy.servidorapp.enumerados.Estados;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.Query;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import java.lang.reflect.Field;

class ProveedoresEquipoBeanTest {
    @Mock EntityManager em;
    @Mock ProveedoresEquipoMapper proveedoresEquipoMapper;
    @InjectMocks ProveedoresEquipoBean bean;

    private ProveedoresEquipoDto dto;

    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
        Field emField = bean.getClass().getDeclaredField("em");
        emField.setAccessible(true);
        emField.set(bean, em);
        
        dto = new ProveedoresEquipoDto();
        dto.setNombre("Proveedor Test");
        dto.setEstado(Estados.ACTIVO);
    }

    @Test
    void testCrearProveedor_success() {
        when(proveedoresEquipoMapper.toEntity(dto)).thenReturn(new ProveedoresEquipo());
        TypedQuery<Long> query = mock(TypedQuery.class);
        when(em.createQuery(anyString(), eq(Long.class))).thenReturn(query);
        when(query.setParameter(anyString(), any())).thenReturn(query);
        when(query.getSingleResult()).thenReturn(0L);
        
        bean.crearProveedor(dto);
        
        assertEquals(Estados.ACTIVO, dto.getEstado());
        verify(em).persist(any());
        verify(em).flush();
    }

    @Test
    void testCrearProveedor_nombreNulo() {
        dto.setNombre(null);
        
        assertThrows(IllegalArgumentException.class, () -> bean.crearProveedor(dto), "El nombre del proveedor no puede ser nulo ni vacío");
    }

    @Test
    void testCrearProveedor_nombreVacio() {
        dto.setNombre("");
        
        assertThrows(IllegalArgumentException.class, () -> bean.crearProveedor(dto), "El nombre del proveedor no puede ser nulo ni vacío");
    }

    @Test
    void testCrearProveedor_nombreDuplicado() {
        TypedQuery<Long> query = mock(TypedQuery.class);
        when(em.createQuery(anyString(), eq(Long.class))).thenReturn(query);
        when(query.setParameter(anyString(), any())).thenReturn(query);
        when(query.getSingleResult()).thenReturn(1L);
        
        assertThrows(IllegalArgumentException.class, () -> bean.crearProveedor(dto), "Ya existe un proveedor con ese nombre");
    }

    @Test
    void testModificarProveedor_success() {
        when(proveedoresEquipoMapper.toEntity(dto)).thenReturn(new ProveedoresEquipo());
        TypedQuery<Long> query = mock(TypedQuery.class);
        when(em.createQuery(anyString(), eq(Long.class))).thenReturn(query);
        when(query.setParameter(anyString(), any())).thenReturn(query);
        when(query.getSingleResult()).thenReturn(0L);
        
        bean.modificarProveedor(dto);
        
        verify(em).merge(any());
        verify(em).flush();
    }

    @Test
    void testModificarProveedor_nombreDuplicado() {
        TypedQuery<Long> query = mock(TypedQuery.class);
        when(em.createQuery(anyString(), eq(Long.class))).thenReturn(query);
        when(query.setParameter(anyString(), any())).thenReturn(query);
        when(query.getSingleResult()).thenReturn(1L);
        
        assertThrows(IllegalArgumentException.class, () -> bean.modificarProveedor(dto), "Ya existe otro proveedor con ese nombre");
    }

    @Test
    void testObtenerProveedor() {
        when(em.find(eq(ProveedoresEquipo.class), anyLong())).thenReturn(new ProveedoresEquipo());
        when(proveedoresEquipoMapper.toDto(any(ProveedoresEquipo.class))).thenReturn(new ProveedoresEquipoDto());
        assertNotNull(bean.obtenerProveedor(1L));
    }

    @Test
    void testObtenerProveedores() {
        @SuppressWarnings("unchecked")
        TypedQuery<ProveedoresEquipo> query = mock(TypedQuery.class);
        when(em.createQuery(anyString(), eq(ProveedoresEquipo.class))).thenReturn(query);
        when(query.getResultList()).thenReturn(List.of(new ProveedoresEquipo()));
        when(proveedoresEquipoMapper.toDto(anyList())).thenReturn(List.of(new ProveedoresEquipoDto()));
        assertNotNull(bean.obtenerProveedores());
    }

    @Test
    void testEliminarProveedor_success() {
        Query queryMock = mock(Query.class);
        when(em.createQuery(anyString())).thenReturn(queryMock);
        when(queryMock.setParameter(anyString(), any())).thenReturn(queryMock);
        when(queryMock.executeUpdate()).thenReturn(1);
        
        bean.eliminarProveedor(1L);
        
        verify(em).createQuery(anyString());
        verify(em).flush();
    }

    @Test
    void testEliminarProveedor_noEncontrado() {
        Query queryMock = mock(Query.class);
        when(em.createQuery(anyString())).thenReturn(queryMock);
        when(queryMock.setParameter(anyString(), any())).thenReturn(queryMock);
        when(queryMock.executeUpdate()).thenReturn(0);
        
        assertThrows(IllegalArgumentException.class, () -> bean.eliminarProveedor(1L), "Proveedor no encontrado");
    }

    @Test
    void testFiltrarProveedores_soloEstado() {
        @SuppressWarnings("unchecked")
        TypedQuery<ProveedoresEquipo> query = mock(TypedQuery.class);
        when(em.createQuery(anyString(), eq(ProveedoresEquipo.class))).thenReturn(query);
        when(query.setParameter(anyString(), any())).thenReturn(query);
        when(query.getResultList()).thenReturn(List.of(new ProveedoresEquipo()));
        when(proveedoresEquipoMapper.toDto(anyList())).thenReturn(List.of(new ProveedoresEquipoDto()));
        
        List<ProveedoresEquipoDto> result = bean.filtrarProveedores(null, "ACTIVO");
        
        assertNotNull(result);
        verify(query).setParameter("estado", "ACTIVO");
    }

    @Test
    void testFiltrarProveedores_soloNombre() {
        @SuppressWarnings("unchecked")
        TypedQuery<ProveedoresEquipo> query = mock(TypedQuery.class);
        when(em.createQuery(anyString(), eq(ProveedoresEquipo.class))).thenReturn(query);
        when(query.setParameter(anyString(), any())).thenReturn(query);
        when(query.getResultList()).thenReturn(List.of(new ProveedoresEquipo()));
        when(proveedoresEquipoMapper.toDto(anyList())).thenReturn(List.of(new ProveedoresEquipoDto()));
        
        List<ProveedoresEquipoDto> result = bean.filtrarProveedores("test", null);
        
        assertNotNull(result);
        verify(query).setParameter("nombre", "%test%");
    }

    @Test
    void testFiltrarProveedores_estadoYNombre() {
        @SuppressWarnings("unchecked")
        TypedQuery<ProveedoresEquipo> query = mock(TypedQuery.class);
        when(em.createQuery(anyString(), eq(ProveedoresEquipo.class))).thenReturn(query);
        when(query.setParameter(anyString(), any())).thenReturn(query);
        when(query.getResultList()).thenReturn(List.of(new ProveedoresEquipo()));
        when(proveedoresEquipoMapper.toDto(anyList())).thenReturn(List.of(new ProveedoresEquipoDto()));
        
        List<ProveedoresEquipoDto> result = bean.filtrarProveedores("test", "ACTIVO");
        
        assertNotNull(result);
        verify(query).setParameter("estado", "ACTIVO");
        verify(query).setParameter("nombre", "%test%");
    }

    @Test
    void testFiltrarProveedores_sinFiltros() {
        @SuppressWarnings("unchecked")
        TypedQuery<ProveedoresEquipo> query = mock(TypedQuery.class);
        when(em.createQuery(anyString(), eq(ProveedoresEquipo.class))).thenReturn(query);
        when(query.getResultList()).thenReturn(List.of(new ProveedoresEquipo()));
        when(proveedoresEquipoMapper.toDto(anyList())).thenReturn(List.of(new ProveedoresEquipoDto()));
        
        List<ProveedoresEquipoDto> result = bean.filtrarProveedores(null, null);
        
        assertNotNull(result);
        // Verifica que se llama al método obtenerProveedores() (sin filtro)
        verify(em).createQuery("SELECT p FROM ProveedoresEquipo p ORDER BY p.nombre ASC", ProveedoresEquipo.class);
    }

    @Test
    void testFiltrarProveedores_estadoInvalido() {
        assertThrows(IllegalArgumentException.class, () -> {
            bean.filtrarProveedores(null, "ESTADO_INVALIDO");
        });
    }
} 