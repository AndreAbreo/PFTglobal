package codigocreativo.uy.servidorapp.entidades;

import codigocreativo.uy.servidorapp.enumerados.Estados;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "PERFILES")
public class Perfil implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_PERFIL", nullable = false)
    private Long id;

    @Column(name = "NOMBRE_PERFIL", nullable = false, length = 70)
    private String nombrePerfil;

    @Enumerated(EnumType.STRING)
    @Column(name = "ESTADO", nullable = false, length = 20)
    private Estados estado;

    @OneToMany(mappedBy = "perfil", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties("perfil")
    private List<FuncionalidadesPerfiles> funcionalidadesPerfiles;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombrePerfil() {
        return nombrePerfil;
    }

    public void setNombrePerfil(String nombrePerfil) {
        this.nombrePerfil = nombrePerfil;
    }

    public Estados getEstado() {
        return estado;
    }

    public void setEstado(Estados estado) {
        this.estado = estado;
    }

    public List<FuncionalidadesPerfiles> getFuncionalidadesPerfiles() {
        return funcionalidadesPerfiles;
    }

    public void setFuncionalidadesPerfiles(List<FuncionalidadesPerfiles> funcionalidadesPerfiles) {
        this.funcionalidadesPerfiles = funcionalidadesPerfiles;
    }
}
