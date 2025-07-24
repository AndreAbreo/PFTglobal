package codigocreativo.uy.servidorapp.entidades;

import codigocreativo.uy.servidorapp.enumerados.Estados;
import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "TIPOS_INTERVENCIONES")
public class TiposIntervencione implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_TIPO", nullable = false)
    private Long id;

    @Column(name = "NOMBRE_TIPO", nullable = false, length = 30)
    private String nombreTipo;

    @Enumerated(EnumType.STRING)
    @Column(name = "ESTADO", length = 10)
    private Estados estado;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombreTipo() {
        return nombreTipo;
    }

    public void setNombreTipo(String nombreTipo) {
        this.nombreTipo = nombreTipo;
    }

    public Estados getEstado() {
        return estado;
    }

    public void setEstado(Estados estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return nombreTipo;
    }
}