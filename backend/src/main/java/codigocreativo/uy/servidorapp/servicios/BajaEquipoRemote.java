package codigocreativo.uy.servidorapp.servicios;

import codigocreativo.uy.servidorapp.dtos.BajaEquipoDto;
import codigocreativo.uy.servidorapp.excepciones.ServiciosException;
import jakarta.ejb.Remote;

import java.util.List;

@Remote
public interface BajaEquipoRemote {
    void crearBajaEquipo(BajaEquipoDto bajaEquipo, String emailUsuario) throws ServiciosException;
    List<BajaEquipoDto> obtenerBajasEquipos();
    BajaEquipoDto obtenerBajaEquipo(Long id);
}
