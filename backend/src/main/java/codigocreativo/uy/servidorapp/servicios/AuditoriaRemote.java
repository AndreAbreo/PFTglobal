package codigocreativo.uy.servidorapp.servicios;

import codigocreativo.uy.servidorapp.dtos.AuditoriaDto;
import codigocreativo.uy.servidorapp.excepciones.ServiciosException;
import jakarta.ejb.Remote;

import java.util.List;

@Remote
public interface AuditoriaRemote {
    public void crearRegistro (AuditoriaDto a);
    public List<AuditoriaDto> obtenerTodas() throws ServiciosException;
}
