package codigocreativo.uy.servidorapp.dtos;

import codigocreativo.uy.servidorapp.entidades.Usuario;
import codigocreativo.uy.servidorapp.enumerados.Estados;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;


@JsonIgnoreProperties({"usuarios"})
public class UsuarioDto implements Serializable {

    @JsonManagedReference
    private Set<UsuariosTelefonoDto> usuariosTelefonos = new LinkedHashSet<>();
    private Long id;
    private String cedula;
    private String email;
    private String contrasenia;
    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate fechaNacimiento;
    private Estados estado;
    private String nombre;
    private String apellido;
    private String nombreUsuario;
    private InstitucionDto idInstitucion;
    private PerfilDto idPerfil;

    public UsuarioDto() {
    }

    private UsuarioDto(Builder builder) {
        this.id = builder.id;
        this.cedula = builder.cedula;
        this.email = builder.email;
        this.contrasenia = builder.contrasenia;
        this.fechaNacimiento = builder.fechaNacimiento;
        this.estado = builder.estado;
        this.nombre = builder.nombre;
        this.apellido = builder.apellido;
        this.nombreUsuario = builder.nombreUsuario;
        this.idInstitucion = builder.idInstitucion;
        this.idPerfil = builder.idPerfil;
        this.usuariosTelefonos = builder.usuariosTelefonos;
    }

    public static class Builder {
        private Long id;
        private String cedula;
        private String email;
        private String contrasenia;
        private LocalDate fechaNacimiento;
        private Estados estado;
        private String nombre;
        private String apellido;
        private String nombreUsuario;
        private InstitucionDto idInstitucion;
        private PerfilDto idPerfil;
        private Set<UsuariosTelefonoDto> usuariosTelefonos = new LinkedHashSet<>();

        public Builder setId(Long id) {
            this.id = id;
            return this;
        }

        public Builder setCedula(String cedula) {
            this.cedula = cedula;
            return this;
        }

        public Builder setEmail(String email) {
            this.email = email;
            return this;
        }

        public Builder setContrasenia(String contrasenia) {
            this.contrasenia = contrasenia;
            return this;
        }

        public Builder setFechaNacimiento(LocalDate fechaNacimiento) {
            this.fechaNacimiento = fechaNacimiento;
            return this;
        }

        public Builder setEstado(Estados estado) {
            this.estado = estado;
            return this;
        }

        public Builder setNombre(String nombre) {
            this.nombre = nombre;
            return this;
        }

        public Builder setApellido(String apellido) {
            this.apellido = apellido;
            return this;
        }

        public Builder setNombreUsuario(String nombreUsuario) {
            this.nombreUsuario = nombreUsuario;
            return this;
        }

        public Builder setIdInstitucion(InstitucionDto idInstitucion) {
            this.idInstitucion = idInstitucion;
            return this;
        }

        public Builder setIdPerfil(PerfilDto idPerfil) {
            this.idPerfil = idPerfil;
            return this;
        }

        public Builder setUsuariosTelefonos(Set<UsuariosTelefonoDto> usuariosTelefonos) {
            this.usuariosTelefonos = usuariosTelefonos;
            return this;
        }

        public UsuarioDto build() {
            return new UsuarioDto(this);
        }
    }

    public Long getId() {
        return id;
    }

    public UsuarioDto setId(Long id) {
        this.id = id;
        return this;
    }

    public String getCedula() {
        return cedula;
    }

    public UsuarioDto setCedula(String cedula) {
        this.cedula = cedula;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public UsuarioDto setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public UsuarioDto setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
        return this;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public UsuarioDto setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
        return this;
    }

    public Estados getEstado() {
        return estado;
    }

    public UsuarioDto setEstado(Estados estado) {
        this.estado = estado;
        return this;
    }

    public String getNombre() {
        return nombre;
    }

    public UsuarioDto setNombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public String getApellido() {
        return apellido;
    }

    public UsuarioDto setApellido(String apellido) {
        this.apellido = apellido;
        return this;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public UsuarioDto setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UsuarioDto entity = (UsuarioDto) o;
        return Objects.equals(this.id, entity.id) &&
                Objects.equals(this.cedula, entity.cedula) &&
                Objects.equals(this.email, entity.email) &&
                Objects.equals(this.contrasenia, entity.contrasenia) &&
                Objects.equals(this.fechaNacimiento, entity.fechaNacimiento) &&
                Objects.equals(this.estado, entity.estado) &&
                Objects.equals(this.nombre, entity.nombre) &&
                Objects.equals(this.apellido, entity.apellido) &&
                Objects.equals(this.nombreUsuario, entity.nombreUsuario);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, cedula, email, contrasenia, fechaNacimiento, estado, nombre, apellido, nombreUsuario);
    }

    @Override
    public String toString() {
        return nombre + " " + apellido;
    }

    public void setIdInstitucion(InstitucionDto idInstitucion) {
        this.idInstitucion = idInstitucion;
    }

    public InstitucionDto getIdInstitucion() {
        return idInstitucion;
    }

    public void setIdPerfil(PerfilDto idPerfil) {
        this.idPerfil = idPerfil;
    }

    public PerfilDto getIdPerfil() {
        return idPerfil;
    }

    public Set<UsuariosTelefonoDto> getUsuariosTelefonos() {
        return usuariosTelefonos;
    }

    public UsuarioDto setUsuariosTelefonos(Set<UsuariosTelefonoDto> usuariosTelefonos) {
        this.usuariosTelefonos = usuariosTelefonos;
        return this;
    }
}
