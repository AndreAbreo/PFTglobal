package codigocreativo.uy.servidorapp.servicios;

import codigocreativo.uy.servidorapp.dtos.MarcasModeloDto;
import codigocreativo.uy.servidorapp.dtos.dtomappers.MarcasModeloMapper;
import codigocreativo.uy.servidorapp.entidades.MarcasModelo;
import codigocreativo.uy.servidorapp.enumerados.Estados;
import codigocreativo.uy.servidorapp.excepciones.ServiciosException;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.NoResultException;

import java.util.List;

@Stateless
public class MarcasModeloBean implements MarcasModeloRemote{
    @PersistenceContext (unitName = "default")
    private EntityManager em;
    private final MarcasModeloMapper marcasModeloMapper;

    @Inject
    public MarcasModeloBean(MarcasModeloMapper marcasModeloMapper) {
        this.marcasModeloMapper = marcasModeloMapper;
    }

    @Override
    public void crearMarcasModelo(MarcasModeloDto marcasModelo) throws ServiciosException {
        // Validar que el DTO no sea nulo
        if (marcasModelo == null) {
            throw new ServiciosException("La marca es obligatoria");
        }
        
        // Validar que el nombre no sea nulo ni vacío
        if (marcasModelo.getNombre() == null || marcasModelo.getNombre().trim().isEmpty()) {
            throw new ServiciosException("El nombre de la marca es obligatorio");
        }
        
        try {
            // Validar que no exista una marca con el mismo nombre (case-insensitive)
            validarNombreUnico(marcasModelo.getNombre().trim());
            
            marcasModelo.setEstado(Estados.ACTIVO);
            em.persist(marcasModeloMapper.toEntity(marcasModelo));
            em.flush();
        } catch (ServiciosException e) {
            // Re-lanzar ServiciosException específicas (como duplicados)
            throw e;
        } catch (Exception e) {
            throw new ServiciosException("Error al crear la marca");
        }
    }

    @Override
    public void modificarMarcasModelo(MarcasModeloDto marcasModelo) throws ServiciosException {
        // Validar que el DTO no sea nulo
        if (marcasModelo == null) {
            throw new ServiciosException("La marca es obligatoria");
        }
        
        // Validar que el ID no sea nulo
        if (marcasModelo.getId() == null) {
            throw new ServiciosException("El ID de la marca es obligatorio para modificar");
        }
        
        MarcasModelo actual = em.find(MarcasModelo.class, marcasModelo.getId());
        if (actual == null) {
            throw new ServiciosException("No se encontró la marca con ID: " + marcasModelo.getId());
        }
        
        // Validar que el nombre no sea nulo ni vacío
        if (marcasModelo.getNombre() == null || marcasModelo.getNombre().trim().isEmpty()) {
            throw new ServiciosException("El nombre de la marca no puede ser nulo ni vacío");
        }
        
        // Validar que no se esté cambiando el nombre
        if (!actual.getNombre().equals(marcasModelo.getNombre().trim())) {
            throw new ServiciosException("No se permite modificar el nombre de la marca");
        }
        
        // Solo se permite modificar el estado (u otros campos permitidos)
        actual.setEstado(marcasModelo.getEstado().name());
        em.merge(actual);
        em.flush();
    }

    @Override
    public MarcasModeloDto obtenerMarca(Long id) throws ServiciosException {
        if (id == null) {
            throw new ServiciosException("El ID es obligatorio");
        }
        
        MarcasModelo marca = em.find(MarcasModelo.class, id);
        if (marca == null) {
            throw new ServiciosException("Marca no encontrada");
        }
        
        return marcasModeloMapper.toDto(marca);
    }

    @Override
    public List<MarcasModeloDto> obtenerMarcasLista() {
        return marcasModeloMapper.toDto(
            em.createQuery("SELECT marcasModelo FROM MarcasModelo marcasModelo ORDER BY marcasModelo.id DESC", MarcasModelo.class)
                .getResultList()
        );
    }

    public List<MarcasModeloDto> obtenerMarcasPorEstadoLista(Estados estado, String nombre) throws ServiciosException {
        if (estado == null) {
            throw new ServiciosException("El estado es obligatorio para filtrar");
        }

        String jpql = "SELECT marcasModelo FROM MarcasModelo marcasModelo WHERE marcasModelo.estado = :estado";
        if (nombre != null && !nombre.trim().isEmpty()) {
            jpql += " AND UPPER(marcasModelo.nombre) LIKE UPPER(:nombre)";
        }
        jpql += " ORDER BY marcasModelo.id DESC";

        var query = em.createQuery(jpql, MarcasModelo.class)
                .setParameter("estado", estado.name());
        if (nombre != null && !nombre.trim().isEmpty()) {
            query.setParameter("nombre", "%" + nombre.trim() + "%");
        }

        return marcasModeloMapper.toDto(query.getResultList());
    }

    @Override
    public void eliminarMarca(Long id) throws ServiciosException {
        if (id == null) {
            throw new ServiciosException("El ID es obligatorio");
        }
        
        // Verificar que la marca existe antes de intentar eliminarla
        MarcasModelo marca = em.find(MarcasModelo.class, id);
        if (marca == null) {
            throw new ServiciosException("Marca no encontrada");
        }
        
        int updatedRows = em.createQuery("UPDATE MarcasModelo marcasModelo SET marcasModelo.estado = 'INACTIVO' WHERE marcasModelo.id = :id")
                .setParameter("id", id)
                .executeUpdate();
        
        if (updatedRows == 0) {
            throw new ServiciosException("No se pudo inactivar la marca");
        }
        
        em.flush();
    }
    
    /**
     * Valida que el nombre de la marca sea único en la base de datos
     */
    private void validarNombreUnico(String nombre) throws ServiciosException {
        try {
            em.createQuery("SELECT m FROM MarcasModelo m WHERE UPPER(m.nombre) = :nombre", MarcasModelo.class)
                    .setParameter("nombre", nombre.toUpperCase())
                    .getSingleResult();
            throw new ServiciosException("Ya existe una marca con el nombre: " + nombre);
        } catch (NoResultException e) {
            // El nombre no existe, es válido
        }
    }
}

