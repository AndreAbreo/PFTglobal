package codigocreativo.uy.servidorapp.servicios;

import codigocreativo.uy.servidorapp.dtos.ProveedoresEquipoDto;
import codigocreativo.uy.servidorapp.dtos.dtomappers.ProveedoresEquipoMapper;
import codigocreativo.uy.servidorapp.entidades.ProveedoresEquipo;
import codigocreativo.uy.servidorapp.enumerados.Estados;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.List;

@Stateless
public class ProveedoresEquipoBean implements ProveedoresEquipoRemote {
    @PersistenceContext(unitName = "default")
    private EntityManager em;
    private final ProveedoresEquipoMapper proveedoresEquipoMapper;

    @Inject
    public ProveedoresEquipoBean(ProveedoresEquipoMapper proveedoresEquipoMapper) {
        this.proveedoresEquipoMapper = proveedoresEquipoMapper;
    }

    @Override
    public void crearProveedor(ProveedoresEquipoDto proveedoresEquipo) {

        if (proveedoresEquipo.getNombre() == null || proveedoresEquipo.getNombre().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del proveedor no puede ser nulo ni vacío");
        }

        Long count = em.createQuery("SELECT COUNT(p) FROM ProveedoresEquipo p WHERE UPPER(p.nombre) = :nombre", Long.class)
            .setParameter("nombre", proveedoresEquipo.getNombre().trim().toUpperCase())
            .getSingleResult();
        if (count > 0) {
            throw new IllegalArgumentException("Ya existe un proveedor con ese nombre");
        }

        proveedoresEquipo.setEstado(Estados.ACTIVO);
        ProveedoresEquipo proveedoresEquipoEntity = proveedoresEquipoMapper.toEntity(proveedoresEquipo);
        em.persist(proveedoresEquipoEntity);
        em.flush();
    }

    @Override
    public void modificarProveedor(ProveedoresEquipoDto proveedoresEquipo) {

        Long count = em.createQuery("SELECT COUNT(p) FROM ProveedoresEquipo p WHERE UPPER(p.nombre) = :nombre AND p.id != :id", Long.class)
            .setParameter("nombre", proveedoresEquipo.getNombre().trim().toUpperCase())
            .setParameter("id", proveedoresEquipo.getId())
            .getSingleResult();
        if (count > 0) {
            throw new IllegalArgumentException("Ya existe otro proveedor con ese nombre");
        }
        
        ProveedoresEquipo proveedoresEquipoEntity = proveedoresEquipoMapper.toEntity(proveedoresEquipo);
        em.merge(proveedoresEquipoEntity);
        em.flush();
    }

    @Override
    public ProveedoresEquipoDto obtenerProveedor(Long id) {
        ProveedoresEquipo proveedoresEquipo = em.find(ProveedoresEquipo.class, id);
        return proveedoresEquipoMapper.toDto(proveedoresEquipo);
    }

    @Override
    public List<ProveedoresEquipoDto> obtenerProveedores() {
        List<ProveedoresEquipo> proveedoresEquipo = em.createQuery("SELECT p FROM ProveedoresEquipo p ORDER BY p.nombre ASC", ProveedoresEquipo.class).getResultList();
        return proveedoresEquipoMapper.toDto(proveedoresEquipo);
    }

    @Override
    public void eliminarProveedor(Long id) {
        int updatedRows = em.createQuery("UPDATE ProveedoresEquipo p SET p.estado = 'INACTIVO' WHERE p.id = :id")
                .setParameter("id", id)
                .executeUpdate();
        
        if (updatedRows == 0) {
            throw new IllegalArgumentException("Proveedor no encontrado");
        }
        
        em.flush();
    }

    @Override
    public List<ProveedoresEquipoDto> buscarProveedores(String nombre, Estados estado) {
        String query = "SELECT p FROM ProveedoresEquipo p WHERE 1=1";
        if (nombre != null && !nombre.isEmpty()) {
            query += " AND LOWER(p.nombre) LIKE :nombre";
        }
        if (estado != null) {
            query += " AND UPPER(p.estado) = :estado";
        }
        var q = em.createQuery(query, ProveedoresEquipo.class);
        if (nombre != null && !nombre.isEmpty()) {
            q.setParameter("nombre", "%" + nombre.toLowerCase() + "%");
        }
        if (estado != null) {
            q.setParameter("estado", estado.name().toUpperCase());
        }
        return proveedoresEquipoMapper.toDto(q.getResultList());
    }

    public List<ProveedoresEquipoDto> filtrarProveedores(String nombre, String estado) {
        Estados estadoEnum = null;
        if (estado != null && !estado.trim().isEmpty()) {
            estadoEnum = Estados.valueOf(estado);
        }
        
        boolean tieneEstado = estadoEnum != null;
        boolean tieneNombre = nombre != null && !nombre.trim().isEmpty();
        
        if (tieneEstado && tieneNombre) {

            return buscarProveedoresPorEstadoYNombre(estadoEnum, nombre.trim());
        } else if (tieneEstado && !tieneNombre) {

            return buscarProveedoresPorEstado(estadoEnum);
        } else if (!tieneEstado && tieneNombre) {

            return buscarProveedoresPorNombre(nombre.trim());
        } else {

            return obtenerProveedores();
        }
    }

    private List<ProveedoresEquipoDto> buscarProveedoresPorEstado(Estados estado) {
        List<ProveedoresEquipo> proveedores = em.createQuery("SELECT p FROM ProveedoresEquipo p WHERE p.estado = :estado ORDER BY p.nombre ASC", ProveedoresEquipo.class)
            .setParameter("estado", estado.name())
            .getResultList();
        return proveedoresEquipoMapper.toDto(proveedores);
    }

    private List<ProveedoresEquipoDto> buscarProveedoresPorNombre(String nombre) {
        List<ProveedoresEquipo> proveedores = em.createQuery("SELECT p FROM ProveedoresEquipo p WHERE UPPER(p.nombre) LIKE UPPER(:nombre) ORDER BY p.nombre ASC", ProveedoresEquipo.class)
            .setParameter("nombre", "%" + nombre + "%")
            .getResultList();
        return proveedoresEquipoMapper.toDto(proveedores);
    }

    private List<ProveedoresEquipoDto> buscarProveedoresPorEstadoYNombre(Estados estado, String nombre) {
        List<ProveedoresEquipo> proveedores = em.createQuery("SELECT p FROM ProveedoresEquipo p WHERE p.estado = :estado AND UPPER(p.nombre) LIKE UPPER(:nombre) ORDER BY p.nombre ASC", ProveedoresEquipo.class)
            .setParameter("estado", estado.name())
            .setParameter("nombre", "%" + nombre + "%")
            .getResultList();
        return proveedoresEquipoMapper.toDto(proveedores);
    }
}