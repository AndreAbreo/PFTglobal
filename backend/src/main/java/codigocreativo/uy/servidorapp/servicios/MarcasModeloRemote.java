package codigocreativo.uy.servidorapp.servicios;
import codigocreativo.uy.servidorapp.dtos.MarcasModeloDto;
import codigocreativo.uy.servidorapp.enumerados.Estados;
import codigocreativo.uy.servidorapp.excepciones.ServiciosException;
import jakarta.ejb.Remote;

import java.util.List;
@Remote
public interface MarcasModeloRemote {
    public void crearMarcasModelo(MarcasModeloDto marcasModelo) throws ServiciosException;
    public void modificarMarcasModelo(MarcasModeloDto marcasModelo) throws ServiciosException;
    public MarcasModeloDto obtenerMarca(Long id) throws ServiciosException;
    public List<MarcasModeloDto> obtenerMarcasLista();
    List<MarcasModeloDto> obtenerMarcasPorEstadoLista(Estados estado, String nombre) throws ServiciosException;
    public void eliminarMarca(Long id) throws ServiciosException;
}
