package codigocreativo.uy.servidorapp.ws;

import codigocreativo.uy.servidorapp.filtros.CORSFilter;
import codigocreativo.uy.servidorapp.config.JacksonConfig;
import codigocreativo.uy.servidorapp.filtros.JwtTokenFilter;
import codigocreativo.uy.servidorapp.filtros.AuditoriaFilter;
import codigocreativo.uy.servidorapp.jwt.LdapService;
import io.swagger.v3.jaxrs2.integration.resources.OpenApiResource;
import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;

import java.util.HashSet;
import java.util.Set;

@ApplicationPath("/api")
public class MiApp extends Application {
    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new HashSet<>();
        
        // Recursos o web services
        resources.add(UbicacionesResource.class);
        resources.add(ModeloResource.class);
        resources.add(IntervencionesResource.class);
        resources.add(TipoEquipoResource.class);
        resources.add(EquipoResource.class);
        resources.add(PaisesResource.class);
        resources.add(FuncionalidadResource.class);
        resources.add(PerfilResource.class);
        resources.add(MarcaResource.class);
        resources.add(UsuarioResource.class);
        resources.add(TipoIntervencionesResource.class);
        resources.add(ProveedoresResource.class);

        // OpenApi
        resources.add(OpenApiResource.class);
        
        // Filtros y configuraciones
        resources.add(AuditoriaFilter.class);
        resources.add(JwtTokenFilter.class);
        resources.add(CORSFilter.class);
        resources.add(JacksonConfig.class);

        resources.add(LdapTestResource.class);
        resources.add(LdapService.class);
        
        return resources;
    }
}
