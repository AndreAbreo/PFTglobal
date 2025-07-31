package codigocreativo.uy.servidorapp.entidades;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "BAJA_EQUIPOS")
public class BajaEquipo implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_BAJA", nullable = false)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ID_USUARIO", nullable = false)
    private Usuario idUsuario;
    
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ID_EQUIPO", nullable = false)
    private Equipo idEquipo;
    
    @Column(name = "RAZON", nullable = false, length = 500)
    private String razon;
    
    @Column(name = "FECHA", nullable = false)
    private LocalDate fecha;
    
    @Column(name = "ESTADO", nullable = false, length = 20)
    private String estado;
    
    @Column(name = "COMENTARIOS", length = 1000)
    private String comentarios;

    public BajaEquipo() {}
    
    public BajaEquipo(Long id, Usuario idUsuario, Equipo idEquipo, String razon, LocalDate fecha, String estado, String comentarios) {
        this.id = id;
        this.idUsuario = idUsuario;
        this.idEquipo = idEquipo;
        this.razon = razon;
        this.fecha = fecha;
        this.estado = estado;
        this.comentarios = comentarios;
    }

    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Usuario getIdUsuario() {
        return idUsuario;
    }
    
    public void setIdUsuario(Usuario idUsuario) {
        this.idUsuario = idUsuario;
    }
    
    public Equipo getIdEquipo() {
        return idEquipo;
    }
    
    public void setIdEquipo(Equipo idEquipo) {
        this.idEquipo = idEquipo;
    }
    
    public String getRazon() {
        return razon;
    }
    
    public void setRazon(String razon) {
        this.razon = razon;
    }
    
    public LocalDate getFecha() {
        return fecha;
    }
    
    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }
    
    public String getEstado() {
        return estado;
    }
    
    public void setEstado(String estado) {
        this.estado = estado;
    }
    
    public String getComentarios() {
        return comentarios;
    }
    
    public void setComentarios(String comentarios) {
        this.comentarios = comentarios;
    }
}