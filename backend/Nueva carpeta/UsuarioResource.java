package codigocreativo.uy.servidorapp.ws;

import codigocreativo.uy.servidorapp.PasswordUtils;
import codigocreativo.uy.servidorapp.dtos.UsuarioDto;
import codigocreativo.uy.servidorapp.enumerados.Estados;
import codigocreativo.uy.servidorapp.jwt.JwtService;
import codigocreativo.uy.servidorapp.jwt.LdapService;
import codigocreativo.uy.servidorapp.servicios.UsuarioRemote;
import com.fabdelgado.ciuy.Validator;
import com.google.api.client.json.webtoken.JsonWebSignature;
import com.google.api.client.json.webtoken.JsonWebToken;
import com.google.auth.oauth2.TokenVerifier;
import com.google.auth.oauth2.TokenVerifier.VerificationException;

import io.jsonwebtoken.Claims;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.ejb.EJB;
import jakarta.json.bind.annotation.JsonbProperty;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.*;

@Path("/usuarios")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UsuarioResource {
    @EJB
    private UsuarioRemote er;
    @EJB
    private JwtService jwtService;

    private static final String EMAIL = "email";
    private static final String BEARER = "bearer";

    @POST
    @Path("/crear")
    public Response crearUsuario(UsuarioDto usuario) {
        if (usuario == null) {
            return Response.status(Response.Status.BAD_REQUEST).entity("{\"message\":\"Usuario nulo\"}").build();
        }
        if (usuario.getEmail() == null || usuario.getEmail().isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST).entity("{\"message\":\"El email es obligatorio\"}").build();
        }
        Validator validator = new Validator();
        if (validator.validateCi(usuario.getCedula())){
            return Response.status(Response.Status.BAD_REQUEST).entity("{\"message\":\"Cedula no es válida\"}").build();
        }

        try {

            String saltedHash = PasswordUtils.generateSaltedHash(usuario.getContrasenia());

            usuario.setContrasenia(saltedHash);

            this.er.crearUsuario(usuario);

            return Response.status(201).entity("{\"message\":\"Usuario creado correctamente\"}").build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("{\"message\":\"Error al crear el usuario\"}").build();
        }
    }



    @PUT
    @Path("/modificar")
    public Response modificarUsuario(UsuarioDto usuario) {
        try {
            this.er.modificarUsuario(usuario);
            return Response.status(200).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }


    @PUT
    @Path("/modificar-propio-usuario")
    public Response modificarPropioUsuario(UsuarioDto usuario, @HeaderParam("Authorization") String authorizationHeader) {
        try {

            String token = authorizationHeader.substring(BEARER.length()).trim();
            Claims claims = jwtService.parseToken(token);
            String correoDelToken = claims.getSubject();

            if (!Objects.equals(correoDelToken, usuario.getEmail())) {
                return Response.status(Response.Status.UNAUTHORIZED).entity("{\"message\":\"No autorizado para modificar este usuario\"}").build();
            }

            UsuarioDto usuarioActual = er.obtenerUsuario(usuario.getId());
            if (usuarioActual == null) {
                return Response.status(Response.Status.NOT_FOUND).entity("{\"message\":\"Usuario no encontrado\"}").build();
            }

            if (usuario.getContrasenia() != null && !usuario.getContrasenia().isEmpty()) {

                if (!usuario.getContrasenia().matches("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$")) {

                    return Response.status(Response.Status.BAD_REQUEST).entity("{\"message\":\"La contraseña debe tener al menos 8 caracteres, incluyendo letras y números.\"}").build();
                }

                String saltedHash = PasswordUtils.generateSaltedHash(usuario.getContrasenia());
                usuario.setContrasenia(saltedHash);

            } else {

                usuario.setContrasenia(usuarioActual.getContrasenia());
            }

            usuario.setNombreUsuario(usuarioActual.getNombreUsuario());
            usuario.setIdPerfil(usuarioActual.getIdPerfil());
            usuario.setEstado(usuarioActual.getEstado());
            usuario.setIdInstitucion(usuarioActual.getIdInstitucion());

            er.modificarUsuario(usuario);


            return Response.status(200).entity("{\"message\":\"Usuario modificado correctamente\"}").build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }





    @PUT
    @Path("/inactivar")
    public Response inactivarUsuario(UsuarioDto usuario, @HeaderParam("Authorization") String authorizationHeader) {

        String token = authorizationHeader.substring(BEARER.length()).trim();
        Claims claims = jwtService.parseToken(token);
        String emailSolicitante = claims.getSubject();
        String perfilSolicitante = claims.get("perfil", String.class);

        if (!perfilSolicitante.equals("Administrador")) {
            return Response.status(Response.Status.FORBIDDEN).entity("{\"message\":\"Requiere ser Administrador para inactivar usuarios\"}").build();
        }
        UsuarioDto usuarioAInactivar = this.er.obtenerUsuarioPorCI(usuario.getCedula());

        if (usuarioAInactivar == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("{\"message\":\"Usuario no encontrado\"}").build();
        }

        if (usuarioAInactivar.getEmail().equals(emailSolicitante)) {
            return Response.status(Response.Status.FORBIDDEN).entity("{\"message\":\"No puedes inactivar tu propia cuenta\"}").build();
        }

        if (usuarioAInactivar.getIdPerfil().getNombrePerfil().equals("Administrador")) {
            return Response.status(Response.Status.FORBIDDEN).entity("{\"message\":\"No puedes inactivar a otro administrador\"}").build();
        }

        this.er.eliminarUsuario(usuarioAInactivar);
        return Response.status(200).entity("{\"message\":\"Usuario desactivado correctamente\"}").build();
    }

    @GET
    @Path("/filtrar")
    public List<UsuarioDto> filtrarUsuarios(@QueryParam("nombre") String nombre,
                                            @QueryParam("apellido") String apellido,
                                            @QueryParam("nombreUsuario") String nombreUsuario,
                                            @QueryParam("email") String email,
                                            @QueryParam("perfil") String tipoUsuario,
                                            @QueryParam("estado") String estado) {

        Map<String, String> filtros = new HashMap<>();
        if (nombre != null) filtros.put("nombre", nombre);
        if (apellido != null) filtros.put("apellido", apellido);
        if (nombreUsuario != null) filtros.put("nombreUsuario", nombreUsuario);
        if (email != null) filtros.put(EMAIL, email);

        if (estado == null || estado.isEmpty()) {
            filtros.put("estado", "ACTIVO");
        } else if (!estado.equals("default")) {

            filtros.put("estado", estado);
        }

        if (tipoUsuario != null && !tipoUsuario.isEmpty() && !tipoUsuario.equals("default")) {
            filtros.put("tipoUsuario", tipoUsuario);
        }

        return this.er.obtenerUsuariosFiltrado(filtros);
    }



    @GET
    @Path("/BuscarUsuarioPorCI")
    public UsuarioDto buscarUsuario(@QueryParam("ci") String ci){
        return this.er.obtenerUsuarioPorCI(ci);
    }

    @GET
    @Path("/seleccionar")
    public UsuarioDto buscarUsuario(@QueryParam("id") Long id){
        return this.er.obtenerUsuario(id);
    }

    @GET
    @Path("/ObtenerUsuarioPorEstado")
    public List<UsuarioDto> obtenerUsuarioPorEstado(@QueryParam("estado") Estados estado){
        return this.er.obtenerUsuariosPorEstado(estado);
    }

    @GET
    @Path("/listar")
    public List<UsuarioDto> obtenerTodosLosUsuarios() {
        return this.er.obtenerUsuarios();
    }

    @GET
    @Path("/obtenerUserEmail")
    public Response getUserByEmail(@QueryParam("email") String email) {
        UsuarioDto user = er.findUserByEmail(email);
        if (user != null) {
            return Response.ok(user).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @POST
    @Path("/login-ldap")
    @Operation(summary = "Login con AD", description = "Permite autenticar usuarios contra el Active Directory usando LDAP", tags = { "Usuarios" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Login exitoso", content = @Content(schema = @Schema(implementation = LoginResponse.class))),
            @ApiResponse(responseCode = "401", description = "No autorizado", content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "500", description = "Error interno", content = @Content(schema = @Schema(implementation = String.class)))
    })
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response loginLdap(LoginRequest loginRequest) {
        if (loginRequest == null || loginRequest.getEmail() == null || loginRequest.getPassword() == null) {
            return Response.status(Response.Status.BAD_REQUEST).entity("{\"error\":\"Solicitud incompleta\"}").build();
        }

        try {
            LdapService ldapService = new LdapService();
            boolean enAd = ldapService.usuarioExistePorPrincipal(loginRequest.getEmail());
            if (!enAd) {
                return Response.status(Response.Status.UNAUTHORIZED)
                        .entity("{\"error\":\"Usuario no pertenece al AD\"}").build();
            }

            UsuarioDto user = er.login(loginRequest.getEmail(), loginRequest.getPassword());
            if (user == null || !user.getEstado().equals(Estados.ACTIVO)) {
                return Response.status(Response.Status.UNAUTHORIZED)
                        .entity("{\"error\":\"Credenciales incorrectas o cuenta inactiva\"}").build();
            }

            user.setContrasenia(null);
            String token = jwtService.generateToken(user.getEmail(), user.getIdPerfil().getNombrePerfil());
            return Response.ok(new LoginResponse(token, user)).build();

        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"error\":\"Error en login-ldap: " + e.getMessage() + "\"}").build();
        }
    }


    @POST
    @Path("/login")

    public Response login(LoginRequest loginRequest) {
        if (loginRequest == null) {
            return Response.status(Response.Status.BAD_REQUEST).entity("{\"error\":\"Pedido de login nulo\"}").build();
        }

        UsuarioDto user = this.er.findUserByEmail(loginRequest.getEmail());

        if (user != null && user.getEstado().equals(Estados.ACTIVO)) {
            try {

                boolean isValid = PasswordUtils.verifyPassword(loginRequest.getPassword(), user.getContrasenia());

                if (!isValid) {
                    return Response.status(Response.Status.UNAUTHORIZED).entity("{\"error\":\"Contraseña incorrecta\"}").build();
                }

                String token = jwtService.generateToken(user.getEmail(), user.getIdPerfil().getNombrePerfil());
                LoginResponse loginResponse = new LoginResponse(token, user);

                return Response.ok(loginResponse).build();
            } catch (Exception e) {
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("{\"error\":\"Error durante el login\"}").build();
            }
        } else {
            return Response.status(Response.Status.UNAUTHORIZED).entity("{\"error\":\"Credenciales incorrectas o cuenta inactiva\"}").build();
        }
    }



    @POST
    @Path("/google-login")
    public Response googleLogin(GoogleLoginRequest googleLoginRequest) {
        if (googleLoginRequest.getIdToken() == null) {
            return Response.status(Response.Status.BAD_REQUEST).entity("{\"error\":\"Token de Google nulo\"}").build();
        }
        try {

            String idTokenString = googleLoginRequest.getIdToken();
            TokenVerifier tokenVerifier = TokenVerifier.newBuilder()
                    .setAudience("103181333646-gp6uip6g6k1rg6p52tsidphj3gt22qut.apps.googleusercontent.com")
                    .build();

            JsonWebSignature idToken = tokenVerifier.verify(idTokenString);
            if (idToken == null) {
                return Response.status(Response.Status.UNAUTHORIZED).entity("{\"error\":\"Token de Google inválido\"}").build();
            }

            JsonWebToken.Payload payload = idToken.getPayload();
            String email = (String) payload.get(EMAIL);
            String name = (String) payload.get("name");

            UsuarioDto user = er.findUserByEmail(email);
            boolean userNeedsAdditionalInfo = false;

            if (user == null) {

                user = new UsuarioDto();
                user.setEmail(email);
                user.setNombre(name);
                userNeedsAdditionalInfo = true;
            } else if (!user.getEstado().equals(Estados.ACTIVO)) {

                return Response.status(Response.Status.FORBIDDEN).entity("{\"error\":\"Cuenta inactiva, por favor contacte al administrador\"}").build();
            }

            String perfilNombre = (user.getIdPerfil() != null) ? user.getIdPerfil().getNombrePerfil() : "Usuario";
            String token = jwtService.generateToken(email, perfilNombre);  // Generar un nuevo JWT válido para este usuario

            GoogleLoginResponse loginResponse = new GoogleLoginResponse(token, userNeedsAdditionalInfo, user);
            return Response.ok(loginResponse).build();

        } catch (VerificationException e) {
            return Response.status(Response.Status.UNAUTHORIZED).entity("{\"error\":\"Token de Google inválido\"}").build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("{\"error\":\"Error al procesar el token de Google\"}").build();
        }
    }


    @POST
    @Path("/renovar-token")
    public Response renovarToken(@HeaderParam("Authorization") String authorizationHeader) {

        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"error\": \"Falta el token de autorización.\"}")
                    .build();
        }

        try {
            String token = authorizationHeader.substring(BEARER.length()).trim();
            Claims claims = jwtService.parseToken(token);

            if (claims.getExpiration().before(new Date())) {
                return Response.status(Response.Status.UNAUTHORIZED)
                        .entity("{\"error\": \"El token ha expirado.\"}")
                        .build();
            }

            String email = claims.get(EMAIL, String.class);
            String perfil = claims.get("perfil", String.class);

            String nuevoToken = jwtService.generateToken(email, perfil);

            Map<String, String> responseMap = new HashMap<>();
            responseMap.put("token", nuevoToken);

            return Response.ok(responseMap).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"error\": \"Error al procesar el token.\"}")
                    .build();
        }
    }






    public static class LoginRequest {
        @JsonbProperty("email")
        private String email;

        @JsonbProperty("password")
        private String password;

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }


    public static class LoginResponse {
        private String token;
        private UsuarioDto user;

        public LoginResponse(String token, UsuarioDto user) {
            this.token = token;
            this.user = user;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public UsuarioDto getUser() {
            return user;
        }

        public void setUser(UsuarioDto user) {
            this.user = user;
        }
    }

    public static class GoogleLoginRequest {
        private String idToken;

        public String getIdToken() {
            return idToken;
        }

        public void setIdToken(String idToken) {
            this.idToken = idToken;
        }
    }


    public static class GoogleLoginResponse {
        private String token;
        private boolean userNeedsAdditionalInfo;
        private UsuarioDto user; // Añade este campo

        public GoogleLoginResponse(String token, boolean userNeedsAdditionalInfo, UsuarioDto user) {
            this.token = token;
            this.userNeedsAdditionalInfo = userNeedsAdditionalInfo;
            this.user = user;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public boolean isUserNeedsAdditionalInfo() {
            return userNeedsAdditionalInfo;
        }

        public void setUserNeedsAdditionalInfo(boolean userNeedsAdditionalInfo) {
            this.userNeedsAdditionalInfo = userNeedsAdditionalInfo;
        }

        public UsuarioDto getUser() {
            return user;
        }

        public void setUser(UsuarioDto user) {
            this.user = user;
        }
    }
}
