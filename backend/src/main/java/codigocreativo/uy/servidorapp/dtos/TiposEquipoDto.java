package codigocreativo.uy.servidorapp.dtos;

import codigocreativo.uy.servidorapp.enumerados.Estados;

import java.io.Serializable;
import java.util.Objects;

/**
 * DTO for {@link codigocreativo.uy.servidorapp.entidades.TiposEquipo}
 */
public class TiposEquipoDto implements Serializable {
    private Long id;
    private String nombreTipo;
    private Estados estado;

    public TiposEquipoDto() {
    }

    public TiposEquipoDto(Long id, String nombreTipo, Estados estado) {
        this.id = id;
        this.nombreTipo = nombreTipo;
        this.estado = estado;
    }

    public Long getId() {
        return id;
    }

    public TiposEquipoDto setId(Long id) {
        this.id = id;
        return this;
    }

    public String getNombreTipo() {
        return nombreTipo;
    }

    public TiposEquipoDto setNombreTipo(String nombreTipo) {
        this.nombreTipo = nombreTipo;
        return this;
    }

    public Estados getEstado() {
        return estado;
    }

    public TiposEquipoDto setEstado(Estados estado) {
        this.estado = estado;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TiposEquipoDto entity = (TiposEquipoDto) o;
        return Objects.equals(this.id, entity.id) &&
                Objects.equals(this.nombreTipo, entity.nombreTipo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nombreTipo);
    }

    @Override
    public String toString() {
        return nombreTipo;
    }
}