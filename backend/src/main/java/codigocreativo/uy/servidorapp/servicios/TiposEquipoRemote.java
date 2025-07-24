package codigocreativo.uy.servidorapp.servicios;

import codigocreativo.uy.servidorapp.dtos.TiposEquipoDto;
import codigocreativo.uy.servidorapp.excepciones.ServiciosException;
import jakarta.ejb.Remote;

import java.util.List;

@Remote
public interface TiposEquipoRemote {
    public void crearTiposEquipo(TiposEquipoDto tiposEquipo) throws ServiciosException;
    public void modificarTiposEquipo(TiposEquipoDto tiposEquipo) throws ServiciosException;
    public void eliminarTiposEquipo(Long id) throws ServiciosException;
    public TiposEquipoDto obtenerPorId(Long id) throws ServiciosException;
    public List<TiposEquipoDto> listarTiposEquipo();
}
