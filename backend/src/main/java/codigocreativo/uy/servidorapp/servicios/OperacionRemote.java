package codigocreativo.uy.servidorapp.servicios;

import codigocreativo.uy.servidorapp.dtos.OperacionDto;
import jakarta.ejb.Remote;

@Remote
public interface OperacionRemote {
    public void crearOperacion(OperacionDto o);
    public void modificarOperacion (OperacionDto o);
    
    public void eliminarOperacion (OperacionDto o);
    
}
