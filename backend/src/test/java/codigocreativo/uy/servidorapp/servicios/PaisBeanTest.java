package codigocreativo.uy.servidorapp.servicios;

import codigocreativo.uy.servidorapp.dtos.PaisDto;
import codigocreativo.uy.servidorapp.dtos.dtomappers.PaisMapper;
import codigocreativo.uy.servidorapp.entidades.Pais;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import java.lang.reflect.Field;

class PaisBeanTest {
    @Mock EntityManager em;
    @Mock PaisMapper paisMapper;
    @InjectMocks PaisBean paisBean;

    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
        Field emField = paisBean.getClass().getDeclaredField("em");
        emField.setAccessible(true);
        emField.set(paisBean, em);
    }

    @Test
    void testCrearPais() {
        PaisDto dto = new PaisDto();
        dto.setNombre("Uruguay");
        when(paisMapper.toEntity(dto)).thenReturn(new Pais());
        TypedQuery<Long> query = mock(TypedQuery.class);
        when(em.createQuery(anyString(), eq(Long.class))).thenReturn(query);
        when(query.setParameter(anyString(), any())).thenReturn(query);
        when(query.getSingleResult()).thenReturn(0L);
        paisBean.crearPais(dto);
        verify(em).persist(any());
        verify(em).flush();
    }

    @Test
    void testCrearPais_nombreNulo() {
        PaisDto dto = new PaisDto();
        dto.setNombre(null);
        assertThrows(IllegalArgumentException.class, () -> paisBean.crearPais(dto), "El nombre del país no puede ser nulo ni vacío");
    }

    @Test
    void testCrearPais_nombreVacio() {
        PaisDto dto = new PaisDto();
        dto.setNombre("");
        assertThrows(IllegalArgumentException.class, () -> paisBean.crearPais(dto), "El nombre del país no puede ser nulo ni vacío");
    }

    @Test
    void testModificarPais() {
        PaisDto dto = new PaisDto();
        when(paisMapper.toEntity(dto)).thenReturn(new Pais());
        paisBean.modificarPais(dto);
        verify(em).merge(any());
        verify(em).flush();
    }

    @Test
    void testObtenerPais() {
        @SuppressWarnings("unchecked")
        TypedQuery<Pais> query = mock(TypedQuery.class);
        when(em.createQuery(anyString(), eq(Pais.class))).thenReturn(query);
        when(query.getResultList()).thenReturn(List.of(new Pais()));
        when(paisMapper.toDto(anyList())).thenReturn(List.of(new PaisDto()));
        assertNotNull(paisBean.obtenerpais());
    }

    @Test
    void testObtenerPaisPorEstado() {
        @SuppressWarnings("unchecked")
        TypedQuery<Pais> query = mock(TypedQuery.class);
        when(em.createQuery(anyString(), eq(Pais.class))).thenReturn(query);
        when(query.setParameter(anyString(), any())).thenReturn(query);
        when(query.getResultList()).thenReturn(List.of(new Pais()));
        when(paisMapper.toDto(anyList())).thenReturn(List.of(new PaisDto()));
        
        List<PaisDto> result = paisBean.obtenerPaisPorEstado(codigocreativo.uy.servidorapp.enumerados.Estados.ACTIVO);
        
        assertNotNull(result);
        verify(query).setParameter("estado", "ACTIVO");
    }

    @Test
    void testObtenerPaisPorEstadoOpcional_conEstado() {
        @SuppressWarnings("unchecked")
        TypedQuery<Pais> query = mock(TypedQuery.class);
        when(em.createQuery(anyString(), eq(Pais.class))).thenReturn(query);
        when(query.setParameter(anyString(), any())).thenReturn(query);
        when(query.getResultList()).thenReturn(List.of(new Pais()));
        when(paisMapper.toDto(anyList())).thenReturn(List.of(new PaisDto()));
        
        List<PaisDto> result = paisBean.obtenerPaisPorEstadoOpcional(codigocreativo.uy.servidorapp.enumerados.Estados.ACTIVO);
        
        assertNotNull(result);
        verify(query).setParameter("estado", "ACTIVO");
    }

    @Test
    void testObtenerPaisPorEstadoOpcional_sinEstado() {
        @SuppressWarnings("unchecked")
        TypedQuery<Pais> query = mock(TypedQuery.class);
        when(em.createQuery(anyString(), eq(Pais.class))).thenReturn(query);
        when(query.getResultList()).thenReturn(List.of(new Pais()));
        when(paisMapper.toDto(anyList())).thenReturn(List.of(new PaisDto()));
        
        List<PaisDto> result = paisBean.obtenerPaisPorEstadoOpcional(null);
        
        assertNotNull(result);

        verify(em).createQuery("SELECT p FROM Pais p ORDER BY p.nombre ASC", Pais.class);
    }

    @Test
    void testObtenerPaisPorNombre() {
        @SuppressWarnings("unchecked")
        TypedQuery<Pais> query = mock(TypedQuery.class);
        when(em.createQuery(anyString(), eq(Pais.class))).thenReturn(query);
        when(query.setParameter(anyString(), any())).thenReturn(query);
        when(query.getResultList()).thenReturn(List.of(new Pais()));
        when(paisMapper.toDto(anyList())).thenReturn(List.of(new PaisDto()));
        
        List<PaisDto> result = paisBean.obtenerPaisPorNombre("uruguay");
        
        assertNotNull(result);
        verify(query).setParameter("nombre", "%uruguay%");
    }

    @Test
    void testObtenerPaisPorEstadoYNombre() {
        @SuppressWarnings("unchecked")
        TypedQuery<Pais> query = mock(TypedQuery.class);
        when(em.createQuery(anyString(), eq(Pais.class))).thenReturn(query);
        when(query.setParameter(anyString(), any())).thenReturn(query);
        when(query.getResultList()).thenReturn(List.of(new Pais()));
        when(paisMapper.toDto(anyList())).thenReturn(List.of(new PaisDto()));
        
        List<PaisDto> result = paisBean.obtenerPaisPorEstadoYNombre(codigocreativo.uy.servidorapp.enumerados.Estados.ACTIVO, "uruguay");
        
        assertNotNull(result);
        verify(query).setParameter("estado", "ACTIVO");
        verify(query).setParameter("nombre", "%uruguay%");
    }

    @Test
    void testFiltrarPaises_soloEstado() {
        @SuppressWarnings("unchecked")
        TypedQuery<Pais> query = mock(TypedQuery.class);
        when(em.createQuery(anyString(), eq(Pais.class))).thenReturn(query);
        when(query.setParameter(anyString(), any())).thenReturn(query);
        when(query.getResultList()).thenReturn(List.of(new Pais()));
        when(paisMapper.toDto(anyList())).thenReturn(List.of(new PaisDto()));
        
        List<PaisDto> result = paisBean.filtrarPaises("ACTIVO", null);
        
        assertNotNull(result);
        verify(query).setParameter("estado", "ACTIVO");
    }

    @Test
    void testFiltrarPaises_soloNombre() {
        @SuppressWarnings("unchecked")
        TypedQuery<Pais> query = mock(TypedQuery.class);
        when(em.createQuery(anyString(), eq(Pais.class))).thenReturn(query);
        when(query.setParameter(anyString(), any())).thenReturn(query);
        when(query.getResultList()).thenReturn(List.of(new Pais()));
        when(paisMapper.toDto(anyList())).thenReturn(List.of(new PaisDto()));
        
        List<PaisDto> result = paisBean.filtrarPaises(null, "uruguay");
        
        assertNotNull(result);
        verify(query).setParameter("nombre", "%uruguay%");
    }

    @Test
    void testFiltrarPaises_estadoYNombre() {
        @SuppressWarnings("unchecked")
        TypedQuery<Pais> query = mock(TypedQuery.class);
        when(em.createQuery(anyString(), eq(Pais.class))).thenReturn(query);
        when(query.setParameter(anyString(), any())).thenReturn(query);
        when(query.getResultList()).thenReturn(List.of(new Pais()));
        when(paisMapper.toDto(anyList())).thenReturn(List.of(new PaisDto()));
        
        List<PaisDto> result = paisBean.filtrarPaises("ACTIVO", "uruguay");
        
        assertNotNull(result);
        verify(query).setParameter("estado", "ACTIVO");
        verify(query).setParameter("nombre", "%uruguay%");
    }

    @Test
    void testFiltrarPaises_sinFiltros() {
        @SuppressWarnings("unchecked")
        TypedQuery<Pais> query = mock(TypedQuery.class);
        when(em.createQuery(anyString(), eq(Pais.class))).thenReturn(query);
        when(query.getResultList()).thenReturn(List.of(new Pais()));
        when(paisMapper.toDto(anyList())).thenReturn(List.of(new PaisDto()));
        
        List<PaisDto> result = paisBean.filtrarPaises(null, null);
        
        assertNotNull(result);

        verify(em).createQuery("SELECT p FROM Pais p ORDER BY p.nombre ASC", Pais.class);
    }

    @Test
    void testFiltrarPaises_estadoInvalido() {
        assertThrows(IllegalArgumentException.class, () -> {
            paisBean.filtrarPaises("ESTADO_INVALIDO", null);
        });
    }

    @Test
    void testObtenerPaisPorId() {
        Pais pais = new Pais();
        PaisDto dto = new PaisDto();
        when(em.find(Pais.class, 1L)).thenReturn(pais);
        when(paisMapper.toDto(pais)).thenReturn(dto);
        
        PaisDto result = paisBean.obtenerPaisPorId(1L);
        
        assertEquals(dto, result);
        verify(em).find(Pais.class, 1L);
    }

    @Test
    void testInactivarPais() {
        @SuppressWarnings("unchecked")
        jakarta.persistence.Query query = mock(jakarta.persistence.Query.class);
        when(em.createQuery(anyString())).thenReturn(query);
        when(query.setParameter(anyString(), any())).thenReturn(query);
        when(query.executeUpdate()).thenReturn(1); // País encontrado y actualizado
        
        paisBean.inactivarPais(1L);
        
        verify(query).executeUpdate();
        verify(em).flush();
    }

    @Test
    void testInactivarPais_noEncontrado() {
        @SuppressWarnings("unchecked")
        jakarta.persistence.Query query = mock(jakarta.persistence.Query.class);
        when(em.createQuery(anyString())).thenReturn(query);
        when(query.setParameter(anyString(), any())).thenReturn(query);
        when(query.executeUpdate()).thenReturn(0); // País no encontrado
        
        assertThrows(IllegalArgumentException.class, () -> paisBean.inactivarPais(1L), "País no encontrado");
    }

    @Test
    void testCrearPais_formateaNombre() {
        PaisDto dto = new PaisDto();
        dto.setNombre("uruguay"); // nombre en minúscula
        when(paisMapper.toEntity(dto)).thenReturn(new Pais());
        TypedQuery<Long> query = mock(TypedQuery.class);
        when(em.createQuery(anyString(), eq(Long.class))).thenReturn(query);
        when(query.setParameter(anyString(), any())).thenReturn(query);
        when(query.getSingleResult()).thenReturn(0L);
        
        paisBean.crearPais(dto);

        assertEquals("Uruguay", dto.getNombre());
        verify(em).persist(any());
    }

    @Test
    void testCrearPais_asignaEstadoActivo() {
        PaisDto dto = new PaisDto();
        dto.setNombre("Uruguay");
        when(paisMapper.toEntity(dto)).thenReturn(new Pais());
        TypedQuery<Long> query = mock(TypedQuery.class);
        when(em.createQuery(anyString(), eq(Long.class))).thenReturn(query);
        when(query.setParameter(anyString(), any())).thenReturn(query);
        when(query.getSingleResult()).thenReturn(0L);
        
        paisBean.crearPais(dto);

        assertEquals(codigocreativo.uy.servidorapp.enumerados.Estados.ACTIVO, dto.getEstado());
        verify(em).persist(any());
    }
} 