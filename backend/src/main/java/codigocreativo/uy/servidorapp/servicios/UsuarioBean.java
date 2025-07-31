package codigocreativo.uy.servidorapp.servicios;

import codigocreativo.uy.servidorapp.dtos.UsuarioDto;
import codigocreativo.uy.servidorapp.dtos.dtomappers.CycleAvoidingMappingContext;
import codigocreativo.uy.servidorapp.dtos.dtomappers.UsuarioMapper;
import codigocreativo.uy.servidorapp.entidades.Usuario;
import codigocreativo.uy.servidorapp.enumerados.Estados;
import codigocreativo.uy.servidorapp.excepciones.ServiciosException;
import codigocreativo.uy.servidorapp.PasswordUtils;
import com.fabdelgado.ciuy.Validator;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Stateless
public class UsuarioBean implements UsuarioRemote {
    @PersistenceContext (unitName = "default")
    private EntityManager em;
    private final UsuarioMapper usuarioMapper;

    @Inject
    public UsuarioBean(UsuarioMapper usuarioMapper) {
        this.usuarioMapper = usuarioMapper;
    }

    private static final String EMAIL = "email";
    private static final String ESTADO = "estado";
    private static final int EDAD_MINIMA = 18;
    private static final String QUERY_USUARIO_POR_EMAIL = "SELECT u FROM Usuario u WHERE u.email = :email";
    private static final String ADMINISTRADOR = "Administrador";
    private static final String SUPER_ADMIN = "SuperAdmin";

    @Override
    public void crearUsuario(UsuarioDto u) throws ServiciosException {

        if (u == null) {
            throw new ServiciosException("Usuario nulo");
        }

        validarMayorDeEdad(u.getFechaNacimiento());

        validarEmailUnico(u.getEmail());

        validarFormatoCedula(u.getCedula());

        validarCedulaUnica(u.getCedula());

        if (u.getContrasenia() == null || u.getContrasenia().trim().isEmpty()) {
            throw new ServiciosException("La contraseña es obligatoria");
        }
        
        u.setEstado(Estados.SIN_VALIDAR);
        em.merge(usuarioMapper.toEntity(u, new CycleAvoidingMappingContext()));
    }

    
    private void validarMayorDeEdad(LocalDate fechaNacimiento) throws ServiciosException {
        if (fechaNacimiento == null) {
            throw new ServiciosException("La fecha de nacimiento es obligatoria");
        }
        
        LocalDate fechaActual = LocalDate.now();
        Period edad = Period.between(fechaNacimiento, fechaActual);
        
        if (edad.getYears() < EDAD_MINIMA) {
            throw new ServiciosException("El usuario debe ser mayor de edad (mínimo " + EDAD_MINIMA + " años)");
        }
    }

    
    private void validarEmailUnico(String email) throws ServiciosException {
        if (email == null || email.trim().isEmpty()) {
            throw new ServiciosException("El email es obligatorio");
        }
        
        try {
            em.createQuery(QUERY_USUARIO_POR_EMAIL, Usuario.class)
                    .setParameter(EMAIL, email.trim())
                    .getSingleResult();
            throw new ServiciosException("Ya existe un usuario con el email: " + email);
        } catch (NoResultException e) {

        }
    }

    
    private void validarFormatoCedula(String cedula) throws ServiciosException {
        if (cedula == null || cedula.trim().isEmpty()) {
            throw new ServiciosException("La cédula es obligatoria");
        }
        
        String cedulaLimpia = cedula.trim();

        if (!cedulaLimpia.matches("\\d+")) {
            throw new ServiciosException("La cédula debe contener solo números: " + cedula);
        }

        if (cedulaLimpia.length() > 8) {
            throw new ServiciosException("La cédula no puede tener más de 8 dígitos: " + cedula);
        }
        
        Validator validator = new Validator();
        
        try {

            if (!validator.validateCi(cedulaLimpia)) {
                throw new ServiciosException("La cédula no es válida: " + cedula);
            }
        } catch (StringIndexOutOfBoundsException e) {

            throw new ServiciosException("La cédula tiene un formato inválido: " + cedula);
        } catch (Exception e) {

            throw new ServiciosException("Error al validar la cédula: " + cedula);
        }
    }

    
    private void validarCedulaUnica(String cedula) throws ServiciosException {
        try {
            em.createQuery("SELECT u FROM Usuario u WHERE u.cedula = :cedula", Usuario.class)
                    .setParameter("cedula", cedula.trim())
                    .getSingleResult();
            throw new ServiciosException("Ya existe un usuario con la cédula: " + cedula);
        } catch (NoResultException e) {

        }
    }

