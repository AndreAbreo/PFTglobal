package codigocreativo.uy.servidorapp.entidades;

import codigocreativo.uy.servidorapp.enumerados.Estados;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "FUNCIONALIDADES")
public class Funcionalidad implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_FUNCIONALIDAD")
    private Long id;

    @Column(name = "NOMBRE_FUNCIONALIDAD")
    private String nombreFuncionalidad;

    @Column(name = "RUTA")
    private String ruta;

    @Enumerated(EnumType.STRING)
    @Column(name = "ESTADO")
    private Estados estado;

    @OneToMany(mappedBy = "funcionalidad", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties("funcionalidad")
    private List<FuncionalidadesPerfiles> funcionalidadesPerfiles;

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public String getRuta() {
        return ruta;
    }

    public void setRuta(String ruta) {
        this.ruta = ruta;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombreFuncionalidad() {
        return nombreFuncionalidad;
    }

    public void setNombreFuncionalidad(String nombreFuncionalidad) {
        this.nombreFuncionalidad = nombreFuncionalidad;
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
