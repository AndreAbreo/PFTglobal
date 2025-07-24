package codigocreativo.uy.servidorapp.dtos;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

public class BajaEquipoDto implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private Long id;
    private String razon;
    private LocalDate fecha;
    private UsuarioDto idUsuario;
    private EquipoDto idEquipo;
    private String estado;
    private String comentarios;
    
    // Constructores
    public BajaEquipoDto() {}
    
    public BajaEquipoDto(Long id, String razon, LocalDate fecha, UsuarioDto idUsuario, EquipoDto idEquipo, String estado, String comentarios) {
        this.id = id;
        this.razon = razon;
        this.fecha = fecha;
        this.idUsuario = idUsuario;
        this.idEquipo = idEquipo;
        this.estado = estado;
        this.comentarios = comentarios;
    }
    
    // Getters y Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
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
    
    public UsuarioDto getIdUsuario() {
        return idUsuario;
    }
    
    public void setIdUsuario(UsuarioDto idUsuario) {
        this.idUsuario = idUsuario;
    }
    
    public EquipoDto getIdEquipo() {
        return idEquipo;
    }
    
    public void setIdEquipo(EquipoDto idEquipo) {
        this.idEquipo = idEquipo;
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
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BajaEquipoDto that = (BajaEquipoDto) o;
        return Objects.equals(id, that.id) &&
               Objects.equals(razon, that.razon) &&
               Objects.equals(fecha, that.fecha) &&
               Objects.equals(estado, that.estado) &&
               Objects.equals(comentarios, that.comentarios);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(id, razon, fecha, estado, comentarios);
    }
    
    @Override
    public String toString() {
        return "BajaEquipoDto{" +
               "id=" + id +
               ", razon=" + razon +
               ", fecha=" + fecha +
               ", estado=" + estado +
               ", comentarios=" + comentarios +
               '}';
    }
}