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
    /**
     * Filtra los modelos por nombre y/o estado.
     *
     * @param nombre nombre del modelo, puede ser parcial
     * @param estado estado del modelo (ACTIVO, INACTIVO, ...)
     * @return lista de modelos que cumplen con el filtro
     */
    List<ModelosEquipoDto> filtrarModelos(String nombre, String estado);
}