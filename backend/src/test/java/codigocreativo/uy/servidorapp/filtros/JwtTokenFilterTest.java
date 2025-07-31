package codigocreativo.uy.servidorapp.filtros;

import codigocreativo.uy.servidorapp.servicios.FuncionalidadRemote;
import io.jsonwebtoken.Claims;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.*;

class JwtTokenFilterTest {
    @Mock FuncionalidadRemote funcionalidadService;
    @Mock ContainerRequestContext requestContext;
    JwtTokenFilter filter;

    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
        filter = spy(new JwtTokenFilter());

        var field = JwtTokenFilter.class.getDeclaredField("funcionalidadService");
        field.setAccessible(true);
        field.set(filter, funcionalidadService);

        for (String methodName : new String[]{"validateToken", "isValidUserInfo", "hasPermission"}) {
            var m = JwtTokenFilter.class.getDeclaredMethod(methodName, methodName.equals("validateToken") ? new Class[]{String.class} : new Class[]{String.class, String.class});
            m.setAccessible(true);
        }
    }

    @Test
    void testPublicEndpoint() {
        when(requestContext.getUriInfo()).thenReturn(mock(jakarta.ws.rs.core.UriInfo.class));
        when(requestContext.getUriInfo().getPath()).thenReturn("/usuarios/login");
        filter.filter(requestContext);
        verify(requestContext, never()).abortWith(any());
    }

    @Test
    void testTokenAusente() {
        when(requestContext.getUriInfo()).thenReturn(mock(jakarta.ws.rs.core.UriInfo.class));
        when(requestContext.getUriInfo().getPath()).thenReturn("/api/privado");
        when(requestContext.getHeaderString(anyString())).thenReturn(null);
        filter.filter(requestContext);
        verify(requestContext).abortWith(argThat(resp -> resp.getStatus() == Response.Status.UNAUTHORIZED.getStatusCode()));
    }

    @Test
    void testTokenInvalido() throws Exception {
        when(requestContext.getUriInfo()).thenReturn(mock(jakarta.ws.rs.core.UriInfo.class));
        when(requestContext.getUriInfo().getPath()).thenReturn("/api/privado");
        when(requestContext.getHeaderString(anyString())).thenReturn("Bearer tokenInvalido");
        doThrow(new JwtTokenFilter.TokenValidationException("Token inválido")).when(filter).validateToken(anyString());
        filter.filter(requestContext);
        verify(requestContext).abortWith(argThat(resp -> resp.getStatus() == Response.Status.UNAUTHORIZED.getStatusCode()));
    }

    @Test
    void testTokenSinEmailOPerfil() throws Exception {
        when(requestContext.getUriInfo()).thenReturn(mock(jakarta.ws.rs.core.UriInfo.class));
        when(requestContext.getUriInfo().getPath()).thenReturn("/api/privado");
        when(requestContext.getHeaderString(anyString())).thenReturn("Bearer tokenValido");
        Claims claims = mock(Claims.class);
        when(claims.get("email", String.class)).thenReturn(null);
        when(claims.get("perfil", String.class)).thenReturn(null);
        doReturn(claims).when(filter).validateToken(anyString());
        filter.filter(requestContext);
        verify(requestContext).abortWith(argThat(resp -> resp.getStatus() == Response.Status.UNAUTHORIZED.getStatusCode()));
    }

    @Test
    void testTokenSinPermiso() throws Exception {
        when(requestContext.getUriInfo()).thenReturn(mock(jakarta.ws.rs.core.UriInfo.class));
        when(requestContext.getUriInfo().getPath()).thenReturn("/api/privado");
        when(requestContext.getHeaderString(anyString())).thenReturn("Bearer tokenValido");
        Claims claims = mock(Claims.class);
        when(claims.get("email", String.class)).thenReturn("user@mail.com");
        when(claims.get("perfil", String.class)).thenReturn("Usuario");
        doReturn(claims).when(filter).validateToken(anyString());
        doReturn(true).when(filter).isValidUserInfo(anyString(), anyString());
        doReturn(false).when(filter).hasPermission(anyString(), anyString());
        filter.filter(requestContext);
        verify(requestContext).abortWith(argThat(resp -> resp.getStatus() == Response.Status.FORBIDDEN.getStatusCode()));
    }

    @Test
    void testTokenConPermiso() throws Exception {
        when(requestContext.getUriInfo()).thenReturn(mock(jakarta.ws.rs.core.UriInfo.class));
        when(requestContext.getUriInfo().getPath()).thenReturn("/api/privado");
        when(requestContext.getHeaderString(anyString())).thenReturn("Bearer tokenValido");
        Claims claims = mock(Claims.class);
        when(claims.get("email", String.class)).thenReturn("admin@mail.com");
        when(claims.get("perfil", String.class)).thenReturn("Administrador");
        doReturn(claims).when(filter).validateToken(anyString());
        doReturn(true).when(filter).isValidUserInfo(anyString(), anyString());
        doReturn(true).when(filter).hasPermission(anyString(), anyString());
        filter.filter(requestContext);
        verify(requestContext, never()).abortWith(any());
    }

    @Test
    void testPermissionCheckThrowsException() throws Exception {

        when(requestContext.getUriInfo()).thenReturn(mock(jakarta.ws.rs.core.UriInfo.class));
        when(requestContext.getUriInfo().getPath()).thenReturn("/api/privado");
        when(requestContext.getHeaderString(anyString())).thenReturn("Bearer tokenValido");
        Claims claims = mock(Claims.class);
        when(claims.get("email", String.class)).thenReturn("admin@mail.com");
        when(claims.get("perfil", String.class)).thenReturn("Administrador");
        doReturn(claims).when(filter).validateToken(anyString());
        doReturn(true).when(filter).isValidUserInfo(anyString(), anyString());

        doThrow(new RuntimeException("DB error")).when(filter).hasPermission(anyString(), anyString());

        filter.filter(requestContext);

        verify(requestContext).abortWith(argThat(resp -> resp.getStatus() == Response.Status.UNAUTHORIZED.getStatusCode()));
    }
} 