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
        // Validar que el DTO no sea nulo
        if (modelosEquipo == null) {
            throw new ServiciosException("El modelo es obligatorio");
        }
        
        // Validar que el nombre no sea nulo ni vacío
        if (modelosEquipo.getNombre() == null || modelosEquipo.getNombre().trim().isEmpty()) {
            throw new ServiciosException("El nombre del modelo es obligatorio");
        }
        
        // Validar que la marca exista
        if (modelosEquipo.getIdMarca() == null || modelosEquipo.getIdMarca().getId() == null) {
            throw new ServiciosException("La marca del modelo es obligatoria");
        }
        
        // Validar que la marca existe en la base de datos
        MarcasModelo marca = em.find(MarcasModelo.class, modelosEquipo.getIdMarca().getId());
        if (marca == null) {
            throw new ServiciosException("La marca especificada no existe");
        }
        
        try {
            // Validar que no exista un modelo con el mismo nombre (case-insensitive)
            validarNombreUnico(modelosEquipo.getNombre().trim());
            
            // Estado por defecto
            modelosEquipo.setEstado(Estados.ACTIVO);
            
            em.persist(modelosEquipoMapper.toEntity(modelosEquipo));
            em.flush();
        } catch (ServiciosException e) {
            // Re-lanzar ServiciosException específicas
            throw e;
        } catch (Exception e) {
            throw new ServiciosException("Error al crear el modelo");
        }
    }

    @Override
    public void modificarModelos(ModelosEquipoDto modelosEquipo) throws ServiciosException {
        // Validar que el DTO no sea nulo
        if (modelosEquipo == null) {
            throw new ServiciosException("El modelo es obligatorio");
        }
        
        // Validar que el ID no sea nulo
        if (modelosEquipo.getId() == null) {
            throw new ServiciosException("El ID del modelo es obligatorio para modificar");
        }
        
        try {
            // Verificar que el modelo existe
            ModelosEquipo actual = em.find(ModelosEquipo.class, modelosEquipo.getId());
            if (actual == null) {
                throw new ServiciosException("No se encontró el modelo con ID: " + modelosEquipo.getId());
            }
            
            // Validar que el nombre no sea nulo ni vacío
            if (modelosEquipo.getNombre() == null || modelosEquipo.getNombre().trim().isEmpty()) {
                throw new ServiciosException("El nombre del modelo es obligatorio");
            }
            
            // Validar que no exista otro modelo con el mismo nombre (excluyendo el actual)
            validarNombreUnicoParaModificacion(modelosEquipo.getNombre().trim(), modelosEquipo.getId());
            
            em.merge(modelosEquipoMapper.toEntity(modelosEquipo));
            em.flush();
        } catch (ServiciosException e) {
            // Re-lanzar ServiciosException específicas
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
            // Re-lanzar ServiciosException específicas
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
            // Verificar que el modelo existe antes de intentar eliminarlo
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
            // Re-lanzar ServiciosException específicas
            throw e;
        } catch (Exception e) {
            throw new ServiciosException("Error al eliminar el modelo");
        }
    }
    
    /**
     * Valida que el nombre del modelo sea único en la base de datos
     */
    private void validarNombreUnico(String nombre) throws ServiciosException {
        try {
            em.createQuery("SELECT m FROM ModelosEquipo m WHERE UPPER(m.nombre) = :nombre", ModelosEquipo.class)
                    .setParameter("nombre", nombre.toUpperCase())
                    .getSingleResult();
            throw new ServiciosException("Ya existe un modelo con el nombre: " + nombre);
        } catch (NoResultException e) {
            // El nombre no existe, es válido
        }
    }
    
    /**
     * Valida que el nombre del modelo sea único para modificaciones (excluyendo el modelo actual)
     */
    private void validarNombreUnicoParaModificacion(String nombre, Long idActual) throws ServiciosException {
        try {
            em.createQuery("SELECT m FROM ModelosEquipo m WHERE UPPER(m.nombre) = :nombre AND m.id != :id", ModelosEquipo.class)
                    .setParameter("nombre", nombre.toUpperCase())
                    .setParameter("id", idActual)
                    .getSingleResult();
            throw new ServiciosException("Ya existe otro modelo con el nombre: " + nombre);
        } catch (NoResultException e) {
            // El nombre no existe, es válido
        }
    }

    public List<ModelosEquipoDto> filtrarModelos(String estado, String nombre) {
        Estados estadoEnum = null;
        if (estado != null && !estado.trim().isEmpty()) {
            estadoEnum = Estados.valueOf(estado);
        }

        boolean tieneEstado = estadoEnum != null;
        boolean tieneNombre = nombre != null && !nombre.trim().isEmpty();

        if (tieneEstado && tieneNombre) {
            return obtenerModelosPorEstadoYNombre(estadoEnum, nombre.trim());
        } else if (tieneEstado) {
            return obtenerModelosPorEstado(estadoEnum);
        } else if (tieneNombre) {
            return obtenerModelosPorNombre(nombre.trim());
        } else {
            return listarModelos();
        }
    }

    private List<ModelosEquipoDto> obtenerModelosPorEstado(Estados estado) {
        List<ModelosEquipo> modelos = em.createQuery(
                "SELECT m FROM ModelosEquipo m WHERE m.estado = :estado ORDER BY m.nombre ASC",
                ModelosEquipo.class)
                .setParameter("estado", estado.name())
                .getResultList();
        return modelosEquipoMapper.toDto(modelos);
    }

    private List<ModelosEquipoDto> obtenerModelosPorNombre(String nombre) {
        List<ModelosEquipo> modelos = em.createQuery(
                "SELECT m FROM ModelosEquipo m WHERE UPPER(m.nombre) LIKE UPPER(:nombre) ORDER BY m.nombre ASC",
                ModelosEquipo.class)
                .setParameter("nombre", "%" + nombre + "%")
                .getResultList();
        return modelosEquipoMapper.toDto(modelos);
    }

    private List<ModelosEquipoDto> obtenerModelosPorEstadoYNombre(Estados estado, String nombre) {
        List<ModelosEquipo> modelos = em.createQuery(
                "SELECT m FROM ModelosEquipo m WHERE m.estado = :estado AND UPPER(m.nombre) LIKE UPPER(:nombre) ORDER BY m.nombre ASC",
                ModelosEquipo.class)
                .setParameter("estado", estado.name())
                .setParameter("nombre", "%" + nombre + "%")
                .getResultList();
        return modelosEquipoMapper.toDto(modelos);
    }
}