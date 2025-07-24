package codigocreativo.uy.servidorapp.dtos;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * DTO for {@link codigocreativo.uy.servidorapp.entidades.Auditoria}
 */
public class AuditoriaDto implements Serializable {
    private Long id;
    private LocalDate fechaHora;
    private UsuarioDto idUsuario;
    private OperacionDto idOperacion;

    public AuditoriaDto() {
    }

    public AuditoriaDto(Long id, LocalDate fechaHora,
                        UsuarioDto idUsuario,
                        OperacionDto idOperacion) {
        this.id = id;
        this.fechaHora = fechaHora;
        this.idUsuario = idUsuario;
        this.idOperacion = idOperacion;
    }

    public Long getId() {
        return id;
    }

    public AuditoriaDto setId(Long id) {
        this.id = id;
        return this;
    }

    public LocalDate getFechaHora() {
        return fechaHora;
    }

    public AuditoriaDto setFechaHora(LocalDate fechaHora) {
        this.fechaHora = fechaHora;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AuditoriaDto entity = (AuditoriaDto) o;
        return Objects.equals(this.id, entity.id) &&
                Objects.equals(this.fechaHora, entity.fechaHora);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, fechaHora);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "id = " + id + ", " +
                "fechaHora = " + fechaHora + ")";
    }

    public UsuarioDto getIdUsuario() {
        return idUsuario;
    }

    public AuditoriaDto setIdUsuario(UsuarioDto idUsuario) {
        this.idUsuario = idUsuario;
        return this;
    }

    public OperacionDto getIdOperacion() {
        return idOperacion;
    }

    public AuditoriaDto setIdOperacion(OperacionDto idOperacion) {
        this.idOperacion = idOperacion;
        return this;
    }
}