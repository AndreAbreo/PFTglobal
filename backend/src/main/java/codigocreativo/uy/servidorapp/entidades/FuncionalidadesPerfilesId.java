package codigocreativo.uy.servidorapp.entidades;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class FuncionalidadesPerfilesId implements Serializable {

    @Column(name = "ID_FUNCIONALIDAD")
    private Long idFuncionalidad;

    @Column(name = "ID_PERFIL")
    private Long idPerfil;

    public FuncionalidadesPerfilesId() {}

    public FuncionalidadesPerfilesId(Long idFuncionalidad, Long idPerfil) {
        this.idFuncionalidad = idFuncionalidad;
        this.idPerfil = idPerfil;
    }

    public Long getIdFuncionalidad() {
        return idFuncionalidad;
    }

    public void setIdFuncionalidad(Long idFuncionalidad) {
        this.idFuncionalidad = idFuncionalidad;
    }

    public Long getIdPerfil() {
        return idPerfil;
    }

    public void setIdPerfil(Long idPerfil) {
        this.idPerfil = idPerfil;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FuncionalidadesPerfilesId that = (FuncionalidadesPerfilesId) o;
        return Objects.equals(idFuncionalidad, that.idFuncionalidad) &&
               Objects.equals(idPerfil, that.idPerfil);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idFuncionalidad, idPerfil);
    }
}
