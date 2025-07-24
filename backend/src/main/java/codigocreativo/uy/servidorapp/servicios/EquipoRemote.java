package codigocreativo.uy.servidorapp.servicios;

import codigocreativo.uy.servidorapp.dtos.BajaEquipoDto;
import codigocreativo.uy.servidorapp.dtos.EquipoDto;
import codigocreativo.uy.servidorapp.excepciones.ServiciosException;
import jakarta.ejb.Remote;

import java.util.List;
import java.util.Map;

@Remote
public interface EquipoRemote {
    public void crearEquipo(EquipoDto equipo) throws ServiciosException;
    public void modificarEquipo(EquipoDto equipo);
    public void eliminarEquipo(BajaEquipoDto bajaEquipo);

    List<EquipoDto> obtenerEquiposFiltrado(Map<String, String> filtros);

    public EquipoDto obtenerEquipo(Long id);
    public List<EquipoDto> listarEquipos();

}