package codigocreativo.uy.servidorapp.filtros;

import java.security.Key;
import java.util.Base64;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;
import java.util.logging.Level;

import jakarta.annotation.Priority;
import jakarta.ejb.EJB;
import jakarta.ws.rs.Priorities;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;
import codigocreativo.uy.servidorapp.servicios.FuncionalidadRemote;
import codigocreativo.uy.servidorapp.dtos.FuncionalidadDto;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Provider
@Priority(Priorities.AUTHENTICATION)
public class JwtTokenFilter implements ContainerRequestFilter {

    private static final Logger LOGGER = Logger.getLogger(JwtTokenFilter.class.getName());
    private static final String SECRET_KEY = System.getenv("SECRET_KEY");
    private static final String ERROR_JSON_FORMAT = "{\"error\":\"%s\"}";

    public static class TokenValidationException extends Exception {
        public TokenValidationException(String message) {
            super(message);
        }
    }

    private static final Set<String> PUBLIC_PATHS = Set.of(
        "/usuarios/login",
        "/usuarios/seleccionar",
        "/usuarios/google-login",
        "/usuarios/crear",
        "/api/openapi.json",
        "/api/swagger-ui",
        "/openapi.json",
        "/swagger-ui",
        "/swagger-ui/index.html",
        "/usuarios/listar",
        "/usuarios/modificar-propio-usuario",
        "/perfiles/listar",
        "/equipos/listar",
        "/equipos/listarBajas",
        "/intervenciones/listar",
        "/tipos-equipo/listar",
        "/marcas/listar",
        "/ubicaciones/listar",
        "/modelos/listar",
        "/proveedores/listar",
        "/paises/listar",
        "/funcionalidades/listar",
        "/tipoIntervenciones/listar"
    );

    @EJB
    private FuncionalidadRemote funcionalidadService;

    @Override
    public void filter(ContainerRequestContext requestContext) {
        String path = requestContext.getUriInfo().getPath();
        String method = requestContext.getMethod();
        LOGGER.info("Procesando petición: " + method + " " + path);

        if (isPublicEndpoint(path)) {
            return;
        }

        processAuthenticationAndAuthorization(requestContext, path);
    }

