package codigocreativo.uy.servidorapp.servicios;

import codigocreativo.uy.servidorapp.dtos.FuncionalidadDto;
import codigocreativo.uy.servidorapp.dtos.dtomappers.FuncionalidadMapper;
import codigocreativo.uy.servidorapp.dtos.dtomappers.CycleAvoidingMappingContext;
import codigocreativo.uy.servidorapp.dtos.PerfilDto;
import codigocreativo.uy.servidorapp.entidades.Funcionalidad;
import codigocreativo.uy.servidorapp.entidades.FuncionalidadesPerfiles;
import codigocreativo.uy.servidorapp.entidades.FuncionalidadesPerfilesId;
import codigocreativo.uy.servidorapp.entidades.Perfil;
import codigocreativo.uy.servidorapp.enumerados.Estados;
import codigocreativo.uy.servidorapp.excepciones.ServiciosException;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.ArrayList;
import java.util.List;

@Stateless
public class FuncionalidadBean implements FuncionalidadRemote {
    private static final String ERROR_FUNCIONALIDAD_NO_ENCONTRADA = "Funcionalidad no encontrada con ID: ";
    private static final String ERROR_FUNCIONALIDAD_CON_PERFILES = "No se puede eliminar la funcionalidad porque tiene perfiles asociados";
    
    @PersistenceContext(unitName = "default")
    private EntityManager em;
    private final FuncionalidadMapper funcionalidadMapper;

    @Inject
    public FuncionalidadBean(FuncionalidadMapper funcionalidadMapper) {
        this.funcionalidadMapper = funcionalidadMapper;
    }

    @Override
    public List<FuncionalidadDto> obtenerTodas() {
        return funcionalidadMapper.toDto(
                em.createQuery("SELECT f FROM Funcionalidad f ORDER BY f.ruta", Funcionalidad.class).getResultList(),
                new CycleAvoidingMappingContext()
        );
    }

    @Override
    public FuncionalidadDto crear(FuncionalidadDto funcionalidadDto) {
        if (funcionalidadDto.getPerfiles() == null) {
            funcionalidadDto.setPerfiles(new ArrayList<>()); // Inicializar lista vacía si no hay perfiles
        }

        Funcionalidad funcionalidad = funcionalidadMapper.toEntity(funcionalidadDto, new CycleAvoidingMappingContext());
        em.persist(funcionalidad);  // Usamos EntityManager para guardar
        em.flush();  // Aseguramos que se guarde la información en la base de datos
        return funcionalidadMapper.toDto(funcionalidad, new CycleAvoidingMappingContext());
    }

    @Override
    public FuncionalidadDto actualizar(FuncionalidadDto funcionalidadDto) {
        Funcionalidad funcionalidad = funcionalidadMapper.toEntity(funcionalidadDto, new CycleAvoidingMappingContext());

        Funcionalidad funcionalidadExistente = em.find(Funcionalidad.class, funcionalidad.getId());
        if (funcionalidadExistente == null) {
            return null; // Devuelve null si no se encuentra la funcionalidad
        }

        List<FuncionalidadesPerfiles> funcionalidadesPerfilesNuevas = new ArrayList<>();
        for (PerfilDto perfilDto : funcionalidadDto.getPerfiles()) {
            Perfil perfil = em.find(Perfil.class, perfilDto.getId());
            if (perfil == null) {
                continue; // Saltar si el perfil no es encontrado
            }
            FuncionalidadesPerfilesId id = new FuncionalidadesPerfilesId(funcionalidad.getId(), perfil.getId());
            FuncionalidadesPerfiles fp = new FuncionalidadesPerfiles(id, funcionalidad, perfil);
            funcionalidadesPerfilesNuevas.add(fp);
        }

        funcionalidad.setFuncionalidadesPerfiles(funcionalidadesPerfilesNuevas);

        em.merge(funcionalidad);
        em.flush();

        return funcionalidadMapper.toDto(funcionalidad, new CycleAvoidingMappingContext());
    }

    @Override
    public void eliminar(Long id) throws ServiciosException {

        if (id == null) {
            throw new ServiciosException("El ID de la funcionalidad es obligatorio para la eliminación");
        }

        Funcionalidad funcionalidad = em.find(Funcionalidad.class, id);
        if (funcionalidad == null) {
            throw new ServiciosException(ERROR_FUNCIONALIDAD_NO_ENCONTRADA + id);
        }

        funcionalidad.setEstado(Estados.INACTIVO);
        em.merge(funcionalidad);
        em.flush();
    }

    @Override
    public FuncionalidadDto buscarPorId(Long id) {
        Funcionalidad funcionalidad = em.find(Funcionalidad.class, id);
        return funcionalidadMapper.toDto(funcionalidad, new CycleAvoidingMappingContext());
    }
}