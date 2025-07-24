package codigocreativo.uy.servidorapp.servicios;

import codigocreativo.uy.servidorapp.dtos.TiposEquipoDto;
import codigocreativo.uy.servidorapp.dtos.dtomappers.TiposEquipoMapper;
import codigocreativo.uy.servidorapp.entidades.TiposEquipo;
import codigocreativo.uy.servidorapp.enumerados.Estados;
import codigocreativo.uy.servidorapp.excepciones.ServiciosException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import jakarta.persistence.NoResultException;

class TiposEquipoBeanTest {

    @Mock
    private EntityManager em;

    @Mock
    private TiposEquipoMapper tiposEquipoMapper;

    @InjectMocks
    private TiposEquipoBean tiposEquipoBean;

    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);

        tiposEquipoBean = new TiposEquipoBean(tiposEquipoMapper);

        // Inyectar el EntityManager usando reflexión
        Field emField = TiposEquipoBean.class.getDeclaredField("em");
        emField.setAccessible(true);
        emField.set(tiposEquipoBean, em);
    }

    // ========== TESTS PARA CREAR TIPOS EQUIPO ==========

    @Test
    void testCrearTiposEquipo_conDtoNulo() {
        ServiciosException exception = assertThrows(ServiciosException.class, 
            () -> tiposEquipoBean.crearTiposEquipo(null));
        assertEquals("El tipo de equipo es obligatorio", exception.getMessage());
    }

    @Test
    void testCrearTiposEquipo_conNombreNulo() {
        TiposEquipoDto dto = new TiposEquipoDto();
        dto.setNombreTipo(null);

        ServiciosException exception = assertThrows(ServiciosException.class, 
            () -> tiposEquipoBean.crearTiposEquipo(dto));
        assertEquals("El nombre del tipo de equipo es obligatorio", exception.getMessage());
    }

    @Test
    void testCrearTiposEquipo_conNombreVacio() {
        TiposEquipoDto dto = new TiposEquipoDto();
        dto.setNombreTipo("");

        ServiciosException exception = assertThrows(ServiciosException.class, 
            () -> tiposEquipoBean.crearTiposEquipo(dto));
        assertEquals("El nombre del tipo de equipo es obligatorio", exception.getMessage());
    }

    @Test
    void testCrearTiposEquipo_conNombreDuplicado() {
        TiposEquipoDto dto = new TiposEquipoDto();
        dto.setNombreTipo("TipoExistente");

        // Mock para nombre duplicado - debe retornar un resultado
        @SuppressWarnings("unchecked")
        TypedQuery<TiposEquipo> queryMock = mock(TypedQuery.class);
        when(em.createQuery(anyString(), eq(TiposEquipo.class))).thenReturn(queryMock);
        when(queryMock.setParameter("nombre", "TIPOEXISTENTE")).thenReturn(queryMock);
        when(queryMock.getSingleResult()).thenReturn(new TiposEquipo()); // Existe

        ServiciosException exception = assertThrows(ServiciosException.class, 
            () -> tiposEquipoBean.crearTiposEquipo(dto));
        assertEquals("Ya existe un tipo de equipo con el nombre: TipoExistente", exception.getMessage());
    }

    @Test
    void testCrearTiposEquipo_exitoso() throws ServiciosException {
        TiposEquipoDto dto = new TiposEquipoDto();
        dto.setNombreTipo("NuevoTipo");
        TiposEquipo entity = new TiposEquipo();

        // Mock para nombre único - debe lanzar NoResultException
        @SuppressWarnings("unchecked")
        TypedQuery<TiposEquipo> queryMock = mock(TypedQuery.class);
        when(em.createQuery(anyString(), eq(TiposEquipo.class))).thenReturn(queryMock);
        when(queryMock.setParameter("nombre", "NUEVOTIPO")).thenReturn(queryMock);
        when(queryMock.getSingleResult()).thenThrow(new NoResultException("No existe"));

        when(tiposEquipoMapper.toEntity(dto)).thenReturn(entity);

        assertDoesNotThrow(() -> tiposEquipoBean.crearTiposEquipo(dto));

        assertEquals(Estados.ACTIVO, dto.getEstado());
        verify(em, times(1)).persist(entity);
        verify(em, times(1)).flush();
    }

    // ========== TESTS PARA MODIFICAR TIPOS EQUIPO ==========

    @Test
    void testModificarTiposEquipo_conDtoNulo() {
        ServiciosException exception = assertThrows(ServiciosException.class, 
            () -> tiposEquipoBean.modificarTiposEquipo(null));
        assertEquals("El tipo de equipo es obligatorio", exception.getMessage());
    }

    @Test
    void testModificarTiposEquipo_conIdNulo() {
        TiposEquipoDto dto = new TiposEquipoDto();
        dto.setNombreTipo("Tipo");

        ServiciosException exception = assertThrows(ServiciosException.class, 
            () -> tiposEquipoBean.modificarTiposEquipo(dto));
        assertEquals("El ID del tipo de equipo es obligatorio para modificar", exception.getMessage());
    }

    @Test
    void testModificarTiposEquipo_noExiste() {
        TiposEquipoDto dto = new TiposEquipoDto();
        dto.setId(1L);
        dto.setNombreTipo("Tipo");

        when(em.find(TiposEquipo.class, 1L)).thenReturn(null);

        ServiciosException exception = assertThrows(ServiciosException.class, 
            () -> tiposEquipoBean.modificarTiposEquipo(dto));
        assertEquals("No se encontró el tipo de equipo con ID: 1", exception.getMessage());
    }

    @Test
    void testModificarTiposEquipo_exitoso() throws ServiciosException {
        TiposEquipoDto dto = new TiposEquipoDto();
        dto.setId(1L);
        dto.setNombreTipo("TipoModificado");
        dto.setEstado(Estados.INACTIVO);
        
        TiposEquipo entity = new TiposEquipo();
        entity.setId(1L);

        when(em.find(TiposEquipo.class, 1L)).thenReturn(entity);
        when(tiposEquipoMapper.toEntity(dto)).thenReturn(entity);

        assertDoesNotThrow(() -> tiposEquipoBean.modificarTiposEquipo(dto));

        verify(em, times(1)).merge(entity);
        verify(em, times(1)).flush();
    }

    // ========== TESTS PARA ELIMINAR TIPOS EQUIPO ==========

    @Test
    void testEliminarTiposEquipo_conIdNulo() {
        ServiciosException exception = assertThrows(ServiciosException.class, 
            () -> tiposEquipoBean.eliminarTiposEquipo(null));
        assertEquals("El ID es obligatorio", exception.getMessage());
    }

    @Test
    void testEliminarTiposEquipo_noExiste() {
        Long id = 1L;
        when(em.find(TiposEquipo.class, id)).thenReturn(null);

        ServiciosException exception = assertThrows(ServiciosException.class, 
            () -> tiposEquipoBean.eliminarTiposEquipo(id));
        assertEquals("No se encontró el tipo de equipo con ID: 1", exception.getMessage());
    }

    @Test
    void testEliminarTiposEquipo_exitoso() throws ServiciosException {
        Long id = 1L;
        TiposEquipo entity = new TiposEquipo();
        entity.setEstado("ACTIVO");

        when(em.find(TiposEquipo.class, id)).thenReturn(entity);

        assertDoesNotThrow(() -> tiposEquipoBean.eliminarTiposEquipo(id));

        assertEquals("INACTIVO", entity.getEstado());
        verify(em).merge(entity);
    }

    // ========== TESTS PARA OBTENER POR ID ==========

    @Test
    void testObtenerPorId_conIdNulo() {
        ServiciosException exception = assertThrows(ServiciosException.class, 
            () -> tiposEquipoBean.obtenerPorId(null));
        assertEquals("El ID es obligatorio", exception.getMessage());
    }

    @Test
    void testObtenerPorId_noExiste() {
        when(em.find(TiposEquipo.class, 1L)).thenReturn(null);

        ServiciosException exception = assertThrows(ServiciosException.class, 
            () -> tiposEquipoBean.obtenerPorId(1L));
        assertEquals("No se encontró el tipo de equipo con ID: 1", exception.getMessage());
    }

    @Test
    void testObtenerPorId_exitoso() throws ServiciosException {
        Long id = 1L;
        TiposEquipo entity = new TiposEquipo();
        TiposEquipoDto dto = new TiposEquipoDto();

        when(em.find(TiposEquipo.class, id)).thenReturn(entity);
        when(tiposEquipoMapper.toDto(entity)).thenReturn(dto);

        TiposEquipoDto result = tiposEquipoBean.obtenerPorId(id);

        assertEquals(dto, result);
        verify(em).find(TiposEquipo.class, id);
        verify(tiposEquipoMapper).toDto(entity);
    }

    // ========== TESTS PARA LISTAR TIPOS EQUIPO ==========

    @Test
    void testListarTiposEquipo_exitoso() {
        List<TiposEquipo> entities = Collections.singletonList(new TiposEquipo());
        List<TiposEquipoDto> dtos = Collections.singletonList(new TiposEquipoDto());

        @SuppressWarnings("unchecked")
        TypedQuery<TiposEquipo> query = mock(TypedQuery.class);
        when(em.createQuery("SELECT t FROM TiposEquipo t ORDER BY t.nombreTipo ASC", TiposEquipo.class)).thenReturn(query);
        when(query.getResultList()).thenReturn(entities);
        when(tiposEquipoMapper.toDto(entities)).thenReturn(dtos);

        List<TiposEquipoDto> result = tiposEquipoBean.listarTiposEquipo();

        assertEquals(dtos, result);
        verify(query).getResultList();
        verify(tiposEquipoMapper).toDto(entities);
    }

    // ========== TESTS DE MANEJO DE EXCEPCIONES ==========

    @Test
    void testCrearTiposEquipo_errorBaseDatos() {
        TiposEquipoDto dto = new TiposEquipoDto();
        dto.setNombreTipo("NuevoTipo");

        // Mock para nombre único - debe lanzar NoResultException
        @SuppressWarnings("unchecked")
        TypedQuery<TiposEquipo> queryMock = mock(TypedQuery.class);
        when(em.createQuery(anyString(), eq(TiposEquipo.class))).thenReturn(queryMock);
        when(queryMock.setParameter("nombre", "NUEVOTIPO")).thenReturn(queryMock);
        when(queryMock.getSingleResult()).thenThrow(new NoResultException("No existe"));

        // Error en persist
        when(tiposEquipoMapper.toEntity(dto)).thenReturn(new TiposEquipo());
        doThrow(new RuntimeException("Error de BD")).when(em).persist(any());

        ServiciosException exception = assertThrows(ServiciosException.class, 
            () -> tiposEquipoBean.crearTiposEquipo(dto));
        assertEquals("Error al crear el tipo de equipo", exception.getMessage());
    }
}
