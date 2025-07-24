package codigocreativo.uy.servidorapp.servicios;

import codigocreativo.uy.servidorapp.entidades.UsuariosTelefono;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

 class UsuariosTelefonoBeanTest {

    @Mock
    private EntityManager em;

    @InjectMocks
    private UsuariosTelefonoBean usuariosTelefonoBean;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCrearUsuariosTelefono() {
        UsuariosTelefono usuariosTelefono = new UsuariosTelefono();
        usuariosTelefonoBean.crearUsuariosTelefono(usuariosTelefono);
        verify(em, times(1)).persist(usuariosTelefono);
        verify(em, times(1)).flush();
    }

    @Test
    void testModificarUsuariosTelefono() {
        UsuariosTelefono usuariosTelefono = new UsuariosTelefono();
        usuariosTelefonoBean.modificarUsuariosTelefono(usuariosTelefono);
        verify(em, times(1)).merge(usuariosTelefono);
        verify(em, times(1)).flush();
    }

    @Test
    void testObtenerUsuarioTelefono() {
        Long id = 1L;
        UsuariosTelefono usuariosTelefono = new UsuariosTelefono();
        when(em.find(UsuariosTelefono.class, id)).thenReturn(usuariosTelefono);

        usuariosTelefonoBean.obtenerUsuarioTelefono(id);
        verify(em, times(1)).find(UsuariosTelefono.class, id);
    }

    @Test
     void testObtenerusuariosTelefono() {
        List<UsuariosTelefono> expectedList = new ArrayList<>();
        @SuppressWarnings("unchecked")
        TypedQuery<UsuariosTelefono> query = mock(TypedQuery.class);

        when(em.createQuery("SELECT UsuariosTelefono FROM UsuariosTelefono usuariosTelefono", UsuariosTelefono.class)).thenReturn(query);
        when(query.getResultList()).thenReturn(expectedList);

        List<UsuariosTelefono> result = usuariosTelefonoBean.obtenerusuariosTelefono();
        verify(em, times(1)).createQuery("SELECT UsuariosTelefono FROM UsuariosTelefono usuariosTelefono", UsuariosTelefono.class);
        verify(query, times(1)).getResultList();

        assertEquals(expectedList, result);
    }

    @Test
    void testEliminarTelefono() {
        UsuariosTelefono usuariosTelefono = new UsuariosTelefono();
        UsuariosTelefono mergedTelefono = new UsuariosTelefono();

        when(em.merge(usuariosTelefono)).thenReturn(mergedTelefono);

        usuariosTelefonoBean.eliminarTelefono(usuariosTelefono);
        verify(em, times(1)).merge(usuariosTelefono);
        verify(em, times(1)).remove(mergedTelefono);
    }
}
