package codigocreativo.uy.servidorapp.servicios;

import codigocreativo.uy.servidorapp.dtos.BajaUbicacionDto;
import codigocreativo.uy.servidorapp.dtos.UbicacionDto;
import codigocreativo.uy.servidorapp.excepciones.ServiciosException;
import jakarta.ejb.Remote;
import jakarta.transaction.Transactional;

import java.util.List;

@Remote
public interface BajaUbicacionRemote {
    @Transactional
    void crearBajaUbicacion(BajaUbicacionDto bajaUbicacion) throws ServiciosException;

    void borrarUbicacion(Long id) throws ServiciosException;

    public List<BajaUbicacionDto> listarBajaUbicaciones() throws ServiciosException;
    public void bajaLogicaUbicacion(UbicacionDto ub) throws ServiciosException;

}
