package codigocreativo.uy.servidorapp.servicios;

import codigocreativo.uy.servidorapp.dtos.ProveedoresEquipoDto;
import codigocreativo.uy.servidorapp.enumerados.Estados;
import jakarta.ejb.Remote;

import java.util.List;

@Remote
public interface ProveedoresEquipoRemote {
    void crearProveedor(ProveedoresEquipoDto proveedoresEquipo);
    void modificarProveedor(ProveedoresEquipoDto proveedoresEquipo);
    ProveedoresEquipoDto obtenerProveedor(Long id);
    List<ProveedoresEquipoDto> obtenerProveedores();
    void eliminarProveedor(Long id);
    List<ProveedoresEquipoDto> buscarProveedores(String nombre, Estados estado);
    List<ProveedoresEquipoDto> filtrarProveedores(String nombre, String estado);
}