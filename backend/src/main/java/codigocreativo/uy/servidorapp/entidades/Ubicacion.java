package codigocreativo.uy.servidorapp.entidades;

import codigocreativo.uy.servidorapp.enumerados.Estados;
import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.io.Serializable;

@Entity
@Table(name = "UBICACIONES")
public class Ubicacion implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_UBICACION", nullable = false)
    private Long id;

    @Column(name = "NOMBRE", nullable = false, length = 30)
    private String nombre;

    @Column(name = "SECTOR", length = 30)
    private String sector;

    @Column(name = "PISO")
    private Long piso;

    @Column(name = "NUMERO")
    private Long numero;

    @Column(name = "CAMA")
    private Long cama;

    @ManyToOne(fetch = FetchType.EAGER)
    @OnDelete(action = OnDeleteAction.RESTRICT)
    @JoinColumn(name = "ID_INSTITUCION")
    private Institucion idInstitucion;

    @Enumerated(EnumType.STRING)
    @Column(name = "ESTADO", length = 15)
    private Estados estado;

    public Estados getEstado() {
        return estado;
    }

    public void setEstado(Estados estado) {
        this.estado = estado;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getSector() {
        return sector;
    }

    public void setSector(String sector) {
        this.sector = sector;
    }

    public Long getPiso() {
        return piso;
    }

    public void setPiso(Long piso) {
        this.piso = piso;
    }

    public Long getNumero() {
        return numero;
    }

    public void setNumero(Long numero) {
        this.numero = numero;
    }

    public Institucion getIdInstitucion() {
        return idInstitucion;
    }

    public void setIdInstitucion(Institucion idInstitucion) {
        this.idInstitucion = idInstitucion;
    }

    @Override
    public String toString() {
        return nombre;
    }

    public void setCama(Long cama) {
        this.cama = cama;
    }

    public Long getCama() {
        return cama;
    }
}