    @Override
    public void modificarUsuario(UsuarioDto u) {
        em.merge(usuarioMapper.toEntity(u, new CycleAvoidingMappingContext()));
        em.flush();
    }

    @Override
    public void eliminarUsuario(UsuarioDto u) {
        em.createQuery("UPDATE Usuario u SET u.estado = 'INACTIVO' WHERE u.id = :id")
                .setParameter("id", u.getId())
                .executeUpdate();
        em.flush();
    }

    @Override
    public UsuarioDto obtenerUsuario(Long id) {
        return usuarioMapper.toDto(em.find(Usuario.class, id), new CycleAvoidingMappingContext());
    }

    @Override
    public UsuarioDto obtenerUsuarioDto(Long id) {
        return usuarioMapper.toDto(em.createQuery("SELECT u FROM Usuario u WHERE u.id = :id", Usuario.class)
                .setParameter("id", id)
                .getSingleResult(), new CycleAvoidingMappingContext());
    }

    @Override
    public UsuarioDto obtenerUsuarioPorCI(String ci) {
        return usuarioMapper.toDto(em.createQuery("SELECT u FROM Usuario u WHERE u.cedula = :ci", Usuario.class)
                .setParameter("ci", ci)
                .getSingleResult(), new CycleAvoidingMappingContext());
    }

    @Override
    public List<UsuarioDto> obtenerUsuarios() {
        return usuarioMapper.toDto(em.createQuery("SELECT u FROM Usuario u", Usuario.class).getResultList(), new CycleAvoidingMappingContext());
    }

    @Override
    public List<UsuarioDto> obtenerUsuariosFiltrados(String filtro, Object valor) {
        return usuarioMapper.toDto(em.createQuery("SELECT u FROM Usuario u WHERE u." + filtro + " = :valor", Usuario.class)
                .setParameter("valor", valor)
                .getResultList(), new CycleAvoidingMappingContext());
    }

    @Override
    public List<UsuarioDto> obtenerUsuariosPorEstado(Estados estado) {
        return usuarioMapper.toDto(em.createQuery("SELECT u FROM Usuario u WHERE u.estado = :estado", Usuario.class)
                .setParameter(ESTADO, estado)
                .getResultList(), new CycleAvoidingMappingContext());
    }

    @Override
    public UsuarioDto login(String usuario, String password) {
        try {

            Usuario user = em.createQuery("SELECT u FROM Usuario u WHERE u.email = :usuario", Usuario.class)
                    .setParameter("usuario", usuario)
                    .getSingleResult();

            if (user.getContrasenia() != null && PasswordUtils.verifyPassword(password, user.getContrasenia())) {
                UsuarioDto userDto = usuarioMapper.toDto(user, new CycleAvoidingMappingContext());

                userDto.setContrasenia(null);
                return userDto;
            }
            
            return null;
        } catch (NoResultException | NoSuchAlgorithmException | InvalidKeySpecException e) {
            return null;
        }
    }

