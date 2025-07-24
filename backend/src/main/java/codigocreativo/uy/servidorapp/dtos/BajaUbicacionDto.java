package codigocreativo.uy.servidorapp.dtos;

import codigocreativo.uy.servidorapp.entidades.BajaUbicacion;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * DTO for {@link BajaUbicacion}
 */
public class BajaUbicacionDto implements Serializable {
    private Long id;
    private UsuarioDto idUsuario;
    private UbicacionDto idUbicacion;
    private String razon;
    private String comentario;
    private LocalDate fecha;

    public BajaUbicacionDto(Long id, UsuarioDto idUsuario, UbicacionDto idUbicacion, String razon, String comentario, LocalDate fecha) {
        this.id = id;
        this.idUsuario = idUsuario;
        this.idUbicacion = idUbicacion;
        this.razon = razon;
        this.comentario = comentario;
        this.fecha = fecha;
    }

    public BajaUbicacionDto() {
    }

    public Long getId() {
        return id;
    }

    public UsuarioDto getIdUsuario() {
        return idUsuario;
    }

    public UbicacionDto getIdUbicacion() {
        return idUbicacion;
    }

    public String getRazon() {
        return razon;
    }

    public String getComentario() {
        return comentario;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setIdUsuario(UsuarioDto idUsuario) {
        this.idUsuario = idUsuario;
    }

    public void setIdUbicacion(UbicacionDto idUbicacion) {
        this.idUbicacion = idUbicacion;
    }

    public void setRazon(String razon) {
        this.razon = razon;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BajaUbicacionDto entity = (BajaUbicacionDto) o;
        return Objects.equals(this.id, entity.id) &&
                Objects.equals(this.idUsuario, entity.idUsuario) &&
                Objects.equals(this.idUbicacion, entity.idUbicacion) &&
                Objects.equals(this.razon, entity.razon) &&
                Objects.equals(this.comentario, entity.comentario) &&
                Objects.equals(this.fecha, entity.fecha);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, idUsuario, idUbicacion, razon, comentario, fecha);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "id = " + id + ", " +
                "idUsuario = " + idUsuario + ", " +
                "idUbicacion = " + idUbicacion + ", " +
                "razon = " + razon + ", " +
                "comentario = " + comentario + ", " +
                "fecha = " + fecha + ")";
    }
}