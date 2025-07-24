package codigocreativo.uy.servidorapp.servicios;

import codigocreativo.uy.servidorapp.dtos.EquiposUbicacioneDto;
import jakarta.ejb.Remote;

import java.util.List;

@Remote
public interface EquiposUbicacioneRemote {
    public void crearEquiposUbicacione(EquiposUbicacioneDto equiposUbicacione);
    public List<EquiposUbicacioneDto> obtenerEquiposUbicacione();
    List<EquiposUbicacioneDto> obtenerEquiposUbicacionePorEquipo(Long id);
}
