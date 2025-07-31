package codigocreativo.uy.servidorapp.dtos;

import com.fasterxml.jackson.annotation.JsonBackReference;

import java.io.Serializable;
import java.util.Objects;


public class UsuariosTelefonoDto implements Serializable {
    private Long id;
    private String numero;

    @JsonBackReference
    private UsuarioDto idUsuario;

    public UsuariosTelefonoDto() {
    }

    public UsuariosTelefonoDto(Long id, String numero,
                               UsuarioDto idUsuario) {
        this.id = id;
        this.numero = numero;
        this.idUsuario = idUsuario;
    }

    public Long getId() {
        return id;
    }

    public UsuariosTelefonoDto setId(Long id) {
        this.id = id;
        return this;
    }

    public String getNumero() {
        return numero;
    }

    public UsuariosTelefonoDto setNumero(String numero) {
        this.numero = numero;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UsuariosTelefonoDto entity = (UsuariosTelefonoDto) o;
        return Objects.equals(this.id, entity.id) &&
                Objects.equals(this.numero, entity.numero);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, numero);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "id = " + id + ", " +
                "numero = " + numero + ")";
    }

    public UsuarioDto getIdUsuario() {
        return idUsuario;
    }

    public UsuariosTelefonoDto setIdUsuario(UsuarioDto idUsuario) {
        this.idUsuario = idUsuario;
        return this;
    }
}