package codigocreativo.uy.servidorapp.servicios;

import codigocreativo.uy.servidorapp.dtos.IntervencionDto;
import codigocreativo.uy.servidorapp.excepciones.ServiciosException;
import jakarta.ejb.Remote;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Remote
public interface IntervencionRemote {
    // CRUD para intervenciones
    public void crear(IntervencionDto intervencion) throws ServiciosException;
    public void actualizar(IntervencionDto intervencion) throws ServiciosException;
    public List<IntervencionDto> obtenerTodas() throws ServiciosException;
    //obtener intervencion por id
    public IntervencionDto buscarId(Long id) throws ServiciosException;

    List<IntervencionDto> obtenerPorRangoDeFecha(LocalDateTime fechaDesde, LocalDateTime fechaHasta, Long idEquipo) throws ServiciosException;
    Map<String, Long> obtenerCantidadPorTipo(LocalDateTime fechaDesde, LocalDateTime fechaHasta, Long idTipo) throws ServiciosException;

    /**
     * Obtiene las intervenciones aplicando filtros opcionales
     *
     * @param technician nombre o usuario del técnico que realizó la intervención
     * @param type       nombre del tipo de intervención
     * @param estado     estado del técnico
     * @return lista de intervenciones que cumplen con los filtros
     */
    List<IntervencionDto> filtrarIntervenciones(String technician, String type, String estado) throws ServiciosException;
}
