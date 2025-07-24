package codigocreativo.uy.servidorapp.servicios;

import codigocreativo.uy.servidorapp.dtos.PaisDto;
import codigocreativo.uy.servidorapp.enumerados.Estados;
import jakarta.ejb.Remote;

import java.util.List;
@Remote
public interface PaisRemote {
    public void crearPais(PaisDto pais);
    public void modificarPais(PaisDto pais);
    List<PaisDto> obtenerpais();
    List<PaisDto> obtenerPaisPorEstado(Estados estado);
    List<PaisDto> obtenerPaisPorEstadoOpcional(Estados estado);
    List<PaisDto> obtenerPaisPorNombre(String nombre);
    List<PaisDto> obtenerPaisPorEstadoYNombre(Estados estado, String nombre);
    List<PaisDto> filtrarPaises(String estado, String nombre);
    PaisDto obtenerPaisPorId(Long id);
    void inactivarPais(Long id);
}
