package codigocreativo.uy.servidorapp.entidades;

import codigocreativo.uy.servidorapp.enumerados.Estados;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "EQUIPOS")
public class Equipo implements Serializable {

    private static final long serialVersionUID = 2L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_EQUIPO", nullable = false)
    private Long id;

    @Column(name = "ID_INTERNO", nullable = false, length = 50)
    private String idInterno;

    @Column(name = "NRO_SERIE", nullable = false, length = 100)
    private String nroSerie;

    @Column(name = "NOMBRE", nullable = false, length = 100)
    private String nombre;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @OnDelete(action = OnDeleteAction.RESTRICT)
    @JoinColumn(name = "ID_TIPO", nullable = false)
    private TiposEquipo idTipo;

    @ManyToOne(fetch = FetchType.EAGER)
    @OnDelete(action = OnDeleteAction.RESTRICT)
    @JoinColumn(name = "ID_PROVEEDOR")
    private ProveedoresEquipo idProveedor;

    @ManyToOne(fetch = FetchType.EAGER)
    @OnDelete(action = OnDeleteAction.RESTRICT)
    @JoinColumn(name = "ID_PAIS")
    private codigocreativo.uy.servidorapp.entidades.Pais idPais;

    @ManyToOne(fetch = FetchType.EAGER)
    @OnDelete(action = OnDeleteAction.RESTRICT)
    @JoinColumn(name = "ID_MODELO")
    private ModelosEquipo idModelo;

    @Column(name = "IMAGEN")
    private String imagen;

    @Column(name = "FECHA_ADQUISICION")
    private LocalDate fechaAdquisicion;

    @Enumerated(EnumType.STRING)
    @Column(name = "ESTADO", length = 20)
    private Estados estado;

    @OneToMany(mappedBy = "idEquipo")
    private Set<EquiposUbicacione> equiposUbicaciones = new LinkedHashSet<>();

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @OnDelete(action = OnDeleteAction.RESTRICT)
    @JoinColumn(name = "ID_UBICACION", nullable = false)
    private Ubicacion idUbicacion;

    @Size(max = 20)
    @Column(name = "GARANTIA", length = 20)
    private String garantia;

    public String getGarantia() {
        return garantia;
    }

    public void setGarantia(String garantia) {
        this.garantia = garantia;
    }

    public Ubicacion getIdUbicacion() {
        return idUbicacion;
    }

    public void setIdUbicacion(Ubicacion idUbicacion) {
        this.idUbicacion = idUbicacion;
    }

    public Set<EquiposUbicacione> getEquiposUbicaciones() {
        return equiposUbicaciones;
    }

    public void setEquiposUbicaciones(Set<EquiposUbicacione> equiposUbicaciones) {
        this.equiposUbicaciones = equiposUbicaciones;
    }

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

    public String getIdInterno() {
        return idInterno;
    }

    public void setIdInterno(String idInterno) {
        this.idInterno = idInterno;
    }

    public String getNroSerie() {
        return nroSerie;
    }

    public void setNroSerie(String nroSerie) {
        this.nroSerie = nroSerie;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public TiposEquipo getIdTipo() {
        return idTipo;
    }

    public void setIdTipo(TiposEquipo idTipo) {
        this.idTipo = idTipo;
    }

    public ProveedoresEquipo getIdProveedor() {
        return idProveedor;
    }

    public void setIdProveedor(ProveedoresEquipo idProveedor) {
        this.idProveedor = idProveedor;
    }

    public codigocreativo.uy.servidorapp.entidades.Pais getIdPais() {
        return idPais;
    }

    public void setIdPais(codigocreativo.uy.servidorapp.entidades.Pais idPais) {
        this.idPais = idPais;
    }

    public ModelosEquipo getIdModelo() {
        return idModelo;
    }

    public void setIdModelo(ModelosEquipo idModelo) {
        this.idModelo = idModelo;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public LocalDate getFechaAdquisicion() {
        return fechaAdquisicion;
    }

    public void setFechaAdquisicion(LocalDate fechaAdquisicion) {
        this.fechaAdquisicion = fechaAdquisicion;
    }

}