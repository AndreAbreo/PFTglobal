package codigocreativo.uy.servidorapp.servicios;

import codigocreativo.uy.servidorapp.dtos.dtomappers.ModelosEquipoMapper;
import codigocreativo.uy.servidorapp.dtos.ModelosEquipoDto;
import codigocreativo.uy.servidorapp.dtos.MarcasModeloDto;
import codigocreativo.uy.servidorapp.entidades.ModelosEquipo;
import codigocreativo.uy.servidorapp.entidades.MarcasModelo;
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
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class ModelosEquipoBeanTest {

    @Mock
    private EntityManager em;

    @Mock
    private ModelosEquipoMapper modelosEquipoMapper;

    @InjectMocks
    private ModelosEquipoBean modelosEquipoBean;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
        modelosEquipoBean = new ModelosEquipoBean(modelosEquipoMapper);

        // Utiliza reflexión para asignar el EntityManager privado
        Field emField = ModelosEquipoBean.class.getDeclaredField("em");
        emField.setAccessible(true);
        emField.set(modelosEquipoBean, em);
    }

    private MarcasModeloDto crearMarcaDto(Long id) {
        MarcasModeloDto marca = new MarcasModeloDto();
        marca.setId(id);
        return marca;
    }

    // ========== TESTS PARA CREAR MODELOS ==========

    @Test
    void testCrearModelos_conDtoNulo() {
        ServiciosException exception = assertThrows(ServiciosException.class, 
            () -> modelosEquipoBean.crearModelos(null));
        assertEquals("El modelo es obligatorio", exception.getMessage());
    }

    @Test
    void testCrearModelos_conNombreNulo() {
        ModelosEquipoDto dto = new ModelosEquipoDto();
        dto.setNombre(null);

        ServiciosException exception = assertThrows(ServiciosException.class, 
            () -> modelosEquipoBean.crearModelos(dto));
        assertEquals("El nombre del modelo es obligatorio", exception.getMessage());
    }

    @Test
    void testCrearModelos_conNombreVacio() {
        ModelosEquipoDto dto = new ModelosEquipoDto();
        dto.setNombre("");

        ServiciosException exception = assertThrows(ServiciosException.class, 
            () -> modelosEquipoBean.crearModelos(dto));
        assertEquals("El nombre del modelo es obligatorio", exception.getMessage());
    }

    @Test
    void testCrearModelos_conMarcaNula() {
        ModelosEquipoDto dto = new ModelosEquipoDto();
        dto.setNombre("ModeloTest");
        dto.setIdMarca(null);

        ServiciosException exception = assertThrows(ServiciosException.class, 
            () -> modelosEquipoBean.crearModelos(dto));
        assertEquals("La marca del modelo es obligatoria", exception.getMessage());
    }

    @Test
    void testCrearModelos_conMarcaInexistente() {
        ModelosEquipoDto dto = new ModelosEquipoDto();
        dto.setNombre("ModeloTest");
        MarcasModeloDto marca = new MarcasModeloDto();
        marca.setId(999L);
        dto.setIdMarca(marca);

        when(em.find(MarcasModelo.class, 999L)).thenReturn(null);

        ServiciosException exception = assertThrows(ServiciosException.class, 
            () -> modelosEquipoBean.crearModelos(dto));
        assertEquals("La marca especificada no existe", exception.getMessage());
    }

    @Test
    void testCrearModelos_conNombreDuplicado() {
        ModelosEquipoDto dto = new ModelosEquipoDto();
        dto.setNombre("ModeloExistente");
        dto.setIdMarca(crearMarcaDto(1L));

        // Mock para marca existente
        MarcasModelo marca = new MarcasModelo();
        when(em.find(MarcasModelo.class, 1L)).thenReturn(marca);

        // Mock para nombre duplicado
        @SuppressWarnings("unchecked")
        TypedQuery<ModelosEquipo> queryMock = mock(TypedQuery.class);
        when(em.createQuery(anyString(), eq(ModelosEquipo.class))).thenReturn(queryMock);
        when(queryMock.setParameter("nombre", "MODELOEXISTENTE")).thenReturn(queryMock);
        when(queryMock.getSingleResult()).thenReturn(new ModelosEquipo()); // Existe

        ServiciosException exception = assertThrows(ServiciosException.class, 
            () -> modelosEquipoBean.crearModelos(dto));
        assertEquals("Ya existe un modelo con el nombre: ModeloExistente", exception.getMessage());
    }

    @Test
    void testCrearModelos_exitoso() throws ServiciosException {
        ModelosEquipoDto dto = new ModelosEquipoDto();
        dto.setNombre("NuevoModelo");
        dto.setIdMarca(crearMarcaDto(1L));
        ModelosEquipo entity = new ModelosEquipo();

        // Mock para marca existente
        MarcasModelo marca = new MarcasModelo();
        when(em.find(MarcasModelo.class, 1L)).thenReturn(marca);

        // Mock para nombre único - debe lanzar NoResultException
        @SuppressWarnings("unchecked")
        TypedQuery<ModelosEquipo> queryMock = mock(TypedQuery.class);
        when(em.createQuery(anyString(), eq(ModelosEquipo.class))).thenReturn(queryMock);
        when(queryMock.setParameter("nombre", "NUEVOMODELO")).thenReturn(queryMock);
        when(queryMock.getSingleResult()).thenThrow(new jakarta.persistence.NoResultException("No result"));

        when(modelosEquipoMapper.toEntity(dto)).thenReturn(entity);

        assertDoesNotThrow(() -> modelosEquipoBean.crearModelos(dto));
        
        assertEquals(Estados.ACTIVO, dto.getEstado());
        verify(em, times(1)).persist(entity);
        verify(em, times(1)).flush();
    }

    // ========== TESTS PARA MODIFICAR MODELOS ==========

    @Test
    void testModificarModelos_conDtoNulo() {
        ServiciosException exception = assertThrows(ServiciosException.class, 
            () -> modelosEquipoBean.modificarModelos(null));
        assertEquals("El modelo es obligatorio", exception.getMessage());
    }

    @Test
    void testModificarModelos_conIdNulo() {
        ModelosEquipoDto dto = new ModelosEquipoDto();
        dto.setNombre("Modelo");

        ServiciosException exception = assertThrows(ServiciosException.class, 
            () -> modelosEquipoBean.modificarModelos(dto));
        assertEquals("El ID del modelo es obligatorio para modificar", exception.getMessage());
    }

    @Test
    void testModificarModelos_noExiste() {
        ModelosEquipoDto dto = new ModelosEquipoDto();
        dto.setId(1L);
        dto.setNombre("Modelo");

        when(em.find(ModelosEquipo.class, 1L)).thenReturn(null);

        ServiciosException exception = assertThrows(ServiciosException.class, 
            () -> modelosEquipoBean.modificarModelos(dto));
        assertEquals("No se encontró el modelo con ID: 1", exception.getMessage());
    }

    @Test
    void testModificarModelos_nombreDuplicado() {
        ModelosEquipoDto dto = new ModelosEquipoDto();
        dto.setId(1L);
        dto.setNombre("ModeloNuevo");
        dto.setIdMarca(crearMarcaDto(1L));

        // Mock para modelo existente
        ModelosEquipo actual = new ModelosEquipo();
        actual.setId(1L);
        actual.setNombre("ModeloViejo");
        when(em.find(ModelosEquipo.class, 1L)).thenReturn(actual);

        // Mock para marca existente
        MarcasModelo marca = new MarcasModelo();
        when(em.find(MarcasModelo.class, 1L)).thenReturn(marca);

        // Mock para nombre duplicado
        @SuppressWarnings("unchecked")
        TypedQuery<ModelosEquipo> queryMock = mock(TypedQuery.class);
        when(em.createQuery(anyString(), eq(ModelosEquipo.class))).thenReturn(queryMock);
        when(queryMock.setParameter("nombre", "MODELONUEVO")).thenReturn(queryMock);
        when(queryMock.setParameter("id", 1L)).thenReturn(queryMock);
        when(queryMock.getSingleResult()).thenReturn(new ModelosEquipo()); // Existe otro con ese nombre

        ServiciosException exception = assertThrows(ServiciosException.class, 
            () -> modelosEquipoBean.modificarModelos(dto));
        assertEquals("Ya existe otro modelo con el nombre: ModeloNuevo", exception.getMessage());
    }

    @Test
    void testModificarModelos_exitoso() throws ServiciosException {
        ModelosEquipoDto dto = new ModelosEquipoDto();
        dto.setId(1L);
        dto.setNombre("ModeloModificado");
        dto.setIdMarca(crearMarcaDto(1L));
        dto.setEstado(Estados.INACTIVO);
        
        ModelosEquipo entity = new ModelosEquipo();
        entity.setId(1L);

        // Mock para modelo existente
        when(em.find(ModelosEquipo.class, 1L)).thenReturn(entity);

        // Mock para marca existente
        MarcasModelo marca = new MarcasModelo();
        when(em.find(MarcasModelo.class, 1L)).thenReturn(marca);

        // Mock para nombre único (o mismo nombre) - debe lanzar NoResultException
        @SuppressWarnings("unchecked")
        TypedQuery<ModelosEquipo> queryMock = mock(TypedQuery.class);
        when(em.createQuery(anyString(), eq(ModelosEquipo.class))).thenReturn(queryMock);
        when(queryMock.setParameter("nombre", "MODELOMODIFICADO")).thenReturn(queryMock);
        when(queryMock.setParameter("id", 1L)).thenReturn(queryMock);
        when(queryMock.getSingleResult()).thenThrow(new jakarta.persistence.NoResultException("No result"));

        when(modelosEquipoMapper.toEntity(dto)).thenReturn(entity);

        assertDoesNotThrow(() -> modelosEquipoBean.modificarModelos(dto));
        
        verify(em, times(1)).merge(entity);
        verify(em, times(1)).flush();
    }

    // ========== TESTS PARA OBTENER MODELOS ==========

    @Test
    void testObtenerModelos_conIdNulo() {
        ServiciosException exception = assertThrows(ServiciosException.class, 
            () -> modelosEquipoBean.obtenerModelos(null));
        assertEquals("El ID es obligatorio", exception.getMessage());
    }

    @Test
    void testObtenerModelos_noExiste() {
        when(em.find(ModelosEquipo.class, 1L)).thenReturn(null);

        ServiciosException exception = assertThrows(ServiciosException.class, 
            () -> modelosEquipoBean.obtenerModelos(1L));
        assertEquals("No se encontró el modelo con ID: 1", exception.getMessage());
    }

    @Test
    void testObtenerModelos_exitoso() throws ServiciosException {
        Long id = 1L;
        ModelosEquipo entity = new ModelosEquipo();
        ModelosEquipoDto dto = new ModelosEquipoDto();

        when(em.find(ModelosEquipo.class, id)).thenReturn(entity);
        when(modelosEquipoMapper.toDto(entity)).thenReturn(dto);

        ModelosEquipoDto result = modelosEquipoBean.obtenerModelos(id);

        assertEquals(dto, result);
        verify(em).find(ModelosEquipo.class, id);
        verify(modelosEquipoMapper).toDto(entity);
    }

    // ========== TESTS PARA LISTAR MODELOS ==========

    @Test
    void testListarModelos_exitoso() {
        List<ModelosEquipo> entities = new ArrayList<>();
        List<ModelosEquipoDto> dtos = new ArrayList<>();
        
        @SuppressWarnings("unchecked")
        TypedQuery<ModelosEquipo> query = mock(TypedQuery.class);
        when(em.createQuery(anyString(), eq(ModelosEquipo.class))).thenReturn(query);
        when(query.getResultList()).thenReturn(entities);
        when(modelosEquipoMapper.toDto(entities)).thenReturn(dtos);

        List<ModelosEquipoDto> result = modelosEquipoBean.listarModelos();

        assertNotNull(result);
        assertEquals(dtos, result);
        verify(query, times(1)).getResultList();
        verify(modelosEquipoMapper).toDto(entities);
    }

    // ========== TESTS PARA ELIMINAR MODELOS ==========

    @Test
    void testEliminarModelos_conIdNulo() {
        ServiciosException exception = assertThrows(ServiciosException.class, 
            () -> modelosEquipoBean.eliminarModelos(null));
        assertEquals("El ID es obligatorio", exception.getMessage());
    }

    @Test
    void testEliminarModelos_exitoso() throws ServiciosException {
        Long id = 1L;
        ModelosEquipo modelo = new ModelosEquipo();
        when(em.find(ModelosEquipo.class, id)).thenReturn(modelo);
        
        @SuppressWarnings("unchecked")
        jakarta.persistence.Query query = mock(jakarta.persistence.Query.class);
        when(em.createQuery(anyString())).thenReturn(query);
        when(query.setParameter("id", id)).thenReturn(query);
        when(query.executeUpdate()).thenReturn(1);

        assertDoesNotThrow(() -> modelosEquipoBean.eliminarModelos(id));

        verify(query, times(1)).executeUpdate();
        verify(em, times(1)).flush();
    }

    @Test
    void testEliminarModelos_noExiste() {
        Long id = 1L;
        when(em.find(ModelosEquipo.class, id)).thenReturn(null);

        ServiciosException exception = assertThrows(ServiciosException.class, 
            () -> modelosEquipoBean.eliminarModelos(id));
        assertEquals("Modelo no encontrado", exception.getMessage());
    }

    // ========== TESTS DE MANEJO DE EXCEPCIONES ==========

    @Test
    void testCrearModelos_errorBaseDatos() {
        ModelosEquipoDto dto = new ModelosEquipoDto();
        dto.setNombre("NuevoModelo");
        dto.setIdMarca(crearMarcaDto(1L));

        // Mock para marca existente
        MarcasModelo marca = new MarcasModelo();
        when(em.find(MarcasModelo.class, 1L)).thenReturn(marca);

        // Mock para nombre único
        @SuppressWarnings("unchecked")
        TypedQuery<Long> queryMock = mock(TypedQuery.class);
        when(em.createQuery(anyString(), eq(Long.class))).thenReturn(queryMock);
        when(queryMock.setParameter("nombre", "nuevomodelo")).thenReturn(queryMock);
        when(queryMock.getSingleResult()).thenReturn(0L);

        // Error en persist
        when(modelosEquipoMapper.toEntity(dto)).thenReturn(new ModelosEquipo());
        doThrow(new RuntimeException("Error de BD")).when(em).persist(any());

        ServiciosException exception = assertThrows(ServiciosException.class, 
            () -> modelosEquipoBean.crearModelos(dto));
        assertEquals("Error al crear el modelo", exception.getMessage());
    }
}
