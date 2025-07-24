package codigocreativo.uy.servidorapp.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class JacksonConfigTest {
    @Test
    void testGetContext() {
        JacksonConfig config = new JacksonConfig();
        ObjectMapper mapper = config.getContext(Object.class);
        assertNotNull(mapper);
        assertFalse(mapper.isEnabled(com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES));
    }
} 