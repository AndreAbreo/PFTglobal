package codigocreativo.uy.servidorapp.servicios;

import codigocreativo.uy.servidorapp.dtos.TiposIntervencioneDto;
import codigocreativo.uy.servidorapp.dtos.dtomappers.TiposIntervencioneMapper;
import codigocreativo.uy.servidorapp.entidades.TiposIntervencione;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.List;

@Stateless
public class TipoIntervencioneBean implements TipoIntervencioneRemote {
    @PersistenceContext(unitName = "default")
    private EntityManager em;
    private final TiposIntervencioneMapper tiposIntervencioneMapper;

    @Inject
    public TipoIntervencioneBean(TiposIntervencioneMapper tiposIntervencioneMapper) {
        this.tiposIntervencioneMapper = tiposIntervencioneMapper;
    }

    @Override
    public List<TiposIntervencioneDto> obtenerTiposIntervenciones() {
        List<TiposIntervencione> tiposIntervenciones = em.createQuery("SELECT t FROM TiposIntervencione t WHERE t.estado = 'ACTIVO'", TiposIntervencione.class).getResultList();
        return tiposIntervencioneMapper.toDto(tiposIntervenciones);
    }

    @Override
    public TiposIntervencioneDto obtenerTipoIntervencion(Long id) {
        TiposIntervencione tipoIntervencion = em.find(TiposIntervencione.class, id);
        return tiposIntervencioneMapper.toDto(tipoIntervencion);
    }

    @Override
    public void crearTipoIntervencion(TiposIntervencioneDto tipoIntervencion) {
        TiposIntervencione tipoIntervencionEntity = tiposIntervencioneMapper.toEntity(tipoIntervencion);
        em.persist(tipoIntervencionEntity);
    }

    @Override
    public void modificarTipoIntervencion(TiposIntervencioneDto tipoIntervencion) {
        TiposIntervencione tipoIntervencionEntity = tiposIntervencioneMapper.toEntity(tipoIntervencion);
        em.merge(tipoIntervencionEntity);
    }

    @Override
    public void eliminarTipoIntervencion(Long id) {
        //no puede borrar solo poner como estado inactivo
        em.createQuery("UPDATE TiposIntervencione t SET t.estado = 'INACTIVO' WHERE t.id = :id")
                .setParameter("id", id)
                .executeUpdate();
    }

    public List<TiposIntervencioneDto> filtrarTiposIntervenciones(String estado, String nombre) {
        codigocreativo.uy.servidorapp.enumerados.Estados estadoEnum = null;
        if (estado != null && !estado.trim().isEmpty()) {
            estadoEnum = codigocreativo.uy.servidorapp.enumerados.Estados.valueOf(estado);
        }

        boolean tieneEstado = estadoEnum != null;
        boolean tieneNombre = nombre != null && !nombre.trim().isEmpty();

        if (tieneEstado && tieneNombre) {
            return obtenerPorEstadoYNombre(estadoEnum, nombre.trim());
        } else if (tieneEstado) {
            return obtenerPorEstado(estadoEnum);
        } else if (tieneNombre) {
            return obtenerPorNombre(nombre.trim());
        } else {
            return obtenerTiposIntervenciones();
        }
    }

    private List<TiposIntervencioneDto> obtenerPorEstado(codigocreativo.uy.servidorapp.enumerados.Estados estado) {
        List<TiposIntervencione> tipos = em.createQuery("SELECT t FROM TiposIntervencione t WHERE t.estado = :estado ORDER BY t.nombreTipo ASC", TiposIntervencione.class)
                .setParameter("estado", estado.name())
                .getResultList();
        return tiposIntervencioneMapper.toDto(tipos);
    }

    private List<TiposIntervencioneDto> obtenerPorNombre(String nombre) {
        List<TiposIntervencione> tipos = em.createQuery("SELECT t FROM TiposIntervencione t WHERE UPPER(t.nombreTipo) LIKE UPPER(:nombre) ORDER BY t.nombreTipo ASC", TiposIntervencione.class)
                .setParameter("nombre", "%" + nombre + "%")
                .getResultList();
        return tiposIntervencioneMapper.toDto(tipos);
    }

    private List<TiposIntervencioneDto> obtenerPorEstadoYNombre(codigocreativo.uy.servidorapp.enumerados.Estados estado, String nombre) {
        List<TiposIntervencione> tipos = em.createQuery("SELECT t FROM TiposIntervencione t WHERE t.estado = :estado AND UPPER(t.nombreTipo) LIKE UPPER(:nombre) ORDER BY t.nombreTipo ASC", TiposIntervencione.class)
                .setParameter("estado", estado.name())
                .setParameter("nombre", "%" + nombre + "%")
                .getResultList();
        return tiposIntervencioneMapper.toDto(tipos);
    }
}
