package codigocreativo.uy.servidorapp.servicios;

import codigocreativo.uy.servidorapp.dtos.ModelosEquipoDto;
import codigocreativo.uy.servidorapp.dtos.dtomappers.ModelosEquipoMapper;
import codigocreativo.uy.servidorapp.entidades.ModelosEquipo;
import codigocreativo.uy.servidorapp.enumerados.Estados;
import codigocreativo.uy.servidorapp.excepciones.ServiciosException;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.NoResultException;

import java.util.List;
import codigocreativo.uy.servidorapp.entidades.MarcasModelo;

@Stateless
public class ModelosEquipoBean implements ModelosEquipoRemote {
    @PersistenceContext(unitName = "default")
    private EntityManager em;
    private final ModelosEquipoMapper modelosEquipoMapper;

    @Inject
    public ModelosEquipoBean(ModelosEquipoMapper modelosEquipoMapper) {
        this.modelosEquipoMapper = modelosEquipoMapper;
    }

    @Override
    public void crearModelos(ModelosEquipoDto modelosEquipo) throws ServiciosException {

        if (modelosEquipo == null) {
            throw new ServiciosException("El modelo es obligatorio");
        }

        if (modelosEquipo.getNombre() == null || modelosEquipo.getNombre().trim().isEmpty()) {
            throw new ServiciosException("El nombre del modelo es obligatorio");
        }

        if (modelosEquipo.getIdMarca() == null || modelosEquipo.getIdMarca().getId() == null) {
            throw new ServiciosException("La marca del modelo es obligatoria");
        }

        MarcasModelo marca = em.find(MarcasModelo.class, modelosEquipo.getIdMarca().getId());
        if (marca == null) {
            throw new ServiciosException("La marca especificada no existe");
        }
        
        try {

            validarNombreUnico(modelosEquipo.getNombre().trim());

            modelosEquipo.setEstado(Estados.ACTIVO);
            
            em.persist(modelosEquipoMapper.toEntity(modelosEquipo));
            em.flush();
        } catch (ServiciosException e) {

            throw e;
        } catch (Exception e) {
            throw new ServiciosException("Error al crear el modelo");
        }
    }

    @Override
    public void modificarModelos(ModelosEquipoDto modelosEquipo) throws ServiciosException {

        if (modelosEquipo == null) {
            throw new ServiciosException("El modelo es obligatorio");
        }

        if (modelosEquipo.getId() == null) {
            throw new ServiciosException("El ID del modelo es obligatorio para modificar");
        }
        
        try {

            ModelosEquipo actual = em.find(ModelosEquipo.class, modelosEquipo.getId());
            if (actual == null) {
                throw new ServiciosException("No se encontró el modelo con ID: " + modelosEquipo.getId());
            }

            if (modelosEquipo.getNombre() == null || modelosEquipo.getNombre().trim().isEmpty()) {
                throw new ServiciosException("El nombre del modelo es obligatorio");
            }

            validarNombreUnicoParaModificacion(modelosEquipo.getNombre().trim(), modelosEquipo.getId());
            
            em.merge(modelosEquipoMapper.toEntity(modelosEquipo));
            em.flush();
        } catch (ServiciosException e) {

            throw e;
        } catch (Exception e) {
            throw new ServiciosException("Error al modificar el modelo");
        }
    }

    @Override
    public ModelosEquipoDto obtenerModelos(Long id) throws ServiciosException {
        if (id == null) {
            throw new ServiciosException("El ID es obligatorio");
        }
        
        try {
            ModelosEquipo modelo = em.find(ModelosEquipo.class, id);
            if (modelo == null) {
                throw new ServiciosException("No se encontró el modelo con ID: " + id);
            }
            
            return modelosEquipoMapper.toDto(modelo);
        } catch (ServiciosException e) {

            throw e;
        } catch (Exception e) {
            throw new ServiciosException("Error al obtener el modelo");
        }
    }

    @Override
    public List<ModelosEquipoDto> listarModelos() {
        return modelosEquipoMapper.toDto(
            em.createQuery("SELECT modelosEquipo FROM ModelosEquipo modelosEquipo ORDER BY modelosEquipo.nombre ASC", ModelosEquipo.class)
                .getResultList()
        );
    }

    @Override
    public void eliminarModelos(Long id) throws ServiciosException {
        if (id == null) {
            throw new ServiciosException("El ID es obligatorio");
        }
        
        try {

            ModelosEquipo modelo = em.find(ModelosEquipo.class, id);
            if (modelo == null) {
                throw new ServiciosException("Modelo no encontrado");
            }
            
            int updatedRows = em.createQuery("UPDATE ModelosEquipo modelosEquipo SET modelosEquipo.estado = 'INACTIVO' WHERE modelosEquipo.id = :id")
                    .setParameter("id", id)
                    .executeUpdate();
            
            if (updatedRows == 0) {
                throw new ServiciosException("No se pudo inactivar el modelo");
            }
            
            em.flush();
        } catch (ServiciosException e) {

            throw e;
        } catch (Exception e) {
            throw new ServiciosException("Error al eliminar el modelo");
        }
    }
    
    
    private void validarNombreUnico(String nombre) throws ServiciosException {
        try {
            em.createQuery("SELECT m FROM ModelosEquipo m WHERE UPPER(m.nombre) = :nombre", ModelosEquipo.class)
                    .setParameter("nombre", nombre.toUpperCase())
                    .getSingleResult();
            throw new ServiciosException("Ya existe un modelo con el nombre: " + nombre);
        } catch (NoResultException e) {

        }
    }
    
    
    private void validarNombreUnicoParaModificacion(String nombre, Long idActual) throws ServiciosException {
        try {
            em.createQuery("SELECT m FROM ModelosEquipo m WHERE UPPER(m.nombre) = :nombre AND m.id != :id", ModelosEquipo.class)
                    .setParameter("nombre", nombre.toUpperCase())
                    .setParameter("id", idActual)
                    .getSingleResult();
            throw new ServiciosException("Ya existe otro modelo con el nombre: " + nombre);
        } catch (NoResultException e) {

        }
    }
}