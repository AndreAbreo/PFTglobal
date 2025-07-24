package codigocreativo.uy.servidorapp.servicios;

import codigocreativo.uy.servidorapp.dtos.UsuarioDto;
import codigocreativo.uy.servidorapp.enumerados.Estados;
import codigocreativo.uy.servidorapp.excepciones.ServiciosException;

import java.util.List;
import java.util.Map;

public interface UsuarioRemote {
    void crearUsuario(UsuarioDto u) throws ServiciosException;
    void modificarUsuario(UsuarioDto u);
    void eliminarUsuario(UsuarioDto u);
    UsuarioDto obtenerUsuario(Long id);
    UsuarioDto obtenerUsuarioDto(Long id);
    UsuarioDto obtenerUsuarioPorCI(String ci);
    List<UsuarioDto> obtenerUsuarios();
    List<UsuarioDto> obtenerUsuariosFiltrado(Map<String, String> filtros);
    List<UsuarioDto> obtenerUsuariosFiltrados(String filtro, Object valor);
    List<UsuarioDto> obtenerUsuariosPorEstado(Estados estado);
    UsuarioDto login(String usuario, String password);
    UsuarioDto findUserByEmail(String email);
    boolean hasPermission(String email, String requiredRole);
    
    // Nuevos métodos para lógica de negocio
    void validarContrasenia(String contrasenia) throws ServiciosException;
    void validarInactivacionUsuario(String emailSolicitante, String cedulaUsuarioAInactivar) throws ServiciosException;
    void inactivarUsuario(String emailSolicitante, String cedulaUsuarioAInactivar) throws ServiciosException;
    List<UsuarioDto> obtenerUsuariosSinContrasenia();
    List<UsuarioDto> filtrarUsuariosSinContrasenia(Map<String, String> filtros);
    void validarModificacionPropia(String emailToken, Long idUsuario) throws ServiciosException;
    void validarModificacionPorAdministrador(String emailSolicitante, Long idUsuario) throws ServiciosException;
}
