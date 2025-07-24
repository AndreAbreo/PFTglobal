package codigocreativo.uy.servidorapp.entidades;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "PROVEEDORES_EQUIPOS")
public class ProveedoresEquipo implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_PROVEEDOR", nullable = false)
    private Long id;

    @Column(name = "NOMBRE", nullable = false, length = 30)
    private String nombre;

    @Column(name = "ESTADO", nullable = false, length = 20)
    private String estado;

    //Agregar referencia con pais
    @ManyToOne
    @JoinColumn(name = "ID_PAIS", nullable = false)
    private Pais pais;



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

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Pais getPais() {
    	return pais;
    }

    public void setPais(Pais pais) {
    	this.pais = pais;
    }

    @Override
    public String toString() {
    	return nombre;
    }

}