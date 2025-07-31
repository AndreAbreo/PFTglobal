package codigocreativo.uy.servidorapp.servicios;

import codigocreativo.uy.servidorapp.dtos.UsuarioDto;
import codigocreativo.uy.servidorapp.dtos.dtomappers.UsuarioMapper;
import codigocreativo.uy.servidorapp.entidades.Usuario;
import codigocreativo.uy.servidorapp.enumerados.Estados;
import codigocreativo.uy.servidorapp.excepciones.ServiciosException;
import com.fabdelgado.ciuy.Validator;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.List;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import jakarta.persistence.TypedQuery;
import codigocreativo.uy.servidorapp.dtos.dtomappers.CycleAvoidingMappingContext;
import java.lang.reflect.Field;
import jakarta.persistence.Query;
import java.time.LocalDate;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.stream.Stream;

class UsuarioBeanTest {
    @Mock EntityManager em;
    @Mock UsuarioMapper usuarioMapper;
    @InjectMocks UsuarioBean usuarioBean;
    private Validator validator;

    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
        Field emField = usuarioBean.getClass().getDeclaredField("em");
        emField.setAccessible(true);
        emField.set(usuarioBean, em);
        validator = new Validator();
    }


    @Test
    void testCrearUsuarioNulo() {
        ServiciosException exception = assertThrows(ServiciosException.class, 
            () -> usuarioBean.crearUsuario(null));
        assertEquals("Usuario nulo", exception.getMessage());
    }

    @Test
    void testCrearUsuarioExitoso() {
        UsuarioDto dto = crearUsuarioDtoValido();

        @SuppressWarnings("unchecked")
        TypedQuery<Usuario> queryMock = mock(TypedQuery.class);
        when(em.createQuery(anyString(), eq(Usuario.class))).thenReturn(queryMock);
        when(queryMock.setParameter(anyString(), any())).thenReturn(queryMock);
        when(queryMock.getSingleResult()).thenThrow(new NoResultException()); // No existe email ni cédula

        when(usuarioMapper.toEntity(any(UsuarioDto.class), any(CycleAvoidingMappingContext.class))).thenReturn(new Usuario());

        assertDoesNotThrow(() -> usuarioBean.crearUsuario(dto));

        assertEquals(Estados.SIN_VALIDAR, dto.getEstado());
        verify(em).merge(any());
    }


    @Test
    void testCrearUsuarioFechaNacimientoNula() {
        UsuarioDto dto = crearUsuarioDtoValido();
        dto.setFechaNacimiento(null);
        
        ServiciosException exception = assertThrows(ServiciosException.class, 
            () -> usuarioBean.crearUsuario(dto));
        assertEquals("La fecha de nacimiento es obligatoria", exception.getMessage());
    }

    @Test
    void testCrearUsuarioMenorDeEdad() {
        UsuarioDto dto = crearUsuarioDtoValido();
        dto.setFechaNacimiento(LocalDate.now().minusYears(17)); // 17 años
        
        ServiciosException exception = assertThrows(ServiciosException.class, 
            () -> usuarioBean.crearUsuario(dto));
        assertEquals("El usuario debe ser mayor de edad (mínimo 18 años)", exception.getMessage());
    }


    @Test
    void testCrearUsuarioEmailNulo() {
        UsuarioDto dto = crearUsuarioDtoValido();
        dto.setEmail(null);
        
        ServiciosException exception = assertThrows(ServiciosException.class, 
            () -> usuarioBean.crearUsuario(dto));
        assertEquals("El email es obligatorio", exception.getMessage());
    }

    @Test
    void testCrearUsuarioEmailVacio() {
        UsuarioDto dto = crearUsuarioDtoValido();
        dto.setEmail("");
        
        ServiciosException exception = assertThrows(ServiciosException.class, 
            () -> usuarioBean.crearUsuario(dto));
        assertEquals("El email es obligatorio", exception.getMessage());
    }

    @Test
    void testCrearUsuarioEmailDuplicado() {
        UsuarioDto dto = crearUsuarioDtoValido();

        @SuppressWarnings("unchecked")
        TypedQuery<Usuario> queryMock = mock(TypedQuery.class);
        when(em.createQuery(anyString(), eq(Usuario.class))).thenReturn(queryMock);
        when(queryMock.setParameter(anyString(), any())).thenReturn(queryMock);
        when(queryMock.getSingleResult()).thenReturn(new Usuario()); // Usuario encontrado
        
        ServiciosException exception = assertThrows(ServiciosException.class, 
            () -> usuarioBean.crearUsuario(dto));
        assertEquals("Ya existe un usuario con el email: " + dto.getEmail(), exception.getMessage());
    }


    @Test
    void testCrearUsuarioCedulaNula() {
        UsuarioDto dto = crearUsuarioDtoValido();
        dto.setCedula(null);

        @SuppressWarnings("unchecked")
        TypedQuery<Usuario> queryMock = mock(TypedQuery.class);
        when(em.createQuery(anyString(), eq(Usuario.class))).thenReturn(queryMock);
        when(queryMock.setParameter(anyString(), any())).thenReturn(queryMock);
        when(queryMock.getSingleResult()).thenThrow(new NoResultException());
        
        ServiciosException exception = assertThrows(ServiciosException.class, 
            () -> usuarioBean.crearUsuario(dto));
        assertEquals("La cédula es obligatoria", exception.getMessage());
    }

    @ParameterizedTest
    @MethodSource("provideInvalidCedulas")
    void testCrearUsuarioCedulaInvalida(String cedula, String expectedMessage) {
        UsuarioDto dto = crearUsuarioDtoValido();
        dto.setCedula(cedula);

        @SuppressWarnings("unchecked")
        TypedQuery<Usuario> queryMock = mock(TypedQuery.class);
        when(em.createQuery(anyString(), eq(Usuario.class))).thenReturn(queryMock);
        when(queryMock.setParameter(anyString(), any())).thenReturn(queryMock);
        when(queryMock.getSingleResult()).thenThrow(new NoResultException());
        
        ServiciosException exception = assertThrows(ServiciosException.class, 
            () -> usuarioBean.crearUsuario(dto));
        assertEquals(expectedMessage, exception.getMessage());
    }

    static Stream<Arguments> provideInvalidCedulas() {
        return Stream.of(
            Arguments.of("", "La cédula es obligatoria"),
            Arguments.of("1234567a", "La cédula debe contener solo números: 1234567a"),
            Arguments.of("123456789", "La cédula no puede tener más de 8 dígitos: 123456789"),
            Arguments.of("1234-567", "La cédula debe contener solo números: 1234-567")
        );
    }


    @Test
    void testCrearUsuarioConDiferentesCedulasValidas() {

        for (int i = 0; i < 3; i++) { // Reducido de 5 a 3 para optimizar
            UsuarioDto dto = crearUsuarioDtoValido();
            String cedulaValida = validator.randomCi();
            dto.setCedula(cedulaValida);

            @SuppressWarnings("unchecked")
            TypedQuery<Usuario> queryMock = mock(TypedQuery.class);
            when(em.createQuery(anyString(), eq(Usuario.class))).thenReturn(queryMock);
            when(queryMock.setParameter(anyString(), any())).thenReturn(queryMock);
            when(queryMock.getSingleResult()).thenThrow(new NoResultException());

            when(usuarioMapper.toEntity(any(UsuarioDto.class), any(CycleAvoidingMappingContext.class))).thenReturn(new Usuario());

            assertTrue(validator.validateCi(cedulaValida), "La cédula generada debería ser válida: " + cedulaValida);

            assertDoesNotThrow(() -> usuarioBean.crearUsuario(dto));

            assertEquals(Estados.SIN_VALIDAR, dto.getEstado());
            verify(em, times(1)).merge(any());

            reset(em, usuarioMapper);
        }
    }


    private UsuarioDto crearUsuarioDtoValido() {
        UsuarioDto dto = new UsuarioDto();
        dto.setId(1L);
        dto.setNombre("Juan");
        dto.setApellido("Pérez");
        dto.setEmail("juan.perez@test.com");
        dto.setCedula(validator.randomCi()); // Usar cédula válida generada por CIUY
        dto.setFechaNacimiento(LocalDate.now().minusYears(25)); // 25 años
        dto.setContrasenia("password123");
        dto.setEstado(Estados.ACTIVO);
        return dto;
    }


    @Test
    void testModificarUsuario() {
        UsuarioDto dto = new UsuarioDto();
        usuarioBean.modificarUsuario(dto);
        verify(em).merge(any());
        verify(em).flush();
    }

    @Test
    void testEliminarUsuario() {
        UsuarioDto dto = new UsuarioDto();
        dto.setId(1L);
        Query queryMock = mock(Query.class);
        when(em.createQuery(anyString())).thenReturn(queryMock);
        when(queryMock.setParameter(anyString(), any())).thenReturn(queryMock);
        when(queryMock.executeUpdate()).thenReturn(1);
        usuarioBean.eliminarUsuario(dto);
        verify(em).createQuery(anyString());
        verify(em).flush();
    }

    @Test
    void testObtenerUsuario() {
        when(em.find(eq(Usuario.class), anyLong())).thenReturn(new Usuario());
        when(usuarioMapper.toDto(any(Usuario.class), any(CycleAvoidingMappingContext.class))).thenReturn(new UsuarioDto());
        assertNotNull(usuarioBean.obtenerUsuario(1L));
    }

    @Test
    void testObtenerUsuarioDto() {
        @SuppressWarnings("unchecked")
        TypedQuery<Usuario> query = mock(TypedQuery.class);
        when(em.createQuery(anyString(), eq(Usuario.class))).thenReturn(query);
        when(query.setParameter(anyString(), any())).thenReturn(query);
        when(query.getSingleResult()).thenReturn(new Usuario());
        when(usuarioMapper.toDto(any(Usuario.class), any(CycleAvoidingMappingContext.class))).thenReturn(new UsuarioDto());
        assertNotNull(usuarioBean.obtenerUsuarioDto(1L));
    }

    @Test
    void testObtenerUsuarioPorCI() {
        @SuppressWarnings("unchecked")
        TypedQuery<Usuario> query = mock(TypedQuery.class);
        when(em.createQuery(anyString(), eq(Usuario.class))).thenReturn(query);
        when(query.setParameter(anyString(), any())).thenReturn(query);
        when(query.getSingleResult()).thenReturn(new Usuario());
        when(usuarioMapper.toDto(any(Usuario.class), any(CycleAvoidingMappingContext.class))).thenReturn(new UsuarioDto());
        assertNotNull(usuarioBean.obtenerUsuarioPorCI("12345678"));
    }

    @Test
    void testObtenerUsuarios() {
        @SuppressWarnings("unchecked")
        TypedQuery<Usuario> query = mock(TypedQuery.class);
        when(em.createQuery(anyString(), eq(Usuario.class))).thenReturn(query);
        when(query.getResultList()).thenReturn(List.of(new Usuario()));
        when(usuarioMapper.toDto(anyList(), any(CycleAvoidingMappingContext.class))).thenReturn(List.of(new UsuarioDto()));
        assertNotNull(usuarioBean.obtenerUsuarios());
    }

    @Test
    void testObtenerUsuariosFiltrados() {
        @SuppressWarnings("unchecked")
        TypedQuery<Usuario> query = mock(TypedQuery.class);
        when(em.createQuery(anyString(), eq(Usuario.class))).thenReturn(query);
        when(query.setParameter(anyString(), any())).thenReturn(query);
        when(query.getResultList()).thenReturn(List.of(new Usuario()));
        when(usuarioMapper.toDto(anyList(), any(CycleAvoidingMappingContext.class))).thenReturn(List.of(new UsuarioDto()));
        assertNotNull(usuarioBean.obtenerUsuariosFiltrados("filtro", "valor"));
    }

    @Test
    void testObtenerUsuariosPorEstado() {
        @SuppressWarnings("unchecked")
        TypedQuery<Usuario> query = mock(TypedQuery.class);
        when(em.createQuery(anyString(), eq(Usuario.class))).thenReturn(query);
        when(query.setParameter(anyString(), any())).thenReturn(query);
        when(query.getResultList()).thenReturn(List.of(new Usuario()));
        when(usuarioMapper.toDto(anyList(), any(CycleAvoidingMappingContext.class))).thenReturn(List.of(new UsuarioDto()));
        assertNotNull(usuarioBean.obtenerUsuariosPorEstado(Estados.ACTIVO));
    }

    @Test
    void testLoginNoResult() {
        @SuppressWarnings("unchecked")
        TypedQuery<Usuario> query = mock(TypedQuery.class);
        when(em.createQuery(anyString(), eq(Usuario.class))).thenReturn(query);
        when(query.setParameter(anyString(), any())).thenReturn(query);
        when(query.getSingleResult()).thenThrow(new jakarta.persistence.NoResultException());
        assertNull(usuarioBean.login("user", "pass"));
    }

    @Test
    void testFindUserByEmailNoResult() {
        @SuppressWarnings("unchecked")
        TypedQuery<Usuario> query = mock(TypedQuery.class);
        when(em.createQuery(anyString(), eq(Usuario.class))).thenReturn(query);
        when(query.setParameter(anyString(), any())).thenReturn(query);
        when(query.getSingleResult()).thenThrow(new jakarta.persistence.NoResultException());
        assertNull(usuarioBean.findUserByEmail("mail"));
    }

    @Test
    void testHasPermissionNoResult() {
        @SuppressWarnings("unchecked")
        TypedQuery<UsuarioDto> query = mock(TypedQuery.class);
        when(em.createQuery(anyString(), eq(UsuarioDto.class))).thenReturn(query);
        when(query.setParameter(anyString(), any())).thenReturn(query);
        when(query.getSingleResult()).thenThrow(new jakarta.persistence.NoResultException());
        assertFalse(usuarioBean.hasPermission("mail", "role"));
    }

    @Test
    void testObtenerUsuariosFiltrado() {
        @SuppressWarnings("unchecked")
        TypedQuery<Usuario> query = mock(TypedQuery.class);
        when(em.createQuery(anyString(), eq(Usuario.class))).thenReturn(query);
        when(query.setParameter(anyString(), any())).thenReturn(query);
        when(query.getResultList()).thenReturn(List.of(new Usuario()));
        when(usuarioMapper.toDto(anyList(), any(CycleAvoidingMappingContext.class))).thenReturn(List.of(new UsuarioDto()));
        assertNotNull(usuarioBean.obtenerUsuariosFiltrado(Map.of("nombre", "test")));
    }
} 