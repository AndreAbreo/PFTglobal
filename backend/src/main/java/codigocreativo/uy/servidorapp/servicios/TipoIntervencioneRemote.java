package codigocreativo.uy.servidorapp.servicios;

import codigocreativo.uy.servidorapp.dtos.TiposIntervencioneDto;
import jakarta.ejb.Remote;

import java.util.List;

@Remote
public interface TipoIntervencioneRemote {
    List<TiposIntervencioneDto> obtenerTiposIntervenciones();
    TiposIntervencioneDto obtenerTipoIntervencion(Long id);
    void crearTipoIntervencion(TiposIntervencioneDto tipoIntervencion);
    void modificarTipoIntervencion(TiposIntervencioneDto tipoIntervencion);
    void eliminarTipoIntervencion(Long id);
}
