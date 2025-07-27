package codigocreativo.uy.servidorapp.servicios;

import codigocreativo.uy.servidorapp.dtos.EquipoDto;
import codigocreativo.uy.servidorapp.dtos.InstitucionDto;
import codigocreativo.uy.servidorapp.dtos.UbicacionDto;
import codigocreativo.uy.servidorapp.dtos.dtomappers.EquipoMapper;
import codigocreativo.uy.servidorapp.dtos.dtomappers.UbicacionMapper;
import codigocreativo.uy.servidorapp.entidades.Ubicacion;
import codigocreativo.uy.servidorapp.entidades.Institucion;
import codigocreativo.uy.servidorapp.enumerados.Estados;
import codigocreativo.uy.servidorapp.enumerados.Sectores;
import codigocreativo.uy.servidorapp.excepciones.ServiciosException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class UbicacionBeanTest {

    @Mock
    private EntityManager em;

    @Mock
    private UbicacionMapper ubicacionMapper;

    @Mock
    private EquipoMapper equipoMapper;

    @InjectMocks
    private UbicacionBean ubicacionBean;

    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);

        ubicacionBean = new UbicacionBean(ubicacionMapper, equipoMapper);

        // Inyectar el EntityManager usando reflexión
        Field emField = UbicacionBean.class.getDeclaredField("em");
        emField.setAccessible(true);
        emField.set(ubicacionBean, em);
    }

    // ========== TESTS PARA CREAR UBICACION ==========

    @Test
    void testCrearUbicacion_conDtoNulo() {
        ServiciosException exception = assertThrows(ServiciosException.class, 
            () -> ubicacionBean.crearUbicacion(null));
        assertEquals("La ubicación es obligatoria", exception.getMessage());
    }

    @Test
    void testCrearUbicacion_conNombreNulo() {
        UbicacionDto dto = new UbicacionDto();
        dto.setNombre(null);

        ServiciosException exception = assertThrows(ServiciosException.class, 
            () -> ubicacionBean.crearUbicacion(dto));
        assertEquals("El nombre de la ubicación es obligatorio", exception.getMessage());
    }

    @Test
    void testCrearUbicacion_conNombreVacio() {
        UbicacionDto dto = new UbicacionDto();
        dto.setNombre("");

        ServiciosException exception = assertThrows(ServiciosException.class, 
            () -> ubicacionBean.crearUbicacion(dto));
        assertEquals("El nombre de la ubicación es obligatorio", exception.getMessage());
    }

    @Test
    void testCrearUbicacion_conSectorNulo() {
        UbicacionDto dto = new UbicacionDto();
        dto.setNombre("UbicacionTest");
        dto.setSector(null);

        ServiciosException exception = assertThrows(ServiciosException.class, 
            () -> ubicacionBean.crearUbicacion(dto));
        assertEquals("El sector de la ubicación es obligatorio", exception.getMessage());
    }

    @Test
    void testCrearUbicacion_conInstitucionNula() {
        UbicacionDto dto = new UbicacionDto();
        dto.setNombre("UbicacionTest");
        dto.setSector("CENTRO");
        dto.setIdInstitucion(null);

        ServiciosException exception = assertThrows(ServiciosException.class, 
            () -> ubicacionBean.crearUbicacion(dto));
        assertEquals("La institución de la ubicación es obligatoria", exception.getMessage());
    }

    @Test
    void testCrearUbicacion_conInstitucionInexistente() {
        UbicacionDto dto = new UbicacionDto();
        dto.setNombre("UbicacionTest");
        dto.setSector("CENTRO");
        dto.setIdInstitucion(new InstitucionDto().setId(999L));

        when(em.find(Institucion.class, 999L)).thenReturn(null);

        ServiciosException exception = assertThrows(ServiciosException.class, 
            () -> ubicacionBean.crearUbicacion(dto));
        assertEquals("La institución especificada no existe", exception.getMessage());
    }

    @Test
    void testCrearUbicacion_conNombreDuplicado() {
        UbicacionDto dto = new UbicacionDto();
        dto.setNombre("UbicacionExistente");
        dto.setSector("CENTRO");
        dto.setIdInstitucion(new InstitucionDto().setId(1L));

        // Mock para institución existente
        Institucion institucion = new Institucion();
        when(em.find(Institucion.class, 1L)).thenReturn(institucion);

        // Mock para nombre duplicado
        @SuppressWarnings("unchecked")
        TypedQuery<Long> queryMock = mock(TypedQuery.class);
        when(em.createQuery(anyString(), eq(Long.class))).thenReturn(queryMock);
        when(queryMock.setParameter("nombre", "ubicacionexistente")).thenReturn(queryMock);
        when(queryMock.getSingleResult()).thenReturn(1L); // Existe

        ServiciosException exception = assertThrows(ServiciosException.class, 
            () -> ubicacionBean.crearUbicacion(dto));
        assertEquals("Ya existe una ubicación con el nombre: UbicacionExistente", exception.getMessage());
    }

    @Test
    void testCrearUbicacion_exitoso() {
        UbicacionDto dto = new UbicacionDto();
        dto.setNombre("NuevaUbicacion");
        dto.setSector("CENTRO");
        dto.setIdInstitucion(new InstitucionDto().setId(1L));
        Ubicacion entity = new Ubicacion();

        // Mock para institución existente
        Institucion institucion = new Institucion();
        when(em.find(Institucion.class, 1L)).thenReturn(institucion);

        // Mock para nombre único
        @SuppressWarnings("unchecked")
        TypedQuery<Long> queryMock = mock(TypedQuery.class);
        when(em.createQuery(anyString(), eq(Long.class))).thenReturn(queryMock);
        when(queryMock.setParameter("nombre", "nuevaubicacion")).thenReturn(queryMock);
        when(queryMock.getSingleResult()).thenReturn(0L); // No existe

        when(ubicacionMapper.toEntity(dto)).thenReturn(entity);

        assertDoesNotThrow(() -> ubicacionBean.crearUbicacion(dto));

        assertEquals(Estados.ACTIVO, dto.getEstado());
        verify(em, times(1)).persist(entity);
        verify(em, times(1)).flush();
    }

    // ========== TESTS PARA MODIFICAR UBICACION ==========

    @Test
    void testModificarUbicacion_conDtoNulo() {
        ServiciosException exception = assertThrows(ServiciosException.class, 
            () -> ubicacionBean.modificarUbicacion(null));
        assertEquals("La ubicación es obligatoria", exception.getMessage());
    }

    @Test
    void testModificarUbicacion_conIdNulo() {
        UbicacionDto dto = new UbicacionDto();
        dto.setNombre("Ubicacion");

        ServiciosException exception = assertThrows(ServiciosException.class, 
            () -> ubicacionBean.modificarUbicacion(dto));
        assertEquals("El ID de la ubicación es obligatorio para modificar", exception.getMessage());
    }

    @Test
    void testModificarUbicacion_noExiste() {
        UbicacionDto dto = new UbicacionDto();
        dto.setId(1L);
        dto.setNombre("Ubicacion");

        when(em.find(Ubicacion.class, 1L)).thenReturn(null);

        ServiciosException exception = assertThrows(ServiciosException.class, 
            () -> ubicacionBean.modificarUbicacion(dto));
        assertEquals("No se encontró la ubicación con ID: 1", exception.getMessage());
    }

    @Test
    void testModificarUbicacion_exitoso() {
        UbicacionDto dto = new UbicacionDto();
        dto.setId(1L);
        dto.setNombre("UbicacionModificada");
        dto.setSector("SUR");
        dto.setIdInstitucion(new InstitucionDto().setId(1L));
        dto.setEstado(Estados.INACTIVO);
        
        Ubicacion entity = new Ubicacion();
        entity.setId(1L);

        // Mock para ubicación existente
        when(em.find(Ubicacion.class, 1L)).thenReturn(entity);

        // Mock para institución existente
        Institucion institucion = new Institucion();
        when(em.find(Institucion.class, 1L)).thenReturn(institucion);

        when(ubicacionMapper.toEntity(dto)).thenReturn(entity);

        assertDoesNotThrow(() -> ubicacionBean.modificarUbicacion(dto));

        verify(em, times(1)).merge(entity);
        verify(em, times(1)).flush();
    }

    // ========== TESTS PARA BORRAR UBICACION ==========

    @Test
    void testBorrarUbicacion_conIdNulo() {
        ServiciosException exception = assertThrows(ServiciosException.class, 
            () -> ubicacionBean.borrarUbicacion(null));
        assertEquals("El ID es obligatorio", exception.getMessage());
    }

    @Test
    void testBorrarUbicacion_exitoso() {
        Long id = 1L;

        Query query = mock(Query.class);
        when(em.createQuery("UPDATE Ubicacion ubicacion SET ubicacion.estado = 'INACTIVO' WHERE ubicacion.id = :id")).thenReturn(query);
        when(query.setParameter("id", id)).thenReturn(query);
        when(query.executeUpdate()).thenReturn(1);

        assertDoesNotThrow(() -> ubicacionBean.borrarUbicacion(id));

        verify(query).setParameter("id", id);
        verify(query).executeUpdate();
    }

    @Test
    void testBorrarUbicacion_noExiste() {
        Long id = 1L;

        Query query = mock(Query.class);
        when(em.createQuery("UPDATE Ubicacion ubicacion SET ubicacion.estado = 'INACTIVO' WHERE ubicacion.id = :id")).thenReturn(query);
        when(query.setParameter("id", id)).thenReturn(query);
        when(query.executeUpdate()).thenReturn(0); // No se modificó ninguna fila

        ServiciosException exception = assertThrows(ServiciosException.class, 
            () -> ubicacionBean.borrarUbicacion(id));
        assertEquals("No se encontró la ubicación con ID: 1", exception.getMessage());
    }

    // ========== TESTS PARA MOVER EQUIPO ==========

    @Test
    void testMoverEquipoDeUbicacion_conEquipoNulo() {
        UbicacionDto ubicacionDto = new UbicacionDto();

        ServiciosException exception = assertThrows(ServiciosException.class, 
            () -> ubicacionBean.moverEquipoDeUbicacion(null, ubicacionDto));
        assertEquals("El equipo es obligatorio", exception.getMessage());
    }

    @Test
    void testMoverEquipoDeUbicacion_conUbicacionNula() {
        EquipoDto equipoDto = new EquipoDto();

        ServiciosException exception = assertThrows(ServiciosException.class, 
            () -> ubicacionBean.moverEquipoDeUbicacion(equipoDto, null));
        assertEquals("La ubicación es obligatoria", exception.getMessage());
    }

    @Test
    void testMoverEquipoDeUbicacion_exitoso() throws ServiciosException {
        EquipoDto equipoDto = new EquipoDto();
        UbicacionDto ubicacionDto = new UbicacionDto();

        assertDoesNotThrow(() -> ubicacionBean.moverEquipoDeUbicacion(equipoDto, ubicacionDto));

        verify(equipoMapper).toEntity(eq(equipoDto), any());
        verify(em).merge(any());
        verify(em).flush();
    }

    // ========== TESTS PARA LISTAR UBICACIONES ==========




    // ========== TESTS PARA OBTENER UBICACION POR ID ==========

    @Test
    void testObtenerUbicacionPorId_conIdNulo() {
        ServiciosException exception = assertThrows(ServiciosException.class, 
            () -> ubicacionBean.obtenerUbicacionPorId(null));
        assertEquals("El ID es obligatorio", exception.getMessage());
    }

    @Test
    void testObtenerUbicacionPorId_noExiste() {
        Long id = 1L;
        
        @SuppressWarnings("unchecked")
        TypedQuery<Ubicacion> query = mock(TypedQuery.class);
        when(em.createQuery("SELECT u FROM Ubicacion u WHERE u.id = :id", Ubicacion.class)).thenReturn(query);
        when(query.setParameter("id", id)).thenReturn(query);
        when(query.getSingleResult()).thenReturn(null);

        ServiciosException exception = assertThrows(ServiciosException.class, 
            () -> ubicacionBean.obtenerUbicacionPorId(id));
        assertEquals("No se encontró la ubicación con ID: 1", exception.getMessage());
    }

    @Test
    void testObtenerUbicacionPorId_exitoso() throws ServiciosException {
        Long id = 1L;
        Ubicacion entity = new Ubicacion();
        UbicacionDto dto = new UbicacionDto();

        @SuppressWarnings("unchecked")
        TypedQuery<Ubicacion> query = mock(TypedQuery.class);
        when(em.createQuery("SELECT u FROM Ubicacion u WHERE u.id = :id", Ubicacion.class)).thenReturn(query);
        when(query.setParameter("id", id)).thenReturn(query);
        when(query.getSingleResult()).thenReturn(entity);
        when(ubicacionMapper.toDto(entity)).thenReturn(dto);

        UbicacionDto result = ubicacionBean.obtenerUbicacionPorId(id);

        assertNotNull(result);
        assertEquals(dto, result);
        verify(ubicacionMapper).toDto(entity);
    }

    // ========== TESTS PARA BAJA LOGICA ==========

    @Test
    void testBajaLogicaUbicacion_conDtoNulo() {
        ServiciosException exception = assertThrows(ServiciosException.class, 
            () -> ubicacionBean.bajaLogicaUbicacion(null));
        assertEquals("La ubicación es obligatoria", exception.getMessage());
    }

    @Test
    void testBajaLogicaUbicacion_exitoso() throws ServiciosException {
        UbicacionDto dto = new UbicacionDto();
        Ubicacion entity = new Ubicacion();
        dto.setEstado(Estados.ACTIVO);

        when(ubicacionMapper.toEntity(dto)).thenReturn(entity);

        assertDoesNotThrow(() -> ubicacionBean.bajaLogicaUbicacion(dto));

        assertEquals(Estados.INACTIVO, entity.getEstado());
        verify(em).merge(entity);
        verify(em).flush();
    }

    // ========== TESTS DE MANEJO DE EXCEPCIONES ==========

    @Test
    void testCrearUbicacion_errorBaseDatos() {
        UbicacionDto dto = new UbicacionDto();
        dto.setNombre("NuevaUbicacion");
        dto.setSector("CENTRO");
        dto.setIdInstitucion(new InstitucionDto().setId(1L));

        // Mock para institución existente
        Institucion institucion = new Institucion();
        when(em.find(Institucion.class, 1L)).thenReturn(institucion);

        // Mock para nombre único
        @SuppressWarnings("unchecked")
        TypedQuery<Long> queryMock = mock(TypedQuery.class);
        when(em.createQuery(anyString(), eq(Long.class))).thenReturn(queryMock);
        when(queryMock.setParameter("nombre", "nuevaubicacion")).thenReturn(queryMock);
        when(queryMock.getSingleResult()).thenReturn(0L);

        // Error en persist
        when(ubicacionMapper.toEntity(dto)).thenReturn(new Ubicacion());
        doThrow(new RuntimeException("Error de BD")).when(em).persist(any());

        ServiciosException exception = assertThrows(ServiciosException.class, 
            () -> ubicacionBean.crearUbicacion(dto));
        assertEquals("Error al crear la ubicación", exception.getMessage());
    }

    @Test
    void testModificarUbicacion_errorBaseDatos() {
        UbicacionDto dto = new UbicacionDto();
        dto.setId(1L);
        dto.setNombre("UbicacionModificada");

        // Mock para ubicación existente
        when(em.find(Ubicacion.class, 1L)).thenReturn(new Ubicacion());

        // Error en merge
        when(ubicacionMapper.toEntity(dto)).thenReturn(new Ubicacion());
        doThrow(new RuntimeException("Error de BD")).when(em).merge(any());

        ServiciosException exception = assertThrows(ServiciosException.class, 
            () -> ubicacionBean.modificarUbicacion(dto));
        assertEquals("Error al modificar la ubicación", exception.getMessage());
    }

    @Test
    void testBorrarUbicacion_errorBaseDatos() {
        Long id = 1L;
        doThrow(new RuntimeException("Error de BD")).when(em).createQuery(anyString());

        ServiciosException exception = assertThrows(ServiciosException.class, 
            () -> ubicacionBean.borrarUbicacion(id));
        assertEquals("Error al borrar la ubicación", exception.getMessage());
    }

    @Test
    void testMoverEquipoDeUbicacion_errorBaseDatos() {
        EquipoDto equipoDto = new EquipoDto();
        UbicacionDto ubicacionDto = new UbicacionDto();
        
        doThrow(new RuntimeException("Error de BD")).when(em).merge(any());

        ServiciosException exception = assertThrows(ServiciosException.class, 
            () -> ubicacionBean.moverEquipoDeUbicacion(equipoDto, ubicacionDto));
        assertEquals("Error al mover el equipo", exception.getMessage());
    }

    @Test
    void testBajaLogicaUbicacion_errorBaseDatos() {
        UbicacionDto dto = new UbicacionDto();
        
        doThrow(new RuntimeException("Error de BD")).when(em).merge(any());

        ServiciosException exception = assertThrows(ServiciosException.class, 
            () -> ubicacionBean.bajaLogicaUbicacion(dto));
        assertEquals("Error al dar de baja la ubicación", exception.getMessage());
    }
}
