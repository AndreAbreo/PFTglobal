package codigocreativo.uy.servidorapp.servicios;

import codigocreativo.uy.servidorapp.dtos.FuncionalidadDto;
import codigocreativo.uy.servidorapp.excepciones.ServiciosException;

import jakarta.ejb.Remote;

import java.util.List;

@Remote
public interface FuncionalidadRemote {

    List<FuncionalidadDto> obtenerTodas();

    FuncionalidadDto crear(FuncionalidadDto funcionalidad);

    FuncionalidadDto actualizar(FuncionalidadDto funcionalidad);

    void eliminar(Long id) throws ServiciosException;

    FuncionalidadDto buscarPorId(Long id);

    /**
     * Filtra las funcionalidades por nombre y/o estado.
     *
     * @param nombre nombre de la funcionalidad
     * @param estado estado de la funcionalidad (ACTIVO, INACTIVO, ...)
     * @return lista filtrada
     */
    List<FuncionalidadDto> filtrarFuncionalidades(String nombre, String estado);

}
