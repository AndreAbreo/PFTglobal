package codigocreativo.uy.servidorapp.filtros;

import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerResponseContext;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuditoriaFilterTest {
    @Test
    void testFilterRequest() {
        AuditoriaFilter filter = new AuditoriaFilter();
        ContainerRequestContext reqCtx = mock(ContainerRequestContext.class);
        when(reqCtx.getMethod()).thenReturn("GET");
        when(reqCtx.getUriInfo()).thenReturn(mock(jakarta.ws.rs.core.UriInfo.class));
        when(reqCtx.getUriInfo().getPath()).thenReturn("/api/test");
        when(reqCtx.getHeaderString(anyString())).thenReturn(null);
        assertDoesNotThrow(() -> filter.filter(reqCtx));
    }

    @Test
    void testFilterResponse() {
        AuditoriaFilter filter = new AuditoriaFilter();
        ContainerRequestContext reqCtx = mock(ContainerRequestContext.class);
        ContainerResponseContext respCtx = mock(ContainerResponseContext.class);
        when(respCtx.getStatus()).thenReturn(200);
        assertDoesNotThrow(() -> filter.filter(reqCtx, respCtx));
    }
} 