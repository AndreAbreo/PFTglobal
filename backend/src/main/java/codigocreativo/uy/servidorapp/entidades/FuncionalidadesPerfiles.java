package codigocreativo.uy.servidorapp.entidades;

import jakarta.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "FUNCIONALIDADES_PERFILES")
public class FuncionalidadesPerfiles implements Serializable {

    @EmbeddedId
    private FuncionalidadesPerfilesId id;

    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("idFuncionalidad")
    @JoinColumn(name = "ID_FUNCIONALIDAD", nullable = false)
    private Funcionalidad funcionalidad;

    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("idPerfil")
    @JoinColumn(name = "ID_PERFIL", nullable = false)
    private Perfil perfil;

    public FuncionalidadesPerfiles() {}

    public FuncionalidadesPerfiles(FuncionalidadesPerfilesId id, Funcionalidad funcionalidad, Perfil perfil) {
        this.id = id;
        this.funcionalidad = funcionalidad;
        this.perfil = perfil;
    }

    public FuncionalidadesPerfilesId getId() {
        return id;
    }

    public void setId(FuncionalidadesPerfilesId id) {
        this.id = id;
    }

    public Funcionalidad getFuncionalidad() {
        return funcionalidad;
    }

    public void setFuncionalidad(Funcionalidad funcionalidad) {
        this.funcionalidad = funcionalidad;
    }

    public Perfil getPerfil() {
        return perfil;
    }

    public void setPerfil(Perfil perfil) {
        this.perfil = perfil;
    }
}
