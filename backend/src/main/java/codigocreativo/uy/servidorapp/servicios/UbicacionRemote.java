package codigocreativo.uy.servidorapp.servicios;

import codigocreativo.uy.servidorapp.dtos.EquipoDto;
import codigocreativo.uy.servidorapp.dtos.UbicacionDto;
import codigocreativo.uy.servidorapp.excepciones.ServiciosException;
import jakarta.ejb.Remote;

import java.util.List;

@Remote
public interface UbicacionRemote {

    void crearUbicacion(UbicacionDto ubi) throws ServiciosException;

    void modificarUbicacion(UbicacionDto ubi) throws ServiciosException;

    void borrarUbicacion(Long id) throws ServiciosException;

    void moverEquipoDeUbicacion(EquipoDto equipo, UbicacionDto ubicacion) throws ServiciosException;

    public List<UbicacionDto> listarUbicaciones() throws ServiciosException;

    public UbicacionDto obtenerUbicacionPorId(Long id) throws ServiciosException;

    void bajaLogicaUbicacion(UbicacionDto ub) throws ServiciosException;
}
