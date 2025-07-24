package codigocreativo.uy.servidorapp.filtros;

import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.container.ContainerResponseContext;
import jakarta.ws.rs.container.ContainerResponseFilter;
import jakarta.ws.rs.ext.Provider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Provider
public class AuditoriaFilter implements ContainerRequestFilter, ContainerResponseFilter {
    
    private static final Logger logger = LoggerFactory.getLogger(AuditoriaFilter.class);
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        String timestamp = LocalDateTime.now().format(formatter);
        String method = requestContext.getMethod();
        String uri = requestContext.getUriInfo().getPath();
        String clientIP = requestContext.getHeaderString("X-Forwarded-For");
        if (clientIP == null) {
            clientIP = requestContext.getHeaderString("X-Real-IP");
        }
        String userAgent = requestContext.getHeaderString("User-Agent");

        if (logger.isInfoEnabled()) {
            StringBuilder logMessage = new StringBuilder();
            logMessage.append("=== AUDITORIA DE SOLICITUD ===\n");
            logMessage.append("Timestamp: ").append(timestamp).append("\n");
            logMessage.append("Método: ").append(method).append("\n");
            logMessage.append("URI: ").append(uri).append("\n");
            logMessage.append("IP Cliente: ").append(clientIP).append("\n");
            logMessage.append("User-Agent: ").append(userAgent);

            logger.info(logMessage.toString());
        }
    }

    @Override
    public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext) throws IOException {
        String timestamp = LocalDateTime.now().format(formatter);
        int status = responseContext.getStatus();
        
        if (logger.isInfoEnabled()) {
            StringBuilder logMessage = new StringBuilder();
            logMessage.append("=== AUDITORIA DE RESPUESTA ===\n");
            logMessage.append("Timestamp: ").append(timestamp).append("\n");
            logMessage.append("Status: ").append(status);

            logger.info(logMessage.toString());
        }
    }
} 