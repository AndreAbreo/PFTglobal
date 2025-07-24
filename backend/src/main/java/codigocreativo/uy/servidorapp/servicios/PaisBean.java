package codigocreativo.uy.servidorapp.servicios;

import codigocreativo.uy.servidorapp.dtos.PaisDto;
import codigocreativo.uy.servidorapp.dtos.dtomappers.PaisMapper;
import codigocreativo.uy.servidorapp.entidades.Pais;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.List;
@Stateless
public class PaisBean implements PaisRemote{
    @PersistenceContext (unitName = "default")
    private EntityManager em;
    private final PaisMapper paisMapper;

    @Inject //Se inyecta el mapper
    public PaisBean(PaisMapper paisMapper) {
        this.paisMapper = paisMapper;
    }

    @Override
    public void crearPais(PaisDto pais) {
        // Validar nombre no nulo ni vacío
        if (pais.getNombre() == null || pais.getNombre().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del país no puede ser nulo ni vacío");
        }
        // Forzar mayúscula en la primer letra
        String nombre = pais.getNombre().trim();
        pais.setNombre(nombre.substring(0, 1).toUpperCase() + nombre.substring(1));
        // Validar que no esté repetido (case-insensitive)
        Long count = em.createQuery("SELECT COUNT(p) FROM Pais p WHERE UPPER(p.nombre) = :nombre", Long.class)
            .setParameter("nombre", pais.getNombre().toUpperCase())
            .getSingleResult();
        if (count > 0) {
            throw new IllegalArgumentException("Ya existe un país con ese nombre");
        }
        // Estado por defecto
        pais.setEstado(codigocreativo.uy.servidorapp.enumerados.Estados.ACTIVO);
        Pais paisEntity = paisMapper.toEntity(pais);
        em.persist(paisEntity);
        em.flush();
    }

    @Override
    public void modificarPais(PaisDto pais) {
        em.merge(paisMapper.toEntity(pais));
        em.flush();
    }

    @Override
    public List<PaisDto> obtenerpais() {
        List<Pais> paises = em.createQuery("SELECT p FROM Pais p ORDER BY p.nombre ASC", Pais.class).getResultList();
        return paisMapper.toDto(paises);
    }

    public List<PaisDto> obtenerPaisPorEstado(codigocreativo.uy.servidorapp.enumerados.Estados estado) {
        List<Pais> paises = em.createQuery("SELECT p FROM Pais p WHERE p.estado = :estado ORDER BY p.nombre ASC", Pais.class)
            .setParameter("estado", estado.name())
            .getResultList();
        return paisMapper.toDto(paises);
    }

    public List<PaisDto> obtenerPaisPorEstadoOpcional(codigocreativo.uy.servidorapp.enumerados.Estados estado) {
        if (estado == null) {
            // Si no se proporciona estado, devolver todos los países
            return obtenerpais();
        } else {
            // Si se proporciona estado, filtrar por ese estado
            return obtenerPaisPorEstado(estado);
        }
    }

    public List<PaisDto> obtenerPaisPorNombre(String nombre) {
        List<Pais> paises = em.createQuery("SELECT p FROM Pais p WHERE UPPER(p.nombre) LIKE UPPER(:nombre) ORDER BY p.nombre ASC", Pais.class)
            .setParameter("nombre", "%" + nombre + "%")
            .getResultList();
        return paisMapper.toDto(paises);
    }

    public List<PaisDto> obtenerPaisPorEstadoYNombre(codigocreativo.uy.servidorapp.enumerados.Estados estado, String nombre) {
        List<Pais> paises = em.createQuery("SELECT p FROM Pais p WHERE p.estado = :estado AND UPPER(p.nombre) LIKE UPPER(:nombre) ORDER BY p.nombre ASC", Pais.class)
            .setParameter("estado", estado.name())
            .setParameter("nombre", "%" + nombre + "%")
            .getResultList();
        return paisMapper.toDto(paises);
    }

    public List<PaisDto> filtrarPaises(String estado, String nombre) {
        codigocreativo.uy.servidorapp.enumerados.Estados estadoEnum = null;
        if (estado != null && !estado.trim().isEmpty()) {
            estadoEnum = codigocreativo.uy.servidorapp.enumerados.Estados.valueOf(estado);
        }
        
        boolean tieneEstado = estadoEnum != null;
        boolean tieneNombre = nombre != null && !nombre.trim().isEmpty();
        
        if (tieneEstado && tieneNombre) {
            // Filtrar por estado y nombre
            return obtenerPaisPorEstadoYNombre(estadoEnum, nombre.trim());
        } else if (tieneEstado && !tieneNombre) {
            // Filtrar solo por estado
            return obtenerPaisPorEstadoOpcional(estadoEnum);
        } else if (!tieneEstado && tieneNombre) {
            // Filtrar solo por nombre
            return obtenerPaisPorNombre(nombre.trim());
        } else {
            // Sin filtros, devolver todos
            return obtenerPaisPorEstadoOpcional(null);
        }
    }

    public void inactivarPais(Long id) {
        int updatedRows = em.createQuery("UPDATE Pais p SET p.estado = 'INACTIVO' WHERE p.id = :id")
            .setParameter("id", id)
            .executeUpdate();
        
        if (updatedRows == 0) {
            throw new IllegalArgumentException("País no encontrado");
        }
        
        em.flush();
    }

    public PaisDto obtenerPaisPorId(Long id) {
        Pais pais = em.find(Pais.class, id);
        return paisMapper.toDto(pais);
    }
}
