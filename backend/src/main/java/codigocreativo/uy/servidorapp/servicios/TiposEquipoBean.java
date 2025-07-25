package codigocreativo.uy.servidorapp.servicios;

import codigocreativo.uy.servidorapp.dtos.TiposEquipoDto;
import codigocreativo.uy.servidorapp.dtos.dtomappers.TiposEquipoMapper;
import codigocreativo.uy.servidorapp.entidades.TiposEquipo;
import codigocreativo.uy.servidorapp.enumerados.Estados;
import codigocreativo.uy.servidorapp.excepciones.ServiciosException;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.NoResultException;

import java.util.List;

@Stateless
public class TiposEquipoBean implements TiposEquipoRemote{
    @PersistenceContext (unitName = "default")
    private EntityManager em;
    private final TiposEquipoMapper tiposEquipoMapper;

    @Inject
    public TiposEquipoBean(TiposEquipoMapper tiposEquipoMapper) {
        this.tiposEquipoMapper = tiposEquipoMapper;
    }

    @Override
    public void crearTiposEquipo(TiposEquipoDto tiposEquipo) throws ServiciosException {
        // Validar que el DTO no sea nulo
        if (tiposEquipo == null) {
            throw new ServiciosException("El tipo de equipo es obligatorio");
        }
        
        // Validar que el nombre no sea nulo ni vacío
        if (tiposEquipo.getNombreTipo() == null || tiposEquipo.getNombreTipo().trim().isEmpty()) {
            throw new ServiciosException("El nombre del tipo de equipo es obligatorio");
        }
        
        // Validar que no exista un tipo con el mismo nombre (case-insensitive)
        validarNombreUnico(tiposEquipo.getNombreTipo().trim());
        
        // Estado por defecto
        tiposEquipo.setEstado(Estados.ACTIVO);
        
        try {
            TiposEquipo tiposEquipoEntity = tiposEquipoMapper.toEntity(tiposEquipo);
            em.persist(tiposEquipoEntity);
            em.flush();
        } catch (Exception e) {
            throw new ServiciosException("Error al crear el tipo de equipo");
        }
    }

    @Override
    public void modificarTiposEquipo(TiposEquipoDto tiposEquipo) throws ServiciosException {
        // Validar que el DTO no sea nulo
        if (tiposEquipo == null) {
            throw new ServiciosException("El tipo de equipo es obligatorio");
        }
        
        // Validar que el ID no sea nulo
        if (tiposEquipo.getId() == null) {
            throw new ServiciosException("El ID del tipo de equipo es obligatorio para modificar");
        }
        
        // Verificar que el tipo existe
        TiposEquipo actual = em.find(TiposEquipo.class, tiposEquipo.getId());
        if (actual == null) {
            throw new ServiciosException("No se encontró el tipo de equipo con ID: " + tiposEquipo.getId());
        }
        
        // Validar que el nombre no sea nulo ni vacío
        if (tiposEquipo.getNombreTipo() == null || tiposEquipo.getNombreTipo().trim().isEmpty()) {
            throw new ServiciosException("El nombre del tipo de equipo no puede ser nulo ni vacío");
        }
        
        try {
            // Validar que no exista otro tipo con el mismo nombre (excluyendo el actual)
            validarNombreUnicoParaModificacion(tiposEquipo.getNombreTipo().trim(), tiposEquipo.getId());
            
            TiposEquipo tiposEquipoEntity = tiposEquipoMapper.toEntity(tiposEquipo);
            em.merge(tiposEquipoEntity);
            em.flush();
        } catch (ServiciosException e) {
            // Re-lanzar ServiciosException específicas
            throw e;
        } catch (Exception e) {
            throw new ServiciosException("Error al modificar el tipo de equipo");
        }
    }

    @Override
    public void eliminarTiposEquipo(Long id) throws ServiciosException {
        if (id == null) {
            throw new ServiciosException("El ID es obligatorio");
        }
        
        // Verificar que el tipo existe antes de intentar eliminarlo
        TiposEquipo tiposEquipoEntity = em.find(TiposEquipo.class, id);
        if (tiposEquipoEntity == null) {
            throw new ServiciosException("No se encontró el tipo de equipo con ID: " + id);
        }
        
        // Solo cambia a estado INACTIVO
        tiposEquipoEntity.setEstado("INACTIVO");
        em.merge(tiposEquipoEntity);
        em.flush();
    }

