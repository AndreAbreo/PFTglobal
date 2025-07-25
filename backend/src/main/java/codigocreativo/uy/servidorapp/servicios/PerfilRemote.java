package codigocreativo.uy.servidorapp.servicios;

import codigocreativo.uy.servidorapp.dtos.PerfilDto;
import codigocreativo.uy.servidorapp.enumerados.Estados;
import codigocreativo.uy.servidorapp.excepciones.ServiciosException;
import jakarta.ejb.Remote;

import java.util.List;

@Remote
public interface PerfilRemote {
    public PerfilDto crearPerfil(PerfilDto p) throws ServiciosException;
    public void modificarPerfil(PerfilDto p) throws ServiciosException;
    public void eliminarPerfil(PerfilDto p) throws ServiciosException;
    public PerfilDto obtenerPerfil(Long id);
    public List<PerfilDto> obtenerPerfiles();
    public List<PerfilDto> listarPerfilesPorNombre(String nombre);
    public List<PerfilDto> listarPerfilesPorEstado(Estados estado);
    List<PerfilDto> filtrarPerfiles(String estado, String nombre);

}
