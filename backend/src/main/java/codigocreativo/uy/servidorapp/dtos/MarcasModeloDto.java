package codigocreativo.uy.servidorapp.dtos;

import codigocreativo.uy.servidorapp.enumerados.Estados;

import java.io.Serializable;
import java.util.Objects;


public class MarcasModeloDto implements Serializable {
    private Long id;
    private String nombre;
    private Estados estado;

    public MarcasModeloDto() {
    }

    public MarcasModeloDto(Long id, String nombre, Estados estado) {
        this.id = id;
        this.nombre = nombre;
        this.estado = estado;
    }

    public Long getId() {
        return id;
    }

    public MarcasModeloDto setId(Long id) {
        this.id = id;
        return this;
    }

    public String getNombre() {
        return nombre;
    }

    public MarcasModeloDto setNombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MarcasModeloDto entity = (MarcasModeloDto) o;
        return Objects.equals(this.id, entity.id) &&
                Objects.equals(this.nombre, entity.nombre);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nombre);
    }

    @Override
    public String toString() {
        return nombre;
    }

    public Estados getEstado() {
        return estado;
    }

    public MarcasModeloDto setEstado(Estados estado) {
        this.estado = estado;
        return this;
    }
}