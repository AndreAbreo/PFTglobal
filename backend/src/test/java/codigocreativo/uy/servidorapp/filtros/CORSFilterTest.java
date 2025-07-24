package codigocreativo.uy.servidorapp.filtros;

import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerResponseContext;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import jakarta.ws.rs.core.MultivaluedMap;

class CORSFilterTest {
    @Test
    @SuppressWarnings("unchecked")
    void testFilterAddsCORSHeaders() {
        CORSFilter filter = new CORSFilter();
        ContainerRequestContext reqCtx = mock(ContainerRequestContext.class);
        ContainerResponseContext respCtx = mock(ContainerResponseContext.class);
        MultivaluedMap<String, String> headers = new org.glassfish.jersey.internal.util.collection.MultivaluedStringMap();
        when(respCtx.getHeaders()).thenReturn((MultivaluedMap<String, Object>) (MultivaluedMap<?, ?>) headers);
        assertDoesNotThrow(() -> filter.filter(reqCtx, respCtx));
        assertEquals("*", headers.getFirst("Access-Control-Allow-Origin"));
        assertEquals("GET, POST, PUT, DELETE, OPTIONS, HEAD", headers.getFirst("Access-Control-Allow-Methods"));
        assertEquals("origin, content-type, accept, authorization", headers.getFirst("Access-Control-Allow-Headers"));
        assertEquals("true", headers.getFirst("Access-Control-Allow-Credentials"));
    }
} 