package codigocreativo.uy.servidorapp.entidades;

import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "AUDITORIAS")
public class Auditoria implements Serializable {
    @Id
    @Column(name = "ID_AUDITORIA", nullable = false)
    private Long id;

    @Column(name = "FECHA_HORA", nullable = false)
    private LocalDate fechaHora;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.RESTRICT)
    @JoinColumn(name = "ID_USUARIO", nullable = false)
    private Usuario idUsuario;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.RESTRICT)
    @JoinColumn(name = "ID_OPERACION", nullable = false)
    private Operacion idOperacion;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(LocalDate fechaHora) {
        this.fechaHora = fechaHora;
    }

    public Usuario getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Usuario idUsuario) {
        this.idUsuario = idUsuario;
    }

    public codigocreativo.uy.servidorapp.entidades.Operacion getIdOperacion() {
        return idOperacion;
    }

    public void setIdOperacion(codigocreativo.uy.servidorapp.entidades.Operacion idOperacion) {
        this.idOperacion = idOperacion;
    }

}