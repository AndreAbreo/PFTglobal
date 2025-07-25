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

    @Override
    public List<TiposIntervencioneDto> filtrarTiposIntervenciones(String nombre, String estado) {
        codigocreativo.uy.servidorapp.enumerados.Estados estadoEnum = null;
        if (estado != null && !estado.trim().isEmpty()) {
            estadoEnum = codigocreativo.uy.servidorapp.enumerados.Estados.valueOf(estado);
        }

        StringBuilder jpql = new StringBuilder("SELECT t FROM TiposIntervencione t WHERE 1=1");
        if (nombre != null && !nombre.trim().isEmpty()) {
            jpql.append(" AND UPPER(t.nombreTipo) LIKE UPPER(:nombre)");
        }
        if (estadoEnum != null) {
            jpql.append(" AND t.estado = :estado");
        }
        jpql.append(" ORDER BY t.nombreTipo ASC");

        var query = em.createQuery(jpql.toString(), TiposIntervencione.class);
        if (nombre != null && !nombre.trim().isEmpty()) {
            query.setParameter("nombre", "%" + nombre.trim() + "%");
        }
        if (estadoEnum != null) {
            query.setParameter("estado", estadoEnum.name());
        }

        return tiposIntervencioneMapper.toDto(query.getResultList());
    }
}
