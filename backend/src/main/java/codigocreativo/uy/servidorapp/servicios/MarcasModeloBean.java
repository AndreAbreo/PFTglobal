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

        if (marcasModelo == null) {
            throw new ServiciosException("La marca es obligatoria");
        }

        if (marcasModelo.getNombre() == null || marcasModelo.getNombre().trim().isEmpty()) {
            throw new ServiciosException("El nombre de la marca es obligatorio");
        }
        
        try {

            validarNombreUnico(marcasModelo.getNombre().trim());
            
            marcasModelo.setEstado(Estados.ACTIVO);
            em.persist(marcasModeloMapper.toEntity(marcasModelo));
            em.flush();
        } catch (ServiciosException e) {

            throw e;
        } catch (Exception e) {
            throw new ServiciosException("Error al crear la marca");
        }
    }

    @Override
    public void modificarMarcasModelo(MarcasModeloDto marcasModelo) throws ServiciosException {

        if (marcasModelo == null) {
            throw new ServiciosException("La marca es obligatoria");
        }

        if (marcasModelo.getId() == null) {
            throw new ServiciosException("El ID de la marca es obligatorio para modificar");
        }
        
        MarcasModelo actual = em.find(MarcasModelo.class, marcasModelo.getId());
        if (actual == null) {
            throw new ServiciosException("No se encontró la marca con ID: " + marcasModelo.getId());
        }

        if (marcasModelo.getNombre() == null || marcasModelo.getNombre().trim().isEmpty()) {
            throw new ServiciosException("El nombre de la marca no puede ser nulo ni vacío");
        }

        if (!actual.getNombre().equals(marcasModelo.getNombre().trim())) {
            throw new ServiciosException("No se permite modificar el nombre de la marca");
        }

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

    public List<MarcasModeloDto> obtenerMarcasPorEstadoLista(Estados estado) throws ServiciosException {
        if (estado == null) {
            throw new ServiciosException("El estado es obligatorio para filtrar");
        }
        
        return marcasModeloMapper.toDto(
            em.createQuery("SELECT marcasModelo FROM MarcasModelo marcasModelo WHERE marcasModelo.estado = :estado ORDER BY marcasModelo.id DESC", MarcasModelo.class)
                .setParameter("estado", estado.name())
                .getResultList()
        );
    }

    @Override
    public void eliminarMarca(Long id) throws ServiciosException {
        if (id == null) {
            throw new ServiciosException("El ID es obligatorio");
        }

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
    
    
    private void validarNombreUnico(String nombre) throws ServiciosException {
        try {
            em.createQuery("SELECT m FROM MarcasModelo m WHERE UPPER(m.nombre) = :nombre", MarcasModelo.class)
                    .setParameter("nombre", nombre.toUpperCase())
                    .getSingleResult();
            throw new ServiciosException("Ya existe una marca con el nombre: " + nombre);
        } catch (NoResultException e) {

        }
    }
}