    private void processAuthenticationAndAuthorization(ContainerRequestContext requestContext, String path) {

        String token = extractAndValidateToken(requestContext);
        if (token == null) {
            return; // La respuesta de error ya fue enviada
        }

        try {

            Claims claims = validateToken(token);

            processUserInfo(requestContext, claims, path);

        } catch (Exception e) {
            String errorMsg = "Error al procesar el token: " + e.getMessage();
            LOGGER.log(Level.SEVERE, errorMsg, e);
            requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED)
                .entity(String.format(ERROR_JSON_FORMAT, errorMsg))
                .build());
            return; // Asegurar que el método termina aquí después de abortar
        }
    }

    private void processUserInfo(ContainerRequestContext requestContext, Claims claims, String path) {

        String email = claims.get("email", String.class);
        String perfil = claims.get("perfil", String.class);
        LOGGER.log(Level.INFO, "Token válido para usuario: {0} con perfil: {1}", new Object[]{email, perfil});

        if (!isValidUserInfo(email, perfil)) {
            String errorMsg = "Token inválido: información de usuario incompleta - Email: " + 
                            (email == null ? "null" : email) + 
                            ", Perfil: " + (perfil == null ? "null" : perfil);
            LOGGER.warning(errorMsg);
            requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED)
                .entity(String.format(ERROR_JSON_FORMAT, errorMsg))
                .build());
            return;
        }

        storeUserInfo(requestContext, email, perfil);

        validatePermissions(requestContext, email, perfil, path);
    }

    private void validatePermissions(ContainerRequestContext requestContext, String email, String perfil, String path) {
        try {

            if (!hasPermission(perfil, path)) {
                String errorMsg = getPermissionErrorMessage(path, email, perfil);
                LOGGER.warning(errorMsg);
                requestContext.abortWith(Response.status(Response.Status.FORBIDDEN)
                    .entity(String.format(ERROR_JSON_FORMAT, errorMsg))
                    .build());
            } else {
                LOGGER.log(Level.INFO, "Permisos validados correctamente para el usuario: {0}", email);
            }
        } catch (Exception e) {
            String errorMsg = "Error al verificar permisos: " + e.getMessage();
            LOGGER.log(Level.SEVERE, errorMsg, e);
            requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED)
                .entity(String.format(ERROR_JSON_FORMAT, errorMsg))
                .build());
        }
    }

    private String getPermissionErrorMessage(String path, String email, String perfil) {
        if (path.equals("/usuarios/modificar")) {
            return "No tiene permisos para modificar usuarios. Solo los administradores pueden realizar esta acción.";
        } else if (path.equals("/usuarios/inactivar")) {
            return "No tiene permisos para inactivar usuarios. Solo los administradores y aux administrativos pueden realizar esta acción.";
        } else {
            LOGGER.log(Level.WARNING, "Acceso denegado - Usuario: {0}, Perfil: {1}, Path: {2} - No tiene los permisos necesarios", 
                    new Object[]{email, perfil, path});
            return "Acceso denegado - No tiene los permisos necesarios";
        }
    }

    private String extractAndValidateToken(ContainerRequestContext requestContext) {
        String authorizationHeader = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);
        
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            String errorMsg = "Token no proporcionado o formato inválido - Header: " + 
                            (authorizationHeader == null ? "null" : authorizationHeader);
            LOGGER.warning(errorMsg);
            requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED)
                .entity(String.format(ERROR_JSON_FORMAT, errorMsg))
                .build());
            return null;
        }

        return authorizationHeader.substring("Bearer".length()).trim();
    }

    protected Claims validateToken(String token) throws TokenValidationException {
        try {
            Key key = Keys.hmacShaKeyFor(Base64.getDecoder().decode(SECRET_KEY));
            return Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            String errorMsg = "Token inválido: " + e.getMessage();
            LOGGER.log(Level.SEVERE, errorMsg, e);
            throw new TokenValidationException(errorMsg);
        }
    }

    protected boolean isValidUserInfo(String email, String perfil) {
        return  email != null && !email.isEmpty() &&
                perfil != null && !perfil.isEmpty();
    }

    private void storeUserInfo(ContainerRequestContext requestContext, 
                            String email, 
                            String perfil) {
        LOGGER.info("Almacenando información del usuario en el contexto - Email: " + email + ", Perfil: " + perfil);
        requestContext.setProperty("email", email);
        requestContext.setProperty("perfil", perfil);
        LOGGER.info("Información del usuario almacenada correctamente");
    }

    private boolean isPublicEndpoint(String path) {
        return PUBLIC_PATHS.stream().anyMatch(path::startsWith);
    }

    protected boolean hasPermission(String perfil, String path) {
        if (perfil == null || perfil.isEmpty()) {
            return false;
        }

        String normalizado = path.replaceAll("/\\d+$", "/{id}");

        List<FuncionalidadDto> funcionalidades = funcionalidadService.obtenerTodas();

        if (path.equals("/usuarios/modificar")) {
            return perfil.equals("Administrador") ||
                    perfil.equals("SuperAdmin") ||
                    perfil.equals("Aux Administrativo");
        }


        boolean hasPermission = funcionalidades.stream()
                .anyMatch(funcionalidad -> {
                    boolean pathMatches = normalizado.startsWith(funcionalidad.getRuta());
                    boolean profileMatches = funcionalidad.getPerfiles().stream()
                            .anyMatch(perfilDto -> perfilDto.getNombrePerfil().equals(perfil));
                    return pathMatches && profileMatches;
                });

        if (!hasPermission) {
            LOGGER.log(Level.WARNING, "Permiso denegado - Perfil: {0}, Path: {1}", new Object[]{perfil, path});
        }

        return hasPermission;
    }

}