    @Override
    public TiposEquipoDto obtenerPorId(Long id) throws ServiciosException {
        if (id == null) {
            throw new ServiciosException("El ID es obligatorio");
        }
        
        TiposEquipo tiposEquipoEntity = em.find(TiposEquipo.class, id);
        if (tiposEquipoEntity == null) {
            throw new ServiciosException("No se encontró el tipo de equipo con ID: " + id);
        }
        
        return tiposEquipoMapper.toDto(tiposEquipoEntity);
    }

    @Override
    public List<TiposEquipoDto> listarTiposEquipo() {
        List<TiposEquipo> tiposEquipos = em.createQuery("SELECT t FROM TiposEquipo t ORDER BY t.nombreTipo ASC", TiposEquipo.class).getResultList();
        return tiposEquipoMapper.toDto(tiposEquipos);
    }

    @Override
    public List<TiposEquipoDto> filtrarTiposEquipo(String nombre, String estado) {
        Estados estadoEnum = null;
        if (estado != null && !estado.trim().isEmpty()) {
            estadoEnum = Estados.valueOf(estado);
        }

        StringBuilder jpql = new StringBuilder("SELECT t FROM TiposEquipo t WHERE 1=1");
        if (nombre != null && !nombre.trim().isEmpty()) {
            jpql.append(" AND UPPER(t.nombreTipo) LIKE UPPER(:nombre)");
        }
        if (estadoEnum != null) {
            jpql.append(" AND t.estado = :estado");
        }
        jpql.append(" ORDER BY t.nombreTipo ASC");

        var query = em.createQuery(jpql.toString(), TiposEquipo.class);
        if (nombre != null && !nombre.trim().isEmpty()) {
            query.setParameter("nombre", "%" + nombre.trim() + "%");
        }
        if (estadoEnum != null) {
            query.setParameter("estado", estadoEnum.name());
        }

        return tiposEquipoMapper.toDto(query.getResultList());
    }
    
    /**
     * Valida que el nombre del tipo de equipo sea único en la base de datos
     */
    private void validarNombreUnico(String nombre) throws ServiciosException {
        try {
            jakarta.persistence.TypedQuery<TiposEquipo> query = em.createQuery("SELECT t FROM TiposEquipo t WHERE UPPER(t.nombreTipo) = :nombre", TiposEquipo.class);
            if (query != null) {
                query.setParameter("nombre", nombre.toUpperCase());
                query.getSingleResult();
                throw new ServiciosException("Ya existe un tipo de equipo con el nombre: " + nombre);
            }
        } catch (NoResultException e) {
            // El nombre no existe, es válido
        } catch (ServiciosException e) {
            // Re-lanzar ServiciosException específicas
            throw e;
        } catch (Exception e) {
            // Handle null query or other exceptions
            throw new ServiciosException("Error al validar el nombre del tipo de equipo");
        }
    }
    
    /**
     * Valida que el nombre del tipo de equipo sea único para modificaciones (excluyendo el tipo actual)
     */
    private void validarNombreUnicoParaModificacion(String nombre, Long idActual) throws ServiciosException {
        try {
            jakarta.persistence.TypedQuery<TiposEquipo> query = em.createQuery("SELECT t FROM TiposEquipo t WHERE UPPER(t.nombreTipo) = :nombre AND t.id != :id", TiposEquipo.class);
            if (query != null) {
                query.setParameter("nombre", nombre.toUpperCase());
                query.setParameter("id", idActual);
                query.getSingleResult();
                throw new ServiciosException("Ya existe otro tipo de equipo con el nombre: " + nombre);
            }
        } catch (NoResultException e) {
            // El nombre no existe, es válido
        } catch (ServiciosException e) {
            // Re-lanzar ServiciosException específicas
            throw e;
        } catch (Exception e) {
            // Handle null query or other exceptions
            throw new ServiciosException("Error al validar el nombre del tipo de equipo");
        }
    }
}