    @Override
    public UsuarioDto findUserByEmail(String email) {
        try {
            return usuarioMapper.toDto(em.createQuery("SELECT u FROM Usuario u WHERE u.email = :email", Usuario.class)
                    .setParameter(EMAIL, email)
                    .getSingleResult(), new CycleAvoidingMappingContext());
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public boolean hasPermission(String email, String requiredRole) {
        try {
            UsuarioDto user = findUserByEmail(email);
            return user != null && user.getIdPerfil().getNombrePerfil().equals(requiredRole);
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public List<UsuarioDto> obtenerUsuariosFiltrado(Map<String, String> filtros) {
        StringBuilder queryBuilder = new StringBuilder("SELECT u FROM Usuario u WHERE 1=1");

        if (filtros.containsKey("cedula")) {
            queryBuilder.append(" AND LOWER(u.cedula) LIKE LOWER(:cedula)");
        }
        if (filtros.containsKey("nombre")) {
            queryBuilder.append(" AND LOWER(u.nombre) LIKE LOWER(:nombre)");
        }
        if (filtros.containsKey("apellido")) {
            queryBuilder.append(" AND LOWER(u.apellido) LIKE LOWER(:apellido)");
        }
        if (filtros.containsKey("nombreUsuario")) {
            queryBuilder.append(" AND LOWER(u.nombreUsuario) LIKE LOWER(:nombreUsuario)");
        }
        if (filtros.containsKey(EMAIL)) {
            queryBuilder.append(" AND LOWER(u.email) LIKE LOWER(:email)");
        }

        boolean estadoValido = false;
        Estados estadoEnum = null;
        if (filtros.containsKey("estado")) {
            try {
                String estadoStr = filtros.get("estado");
                estadoEnum = Estados.valueOf(estadoStr.toUpperCase());
                queryBuilder.append(" AND u.estado = :estado");
                estadoValido = true;
            } catch (IllegalArgumentException e) {

            }
        }

        if (filtros.containsKey("tipoUsuario")) {
            queryBuilder.append(" AND u.idPerfil.nombrePerfil = :tipoUsuario");
        }

        var query = em.createQuery(queryBuilder.toString(), Usuario.class);

        if (filtros.containsKey("cedula")) {
            query.setParameter("cedula", "%" + filtros.get("cedula") + "%");
        }
        if (filtros.containsKey("nombre")) {
            query.setParameter("nombre", "%" + filtros.get("nombre") + "%");
        }
        if (filtros.containsKey("apellido")) {
            query.setParameter("apellido", "%" + filtros.get("apellido") + "%");
        }
        if (filtros.containsKey("nombreUsuario")) {
            query.setParameter("nombreUsuario", "%" + filtros.get("nombreUsuario") + "%");
        }
        if (filtros.containsKey(EMAIL)) {
            query.setParameter(EMAIL, "%" + filtros.get(EMAIL) + "%");
        }
        if (estadoValido) {
            query.setParameter("estado", estadoEnum);
        }
        if (filtros.containsKey("tipoUsuario")) {
            query.setParameter("tipoUsuario", filtros.get("tipoUsuario"));
        }

        return usuarioMapper.toDto(query.getResultList(), new CycleAvoidingMappingContext());
    }


    
    public void validarContrasenia(String contrasenia) throws ServiciosException {
        List<String> errores = new ArrayList<>();

        if (contrasenia == null || contrasenia.trim().isEmpty()) {
            throw new ServiciosException("La contraseña no puede estar vacía");
        }

        if (contrasenia.length() < 8) {
            errores.add("Debe tener al menos 8 caracteres");
        }

        if (!contrasenia.matches(".*[A-Z].*")) {
            errores.add("Debe tener al menos una letra mayúscula");
        }

        if (!contrasenia.matches(".*[a-z].*")) {
            errores.add("Debe tener al menos una letra minúscula");
        }

        if (!contrasenia.matches(".*\\d.*")) {
            errores.add("Debe tener al menos un número");
        }

        if (!contrasenia.matches(".*[!@#$%^&*()_+\\-=\\[\\]{}|;':\",./<>?].*")) {
            errores.add("Debe tener al menos un carácter especial (como ! @ # $ etc)");
        }

        if (!errores.isEmpty()) {
            String mensaje = "La contraseña no es válida:\n - " + String.join("\n - ", errores);
            throw new ServiciosException(mensaje);
        }

        System.out.println("✅ Contraseña válida: " + contrasenia);
    }


    
    public void validarInactivacionUsuario(String emailSolicitante, String cedulaUsuarioAInactivar) throws ServiciosException {

        UsuarioDto solicitante = findUserByEmail(emailSolicitante);
        if (solicitante == null) {
            throw new ServiciosException("Usuario solicitante no encontrado");
        }

        String perfilSolicitante = solicitante.getIdPerfil().getNombrePerfil();
        if (!perfilSolicitante.equals(ADMINISTRADOR) && !perfilSolicitante.equals(SUPER_ADMIN) && !perfilSolicitante.equals("Aux Administrativo")) {
            throw new ServiciosException("Requiere ser Administrador o Aux Administrativo para inactivar usuarios");
        }

        UsuarioDto usuarioAInactivar = obtenerUsuarioPorCI(cedulaUsuarioAInactivar);
        if (usuarioAInactivar == null) {
            throw new ServiciosException("Usuario a inactivar no encontrado");
        }

        if (usuarioAInactivar.getEmail().equals(emailSolicitante)) {
            throw new ServiciosException("No puedes inactivar tu propia cuenta");
        }

        if (usuarioAInactivar.getIdPerfil().getNombrePerfil().equals(ADMINISTRADOR) ||
                usuarioAInactivar.getIdPerfil().getNombrePerfil().equals(SUPER_ADMIN)) {
            throw new ServiciosException("No puedes inactivar a otro administrador");
        }
    }
    
    
    public void inactivarUsuario(String emailSolicitante, String cedulaUsuarioAInactivar) throws ServiciosException {
        validarInactivacionUsuario(emailSolicitante, cedulaUsuarioAInactivar);
        
        UsuarioDto usuarioAInactivar = obtenerUsuarioPorCI(cedulaUsuarioAInactivar);
        eliminarUsuario(usuarioAInactivar);
    }
    
    
    public List<UsuarioDto> obtenerUsuariosSinContrasenia() {
        List<UsuarioDto> usuarios = obtenerUsuarios();
        for (UsuarioDto usuario : usuarios) {
            usuario.setContrasenia(null);
        }
        return usuarios;
    }
    
    
    public List<UsuarioDto> filtrarUsuariosSinContrasenia(Map<String, String> filtros) {
        List<UsuarioDto> usuarios = obtenerUsuariosFiltrado(filtros);
        for (UsuarioDto usuario : usuarios) {
            usuario.setContrasenia(null);
        }
        return usuarios;
    }
    
    
    public void validarModificacionPropia(String emailToken, Long idUsuario) throws ServiciosException {
        UsuarioDto usuario = obtenerUsuario(idUsuario);
        if (usuario == null) {
            throw new ServiciosException("Usuario no encontrado");
        }
        
        if (!usuario.getEmail().equals(emailToken)) {
            throw new ServiciosException("No autorizado para modificar este usuario");
        }
    }
    
    
    public void validarModificacionPorAdministrador(String emailSolicitante, Long idUsuario) throws ServiciosException {
        UsuarioDto solicitante = findUserByEmail(emailSolicitante);
        if (solicitante == null) {
            throw new ServiciosException("Usuario solicitante no encontrado");
        }
        
        if (!solicitante.getIdPerfil().getNombrePerfil().equals(ADMINISTRADOR)
                && !solicitante.getIdPerfil().getNombrePerfil().equals(SUPER_ADMIN)) {
            throw new ServiciosException("Solo los administradores pueden modificar usuarios");
        }
        
        UsuarioDto usuarioAModificar = obtenerUsuario(idUsuario);
        if (usuarioAModificar == null) {
            throw new ServiciosException("Usuario a modificar no encontrado");
        }
        
        if (usuarioAModificar.getEmail().equals(emailSolicitante)) {
            throw new ServiciosException("No puedes modificar tu propio usuario desde este endpoint. Usa el endpoint de modificación propia");
        }
    }
}
