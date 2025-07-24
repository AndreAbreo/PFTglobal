package codigocreativo.uy.servidorapp.servicios;

import codigocreativo.uy.servidorapp.entidades.UsuariosTelefono;
import jakarta.ejb.Remote;

import java.util.List;

@Remote
public interface UsuariosTelefonoRemote {
    void crearUsuariosTelefono(UsuariosTelefono usuariosTelefono);void modificarUsuariosTelefono(UsuariosTelefono usuariosTelefono);
    void obtenerUsuarioTelefono(Long id);
    List<UsuariosTelefono> obtenerusuariosTelefono();

    void eliminarTelefono(UsuariosTelefono usuariosTelefono);
}
