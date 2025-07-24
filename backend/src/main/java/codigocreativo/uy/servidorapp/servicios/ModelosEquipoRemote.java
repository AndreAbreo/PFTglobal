package codigocreativo.uy.servidorapp.servicios;

import codigocreativo.uy.servidorapp.dtos.ModelosEquipoDto;
import codigocreativo.uy.servidorapp.excepciones.ServiciosException;
import jakarta.ejb.Remote;

import java.util.List;

@Remote
public interface ModelosEquipoRemote {
    void crearModelos(ModelosEquipoDto modelosEquipo) throws ServiciosException;
    void modificarModelos(ModelosEquipoDto modelosEquipo) throws ServiciosException;
    ModelosEquipoDto obtenerModelos(Long id) throws ServiciosException;
    List<ModelosEquipoDto> listarModelos();
    void eliminarModelos(Long id) throws ServiciosException;
}