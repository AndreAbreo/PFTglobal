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

        if (tiposEquipo == null) {
            throw new ServiciosException("El tipo de equipo es obligatorio");
        }

        if (tiposEquipo.getNombreTipo() == null || tiposEquipo.getNombreTipo().trim().isEmpty()) {
            throw new ServiciosException("El nombre del tipo de equipo es obligatorio");
        }

        validarNombreUnico(tiposEquipo.getNombreTipo().trim());

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

        if (tiposEquipo == null) {
            throw new ServiciosException("El tipo de equipo es obligatorio");
        }

        if (tiposEquipo.getId() == null) {
            throw new ServiciosException("El ID del tipo de equipo es obligatorio para modificar");
        }

        TiposEquipo actual = em.find(TiposEquipo.class, tiposEquipo.getId());
        if (actual == null) {
            throw new ServiciosException("No se encontró el tipo de equipo con ID: " + tiposEquipo.getId());
        }

        if (tiposEquipo.getNombreTipo() == null || tiposEquipo.getNombreTipo().trim().isEmpty()) {
            throw new ServiciosException("El nombre del tipo de equipo no puede ser nulo ni vacío");
        }
        
        try {

            validarNombreUnicoParaModificacion(tiposEquipo.getNombreTipo().trim(), tiposEquipo.getId());
            
            TiposEquipo tiposEquipoEntity = tiposEquipoMapper.toEntity(tiposEquipo);
            em.merge(tiposEquipoEntity);
            em.flush();
        } catch (ServiciosException e) {

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

        TiposEquipo tiposEquipoEntity = em.find(TiposEquipo.class, id);
        if (tiposEquipoEntity == null) {
            throw new ServiciosException("No se encontró el tipo de equipo con ID: " + id);
        }

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
    
    
    private void validarNombreUnico(String nombre) throws ServiciosException {
        try {
            jakarta.persistence.TypedQuery<TiposEquipo> query = em.createQuery("SELECT t FROM TiposEquipo t WHERE UPPER(t.nombreTipo) = :nombre", TiposEquipo.class);
            if (query != null) {
                query.setParameter("nombre", nombre.toUpperCase());
                query.getSingleResult();
                throw new ServiciosException("Ya existe un tipo de equipo con el nombre: " + nombre);
            }
        } catch (NoResultException e) {

        } catch (ServiciosException e) {

            throw e;
        } catch (Exception e) {

            throw new ServiciosException("Error al validar el nombre del tipo de equipo");
        }
    }
    
    
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

        } catch (ServiciosException e) {

            throw e;
        } catch (Exception e) {

            throw new ServiciosException("Error al validar el nombre del tipo de equipo");
        }
    }
}

