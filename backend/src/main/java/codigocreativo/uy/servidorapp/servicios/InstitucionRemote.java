package codigocreativo.uy.servidorapp.servicios;

import codigocreativo.uy.servidorapp.dtos.InstitucionDto;
import codigocreativo.uy.servidorapp.dtos.UbicacionDto;
import jakarta.ejb.Remote;

import java.util.List;

@Remote
public interface InstitucionRemote {
    public void agregar(InstitucionDto i);
    public void eliminarInstitucion(InstitucionDto i);
    public void modificar(InstitucionDto i);
    public List<UbicacionDto> obtenerUbicaciones();
    public List<InstitucionDto> obtenerInstituciones();
    public InstitucionDto obtenerInstitucionPorNombre(String nombre);
    public InstitucionDto obtenerInstitucionPorId(Long id);
}
