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

}
