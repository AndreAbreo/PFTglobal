package codigocreativo.uy.servidorapp.dtos;

import codigocreativo.uy.servidorapp.entidades.Intervencion;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * DTO for {@link Intervencion}
 */
public class IntervencionDto implements Serializable {
    private Long id;
    private String motivo;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime fechaHora;
    private String comentarios;
    private UsuarioDto idUsuario;
    private TiposIntervencioneDto idTipo;
    private EquipoDto idEquipo;

    public IntervencionDto() {
    }

    public IntervencionDto(Long id, String motivo, LocalDateTime fechaHora, String comentarios,
                           UsuarioDto idUsuario,
                           TiposIntervencioneDto idTipo,
                           EquipoDto idEquipo) {
        this.id = id;
        this.motivo = motivo;
        this.fechaHora = fechaHora;
        this.comentarios = comentarios;
        this.idUsuario = idUsuario;
        this.idTipo = idTipo;
        this.idEquipo = idEquipo;
    }

    public Long getId() {
        return id;
    }

    public IntervencionDto setId(Long id) {
        this.id = id;
        return this;
    }

    public String getMotivo() {
        return motivo;
    }

    public IntervencionDto setMotivo(String motivo) {
        this.motivo = motivo;
        return this;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public IntervencionDto setFechaHora(LocalDateTime fechaHora) {
        this.fechaHora = fechaHora;
        return this;
    }

    public String getComentarios() {
        return comentarios;
    }

    public IntervencionDto setComentarios(String comentarios) {
        this.comentarios = comentarios;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IntervencionDto entity = (IntervencionDto) o;
        return Objects.equals(this.id, entity.id) &&
                Objects.equals(this.motivo, entity.motivo) &&
                Objects.equals(this.fechaHora, entity.fechaHora) &&
                Objects.equals(this.comentarios, entity.comentarios);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, motivo, fechaHora, comentarios, idUsuario, idTipo, idEquipo);
    }

    @Override
    public String toString() {
        return motivo;
    }

    public UsuarioDto getIdUsuario() {
        return idUsuario;
    }

    public IntervencionDto setIdUsuario(UsuarioDto idUsuario) {
        this.idUsuario = idUsuario;
        return this;
    }

    public TiposIntervencioneDto getIdTipo() {
        return idTipo;
    }

    public IntervencionDto setIdTipo(TiposIntervencioneDto idTipo) {
        this.idTipo = idTipo;
        return this;
    }

    public EquipoDto getIdEquipo() {
        return idEquipo;
    }

    public IntervencionDto setIdEquipo(EquipoDto idEquipo) {
        this.idEquipo = idEquipo;
        return this;
    }
}
