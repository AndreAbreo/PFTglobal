package codigocreativo.uy.servidorapp.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.module.jaxb.JaxbAnnotationModule;
import jakarta.ws.rs.ext.ContextResolver;
import jakarta.ws.rs.ext.Provider;


@Provider
public class JacksonConfig implements ContextResolver<ObjectMapper> {

    private final ObjectMapper mapper;

    public JacksonConfig() {
        mapper = new ObjectMapper();
        // Registrar el módulo para manejar Java 8 Date/Time
        mapper.registerModule(new JavaTimeModule());
        // Registrar el módulo para manejar anotaciones JAXB
        mapper.registerModule(new JaxbAnnotationModule());
        // Evitar recursión infinita
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    @Override
    public ObjectMapper getContext(Class<?> type) {
        return mapper;
    }
}