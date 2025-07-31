package codigocreativo.uy.servidorapp.dtos;

import java.io.Serializable;
import java.util.Objects;
import codigocreativo.uy.servidorapp.enumerados.Estados;


public class PaisDto implements Serializable {
    private Long id;
    private String nombre;
    private Estados estado;

    public PaisDto() {
    }

    public PaisDto(Long id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public Long getId() {
        return id;
    }

    public PaisDto setId(Long id) {
        this.id = id;
        return this;
    }

    public String getNombre() {
        return nombre;
    }

    public PaisDto setNombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public Estados getEstado() {
        return estado;
    }

    public void setEstado(Estados estado) {
        this.estado = estado;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PaisDto entity = (PaisDto) o;
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
}