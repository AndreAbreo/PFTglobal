package codigocreativo.uy.servidorapp.jwt;

import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class JwtServiceTest {

    private JwtService jwtService;

    @BeforeEach
    void setUp() {
        jwtService = new JwtService();
        // Usamos una clave segura para los tests
    }

    @Test
    void testGenerateAndParseTokenSuccessfully() {
        String email = "test@example.com";
        String perfil = "Admin";

        String token = jwtService.generateToken(email, perfil);
        assertNotNull(token);

        Claims claims = jwtService.parseToken(token);
        assertEquals(email, claims.getSubject());
        assertEquals(email, claims.get("email", String.class));
        assertEquals(perfil, claims.get("perfil", String.class));
    }

    @Test
    void testGenerateTokenReturnsNullOnException() {
        // Jwts.builder() lanza una excepción si el subject es nulo.
        // El método debe capturarla y devolver null.
        String token = jwtService.generateToken(null, "Admin");
        assertNull(token);
    }

    @Test
    void testParseInvalidTokenString() {
        String invalidToken = "esto.no.es.un.token";
        assertThrows(io.jsonwebtoken.MalformedJwtException.class, () -> jwtService.parseToken(invalidToken));
    }
} 